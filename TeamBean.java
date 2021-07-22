package one.services.beans;

import java.util.List;

import lombok.Data;

@Data
//@NoArgsConstructor
public class TeamBean {

	
	private String tCode;
	private String tName;
	private List<TDetailBean> tdetails; //프론트에서 name이 같은 아이들
}
