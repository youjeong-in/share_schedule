package one.services.friends;


import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.servlet.ModelAndView;

import one.database.mapper.FriendsInterface;
import one.schedule.util.Encryption;
import one.schedule.util.ProjectUtil;
import one.services.beans.MailBean;
import one.services.beans.SearchBean;
import one.services.beans.TDetailBean;
import one.services.beans.TeamBean;
import one.services.beans.UserBeans;

@Service
public class FriendsRelation implements FriendsInterface{

	@Autowired
	Encryption enc;
	@Autowired
	ProjectUtil pu;
	@Autowired 
	SqlSessionTemplate sqlSession;
	@Autowired
	DataSourceTransactionManager tx; 

	@Autowired
	JavaMailSenderImpl javaMail;

	private DefaultTransactionDefinition def; //isolation과 propagation을 위한
	private TransactionStatus status;
	ModelAndView mav;




	public FriendsRelation() {

	}


	public List<TeamBean> getTeamList(TeamBean tb){

		List<TeamBean> list;
		list = sqlSession.selectList("getTeamList", tb);
		//System.out.println(list);

		return list;
	}


	public List<TDetailBean> getMemberList(TDetailBean tdb) {

		List<TDetailBean> memberList;
		memberList = sqlSession.selectList("getMemberList", tdb);

		for(int index = 0; index < memberList.size(); index++) {
			try {
				memberList.get(index).setMsName(enc.aesDecode(memberList.get(index).getMsName(), memberList.get(index).getMsId()));

			} catch (Exception e) {

				e.printStackTrace();
			} 
		}
		//System.out.println(memberList);
		return memberList;
	}

	@Transactional(rollbackFor = Exception.class) //그중에서 하나가 안됐으면 rollback해주세요
	public List<TeamBean> addTeam(TeamBean tb) {


		//Select 오늘날짜의 몇번까지 만들어졌는지 확인
		if(this.getCount()!=null) {
			tb.setTCodeNum(getCount());

			this.setTransactionConf(TransactionDefinition.PROPAGATION_REQUIRED,TransactionDefinition.ISOLATION_READ_COMMITTED ,false);

			try {
				//T 테이블에 추가
				if(this.insTeam(tb)) {

					tb.setMsId((String)pu.getAttribute("userId"));
					tb.setTCode(this.getNewCode());	
					//tb.setTCode("210726001"); //커밋 테스트용
					//TD 테이블에 추가
					this.insMb(tb);
					this.setTransactionResult(true); // commit완료
					System.out.println("commit 성공");						
				} 
			}catch (Exception e) {
				System.out.println("rollback");
				this.setTransactionResult(false); //rollback
				e.printStackTrace();
			}

		}

		//getTeamList 호출

		return this.getTeamList(tb);
	}

	//오늘날짜 몇번까지 만들어졌는지 확인 + 숫자 증가
	public String getCount() {

		int number;

		if(sqlSession.selectOne("getCount")!=null) {
			number = sqlSession.selectOne("getCount");
		}else {
			number = 0;
		}
		String result = (number+1) + ""; 

		for(int add = result.length(); add<3; add++) {
			result = "0" + result;
		}

		return result;
	}

	public String getNewCode() {

		return sqlSession.selectOne("getNewCode");
	}

	public boolean insTeam(TeamBean tb) {
		return this.convert(sqlSession.insert("insTeam", tb));
	}

	public boolean insMb(TeamBean tb) {
		return this.convert(sqlSession.insert("insMb", tb));
	}

	public boolean convert(int data) {
		return (data > 0 ) ? true : false;
	}


	public List<TDetailBean> getFriends(TDetailBean tdb) {
		List<TDetailBean> fList; 

		fList = sqlSession.selectList("getFriends", tdb);


		for(int index = 0; index < fList.size(); index++) {
			try {
				fList.get(index).setMsName(enc.aesDecode(fList.get(index).getMsName(), fList.get(index).getMsId()));
				fList.get(index).setEmail(enc.aesDecode(fList.get(index).getEmail(), fList.get(index).getMsId()));
			} catch (Exception e) {

				e.printStackTrace();
			} 
		}

		//System.out.println(fList);
		return fList;
	}


	//transaction Configuration
	private void setTransactionConf(int propagation, int isolationLevel, boolean isRead) {
		def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(propagation);
		def.setIsolationLevel(isolationLevel);
		def.setReadOnly(isRead);

		status = tx.getTransaction(def);
	}

	//Transaction Result , commit이냐 rollback이냐
	private void setTransactionResult(boolean isCheck) {
		if(isCheck) {
			tx.commit(status);

		}else {
			tx.rollback(status);
		}
	}

	public String getMail(TDetailBean tdb) {
		String mail;

		mail =sqlSession.selectOne("getMail", tdb);

		return mail;
	}


	//멤버추가
	public List<TDetailBean> addMember(TeamBean tb) {


		tb.getTdetails().get(0).setTCode(tb.getTCode());
		this.friendsAuth(tb.getTdetails());  


		return getMemberList(tb.getTdetails().get(0));
	}


