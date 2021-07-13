package one.services.beans;

import java.util.Date;

import lombok.Data;


//로그인기록용 Bean History Table
@Data
public class AccessInfo {

	private String userId;
	private String userPass;
	private int method; // 1은 로그인 , -1 로그아웃
	private String publicIp;
	private String privateIp;
}
