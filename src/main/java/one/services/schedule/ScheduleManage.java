package one.services.schedule;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import one.database.mapper.ScheduleInterface;
import one.schedule.util.Encryption;
import one.schedule.util.ProjectUtil;
import one.services.beans.ScheduleBean;
import one.services.beans.SdDetailBean;
import one.services.beans.TDetailBean;


@Service
public class ScheduleManage {

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
	ModelAndView mav;


	//테스트 파일전송
	public ModelAndView sendFile(ScheduleBean sb) {
		mav = new ModelAndView();
		SdDetailBean sdb = new SdDetailBean();

		for(int i=0; i<sb.getSdFile().size(); i++) {
			sdb.setStickerPath("resources/image/" + pu.savingFile(sb.getSdFile().get(i)));
			sdb.setTCode("210804001");
			sdb.setNum(1);


			if(this.insFile(sdb)) {
				//System.out.println("insert성공");	
			}
		}


		System.out.println(this.getImage(sdb).size());

		for(int r=0; r<this.getImage(sdb).size(); r++) {
			mav.addObject("picture_"+ r +"", this.getImage(sdb).get(r).getStickerPath());
		}
		mav.setViewName("redirect:/");
		return mav;
	}



	public List<SdDetailBean> getImage(SdDetailBean sdb) {
		List<SdDetailBean> list;
		list = sqlSession.selectList("getImage", sdb);
		return list;
	}

	//스케줄등록, 스케줄에 파일등록
	public ModelAndView addSd(ScheduleBean sb) {
		mav = new ModelAndView();
		SdDetailBean sdb = new SdDetailBean();

		try {
			sb.setMsId((String)pu.getAttribute("userId"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.setTransactionConf(TransactionDefinition.PROPAGATION_REQUIRED,TransactionDefinition.ISOLATION_READ_COMMITTED ,false);

		try {
			if(this.insSd(sb)) {
				mav.addObject("message", "스케줄 등록이 완료되었습니다.");
				for(int i=0; i<sb.getSdFile().size(); i++) {
					if(sb.getSdFile().get(i).getOriginalFilename()!="") {
						sdb.setStickerPath("resources/image/" + pu.savingFile(sb.getSdFile().get(i)));
						sdb.setTCode(sb.getTCode());

						if(this.insFile(sdb)){
							mav.addObject("message", "스케줄 등록이 완료되었습니다.");
						}
					}else {
						System.out.println("파일없음");
					}
				}this.setTransactionResult(true);
			}
		}catch(Exception e){
			this.setTransactionResult(false); 
			System.out.println("rollback");
			mav.addObject("message", "스케줄 등록에 실패했습니다. 다시 시도해주세요.");	
		}

		mav.setViewName("redirect:/");

		return mav;
	}

	//스케줄등록 xml불러오는 메서드
	private boolean insSd(ScheduleBean sb) {

		return this.convertBoolean(sqlSession.insert("insSd", sb));
	}


	//파일 insert xml불러오는 메서드
	private boolean insFile(SdDetailBean sdDetailBean) {

		return this.convertBoolean(sqlSession.insert("insFile", sdDetailBean));
	}

	private boolean convertBoolean(int data) {
		return (data > 0)? true:false;
	}

	//월별 스케줄 불러오는 메서드
	public List<ScheduleBean> askMonthSd(ScheduleBean sb) {
		List<ScheduleBean> sdList;

		try {
			sb.setMsId((String)pu.getAttribute("userId"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		sdList = this.selMonthSd(sb);

		for(int i=0; i<sdList.size(); i++) {
			try {
				sdList.get(i).setMsName(enc.aesDecode(this.selMonthSd(sb).get(i).getMsName(), sb.getMsId()));
			} catch (Exception e) {

				e.printStackTrace();
			} 
		}

		//System.out.println(sdList);
		return sdList;
	}

	//월별 스케줄 불러오는 xml
	public List<ScheduleBean> selMonthSd (ScheduleBean sb){

		return sqlSession.selectList("selMonthSd", sb);
	}
	
		//일별 스케줄 불러오는 메서드
		public List<ScheduleBean> askDaySd(ScheduleBean sb) {
			List<ScheduleBean> daySd;
			try {
				sb.setMsId((String)pu.getAttribute("userId"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			//System.out.println("sb   :   "+sb);
			
			daySd = this.selDaySd(sb);
			for(int i=0; i<daySd.size(); i++) {
					try {
						daySd.get(i).setMsName(enc.aesDecode(daySd.get(i).getMsName(), sb.getMsId()));
						daySd.get(i).setProName(this.selCgName(sb).get(i).getProName());
					} catch (Exception e) {e.printStackTrace();}
			}
			System.out.println("최종 : "+daySd);
			return daySd;
		}
	
		public List<ScheduleBean> selDaySd(ScheduleBean sb){
			return sqlSession.selectList("selDaySd", sb);
		}
		
		public List<ScheduleBean> selCgName(ScheduleBean sb) {
			return sqlSession.selectList("selCgName", sb);
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





}
