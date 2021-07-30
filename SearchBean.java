package one.services.beans;

import lombok.Data;

@Data
public class SearchBean {

	private String word;
	private String userId;
	private String userPass;
	private String userName;
	private String userPhone;
	private String userMail;
	private String mailAdd;
	private String myId; //friend신청할때 request아이디

}
