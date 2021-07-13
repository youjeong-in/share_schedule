package one.services.auth;


import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import one.database.mapper.AuthInterface;
import one.services.beans.AccessInfo;
import one.services.beans.UserBeans;


@Service
public class Authentication{
	private ModelAndView mav = null;
	AuthInterface auth;
	
	//로그인
	public ModelAndView logInCtl(AccessInfo ai) {
//		System.out.println(ai.getUserId() + "," + ai.getUserPass());
//		System.out.println(ai.getPublicIp() + "," + ai.getPrivateIp());
//		System.out.println(ai.getMethod());
		
		
		return mav;
	}	
	
	
	//중복체크
	public String isDupCheck(UserBeans ub) {
		return null;
	}
	
	
	//회원가입
	public ModelAndView joinCtl(UserBeans ub) {
		
		return mav;
	}
	
	//아이디 유무
	private boolean isUserId(AccessInfo ai) {
		return false;
	}
	
	//아이디-비번 일치여부
	private boolean isAccess(AccessInfo ai) {
		
		return false;
	}
	
	//History Table에 ins
	private boolean insHistory(AccessInfo ai) {
		return false;
	}
	
	//사용자 정보
	private String getUserInfo(UserBeans ub) {
		return null;
	}
	
	
	//회원  insert
	private boolean insMember(UserBeans ub) {
		
		return false;
	}
	
	//컨버트
	private boolean convert(int value) {
		return (value>0)? true : false;
	}
}
