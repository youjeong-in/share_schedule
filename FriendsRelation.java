package one.services.friends;


import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import one.database.mapper.FriendsInterface;
import one.schedule.util.Encryption;
import one.schedule.util.ProjectUtil;
import one.services.beans.TDetailBean;
import one.services.beans.TeamBean;

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

	private DefaultTransactionDefinition def; //isolation과 propagation을 위한
	private TransactionStatus status;




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

			} catch (Exception e) {

				e.printStackTrace();
			} 
		}
		
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

	

	//DATASOURCETRANSACTION MANAGER :
	// 1.선언적 트랜잭션 : AOP 방식 (특정한 관점으로) @Transactional 어노테이션 사용 --> 관련된 메셔드는 반드시  public해야함
	// 2. 명시적 트랜잭션 Programa Transaction 
	//   	환경설정 : propacaion(전파방식) , isolation (격리수준)
}
