package one.database.mapper;

import java.util.List;

import one.services.beans.ScheduleBean;
import one.services.beans.SdDetailBean;


public interface ScheduleInterface {
	
	boolean insFile(SdDetailBean sdDetailBean);
	List<ScheduleBean> getImage(SdDetailBean sdb);
	boolean insSd(ScheduleBean sb);
	List<ScheduleBean> selMonthSd (ScheduleBean sb);
	List<ScheduleBean> selDaySd (ScheduleBean sb);
	List<ScheduleBean> selCgName(ScheduleBean sb);
}
