package one.database.mapper;

import java.util.List;

import one.services.beans.ScheduleBean;
import one.services.beans.SdDetailBean;


public interface ScheduleInterface {
	
	boolean insFile(SdDetailBean sdDetailBean);
	List<ScheduleBean> getImage(SdDetailBean sdb);
	boolean insSd(ScheduleBean sb);

}
