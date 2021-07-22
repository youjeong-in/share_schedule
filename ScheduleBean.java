package one.services.beans;

import lombok.Data;

@Data
public class ScheduleBean {
	
	private String tCode;
	private String tName;
	private int num;
	private String msId;
	private String msName;
	private String title;
	private String date;  //가져올땐 to_char로 가져와야함.
	private String location;
	private String contents;
	private String proName;
	private String process;
	private String opName;
	private String open;
	private String loName;
	private String loop;

}
