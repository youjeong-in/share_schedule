package one.services.beans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ScheduleBean {
	
	private String tCode;
	private String tName;
	private int num; //db의 예약어 때문에 어쩔수없음.
	private String msId;
	private String msName;
	private String title;
	private String dates;  //가져올땐 to_char로 가져와야함.
	private String location;
	private String contents;
	private String proName;
	private String process;
	private String opName;
	private String open;
	private String loName;
	private String loop;
	private List<MultipartFile> sdFile;
	private List<SdDetailBean> stickerPath;

}
