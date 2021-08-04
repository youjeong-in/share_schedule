package one.services.schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
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

	ModelAndView mav;


	public ModelAndView sendFile(ScheduleBean sb) {
		mav = new ModelAndView();
		SdDetailBean sdb = new SdDetailBean();

		for(int i=0; i<sb.getSdFile().size(); i++) {
			sdb.setStickerPath("resources/image/" + pu.savingFile(sb.getSdFile().get(i)));
			sdb.setTCode("210804001");
			sdb.setNum(1);

			if(this.insFile(sdb)) {
				System.out.println("insert성공");
				mav.setViewName("scheduleManage");
			}
		}

		return mav;
	}

	//stickerPath가 []일 경우에
	//	public ModelAndView sendFile(ScheduleBean sb) {
	//		mav = new ModelAndView();
	//		SdDetailBean sdb = new SdDetailBean();
	//
	//
	//		String[] stickerpath = new String[sb.getSdFile().size()];
	//		sb.setStickerPath(stickerpath);
	//
	//		for(int i=0; i<sb.getSdFile().size(); i++) {
	//			//System.out.println(sb.getSdFile());
	//			(sb.getStickerPath())[i] = ("resources/image/" + pu.savingFile(sb.getSdFile().get(i)));
	//		}
	//
	//		
	////		for(int i=0; i<sb.getStickerPath().length; i++) {
	////			sdb.setTCode("210804001");
	////			sdb.setNum(1);
	////			sdb.setStickerPath(sb.getStickerPath()[i]);
	////		}
	////		System.out.println(sdb.getTCode());
	////		System.out.println(sdb.getNum());
	////		System.out.println("last : " + sdb);
	//		System.out.println(Arrays.toString(sb.getStickerPath()));
	//
	//		if(this.insFile(sdb)) {
	//			System.out.println("insert성공");
	//			mav.setViewName("scheduleManage");
	//		}

	//
	//		return mav;
	//	}


	public boolean insFile(SdDetailBean sdDetailBean) {

		return this.convertBoolean(sqlSession.insert("insFile", sdDetailBean));
	}


	public List<ScheduleBean> getImage(SdDetailBean sdb) {
		List<ScheduleBean> list;
		list = sqlSession.selectList("getImage", sdb);
		System.out.println(list);
		return list;
	}

	private boolean convertBoolean(int data) {
		return (data > 0)? true:false;
	}

}
