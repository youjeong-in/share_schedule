package one.services.auth;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import one.database.mapper.AuthInterface;
import one.services.beans.AccessInfo;
import one.services.beans.UserBeans;


@Service
public class Authentication{
	@Autowired
	AuthDAO dao;
	
	@Autowired
	Gson gson;
	private ModelAndView mav = null;
	AuthInterface auth;
	

	
	
	//로그인
	public ModelAndView logInCtl(AccessInfo ai) {
		boolean check;
		mav = new ModelAndView();
		if(check = dao.isUserId(ai)) {//아이디가 있고
			if(check = dao.isAccess(ai)) { //아이디-비번일치
				if(check = dao.insHistory(ai)) { //로그기록이 insert 됐다면
					mav.setViewName("dashboard");
					mav.addObject("uName", dao.getUserInfo(ai).get(0).getUserName());
					//ArrayList<UserBeans> list = (ArrayList)dao.getUserInfo(ai); //user정보를 가져와서
					
				}
			}
		}
		
		if(!check) {
			mav.setViewName("logIn");
			mav.addObject("message", "아이디나 비밀번호가 일치하지 않습니다.");
		}
	
		return mav;
	}	
	
	
	//중복체크
	public String isDupCheck(AccessInfo ai) {
		
		boolean message = false;
		
		if(!dao.isUserId(ai)) {//아이디가 없으면 사용가능
			message = true;	
		}
	
		return gson.toJson(message);
	}
	
	
	//회원가입
	public ModelAndView joinCtl(UserBeans ub) {
		mav = new ModelAndView();
		
		mav.setViewName("signUp");
		mav.addObject("message", "회원가입에 실패했습니다. 다시 시도해주세요");
		
			if(dao.insMember(ub)) { //인서트가 됐으면
				mav.setViewName("logIn");
				mav.addObject("message", "회원가입을 축하합니다.");
			}
		
		return mav;
	}

}