	//멤버추가 메일보내기
	private void friendsAuth(List<TDetailBean> tdb) {//메일작성, 전송

		String subject = "팀원으로 초대합니다.";
		String contents = "<a href='http://192.168.1.188/EmailAuth?tCode="+tdb.get(0).getTCode()+"'>클릭하여 초대에 승낙해주세요.</a>";
				
//		for(int i=0; i<tdb.size(); i++) {
//				contents[i] ="<a href='http://192.168.1.189/EmailAuth?msId="+tdb.get(i).getMsId()+"&tCode="+tdb.get(0).getTCode()+"'>클릭하여 초대에 승낙해주세요.</a>";
//			
//		}
		
		String from = "i_innew0731@naver.com";
		String[] to = new String[tdb.size()];
		for(int index=0; index<tdb.size(); index++) {
			to[index] = tdb.get(index).getEmail();
		}

		MimeMessage mail = javaMail.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail, "UTF-8");


		try {
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(contents,true);
			
			
			javaMail.send(mail);
		} catch (MessagingException e) {

			e.printStackTrace();
		}



	}
	
	public ModelAndView authConfirm(TeamBean tb) {
		mav = new ModelAndView();
		String message = "멤버추가에 실패했습니다.";

		this.setTransactionConf(TransactionDefinition.PROPAGATION_REQUIRED,TransactionDefinition.ISOLATION_READ_COMMITTED ,false);

		try {
			if(this.insGeneral(tb)) {//true이면 insert가 1이면
				mav.setViewName("dashboard");
				message = "멤버추가가 정상적으로 이루어졌습니다.";
				System.out.println("성공");

				this.setTransactionResult(true); // commit완료

			}
		}catch(Exception e){
			this.setTransactionResult(false); // rollback
			System.out.println("insert fail");
		}

		mav.addObject("message", message);
		return mav;
	}


	@Override
	public boolean insGeneral(TeamBean tb) {

		return this.convert(sqlSession.insert("insGeneral", tb));
	}

	//모든 멤버들을 불러옴
	public List<SearchBean> allmember(){
		List<SearchBean> memberList;
		memberList =sqlSession.selectList("allmember");

		return memberList;
	}

	//allmember와 검색어 비교 
	public List<SearchBean> search (SearchBean sb) {
		List<SearchBean> bean= new ArrayList<SearchBean>();

		for(int list=0; list<this.allmember().size(); list++) {
			SearchBean result  = new SearchBean();
			try {

				result.setUserName(enc.aesDecode(this.allmember().get(list).getUserName(), this.allmember().get(list).getUserId()));
				result.setUserMail(enc.aesDecode(this.allmember().get(list).getUserMail(), this.allmember().get(list).getUserId()));
				result.setUserId(this.allmember().get(list).getUserId());

				if((result.getUserName()+result.getUserMail()+result.getUserId()).contains(sb.getWord())) {
					bean.add(result);

				}	

			} catch (Exception e) {	
				e.printStackTrace();
			}
		}

		return bean;
	}


	//친구 신청
	public String askFriend(SearchBean sb) {
	
		String message="친구신청이 실패하였습니다. 다시 시도하여주세요";
					
		try {
			sb.setMyId((String) pu.getAttribute("userId"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setTransactionConf(TransactionDefinition.PROPAGATION_REQUIRED,TransactionDefinition.ISOLATION_READ_COMMITTED ,false);

		try {
			if(this.askFr(sb)) {//만약에 0이 insert가 됐고, 
				System.out.println("여기");
				message = "친구신청이 완료되었습니다.";
				this.setTransactionResult(true);
				System.out.println("성공");

			}
		}catch(Exception e) {
			e.printStackTrace();
			this.setTransactionResult(false); // rollback
			System.out.println("insert fail");
		}

		
		return message;
	}

	//0으로 친구 신청
	public boolean askFr(SearchBean sb) {

		return this.convert(sqlSession.insert("askFr", sb));
	}

	//1로 친구상태 변경
	public boolean acceptFr(SearchBean sb) {

		return this.convert(sqlSession.insert("acceptFr", sb));
	}


	//웹사이트의 멤버가 아닌 친구 이메일로 초대하기
	public Map<String, String> askMail(MailBean mb) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("message", "요청메일을 전송하지못했습니다. 다시 시도해주세요.");
		
		mb.setFrom("i_innew0731@naver.com");
		mb.setSubject("초대장이 도착했습니다.");
		try {
			mb.setContent(((String)pu.getAttribute("userId"))+"님이 초대하셨습니다." + "<a href='http://192.168.1.188/signUpForm'>클릭하여 초대에 응해주세요.</a>");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		if(this.friendsAuth(mb)) {
			map.put("message", "요청메일을 전송하였습니다.");
		}
		return map;
	}

	//친구 초대 멜보내기
	private boolean friendsAuth(MailBean mb) {//메일작성, 전송	
		boolean check = false;
		
		MimeMessage mail = javaMail.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail, "UTF-8");


		try {
			helper.setFrom(mb.getFrom());
			helper.setTo(mb.getTo());
			helper.setSubject(mb.getSubject());
			helper.setText(mb.getContent(),true);

			javaMail.send(mail);
			check=true;
		} catch (MessagingException e) {

			e.printStackTrace();
		}
		return check;
	}
	
}





	//DATASOURCETRANSACTION MANAGER :
	// 1.선언적 트랜잭션 : AOP 방식 (특정한 관점으로) @Transactional 어노테이션 사용 --> 관련된 메셔드는 반드시  public해야함
	// 2. 명시적 트랜잭션 Programa Transaction 
	//   	환경설정 : propacaion(전파방식) , isolation (격리수준)

