package one.services.beans;

import java.util.List;

import lombok.Data;

@Data
//@NoArgsConstructor
public class TeamBean {

	
	private String tCode;
	private String tCodeNum;
	private String tName;
	private String msId;
	private String email;
	private String myId;
	private List<TDetailBean> tdetails;
	
	
    
}
