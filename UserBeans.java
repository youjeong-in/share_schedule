package one.services.beans;


import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UserBeans {

	private String userId;
	private String userPass;
	private String userName;
	private String userPhone;
	private String userMail;
	private String mailAdd;
	private String stickerPath; //저장된 경로 디비에 전달할내용
	private MultipartFile mpFile; //사용자가 보낸 파일을 저장하는 빈, 여러개로 받으면 배열처리
}
