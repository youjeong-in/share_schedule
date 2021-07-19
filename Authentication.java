package one.services.auth;


import java.io.UnsupportedEncodingException;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import one.database.mapper.AuthInterface;
import one.schedule.util.Encryption;
import one.schedule.util.ProjectUtil;
import one.services.beans.AccessInfo;
import one.services.beans.UserBeans;


@Service
public class Authentication{
	@Autowired
	AuthDAO dao;

	@Autowired
	Gson gson;

	@Autowired
	Encryption enc;

	@Autowired
	ProjectUtil pu;

	private ModelAndView mav = null;
	AuthInterface auth;

	HttpSession session;


	//로그인
	public ModelAndView logInCtl(AccessInfo ai) {
		boolean check=false;
		mav = new ModelAndView();

		String encPwd; //암호화된 비번
		encPwd=dao.getEncPwd(ai);

		try {
			if(pu.getAttribute("logout")!= null && (boolean) pu.getAttribute("logout")) {//로그아웃되어있다.
				pu.setAttribute("logout", false);//로그아웃을 해줌.
				mav.setViewName("redirect:/"); // 페이지를 되돌려보낸당.
				mav.addObject("message","이미 로그아웃되었습니다.");
			}else {

				if(pu.getAttribute("userId")==null) {//세션이 안되있고, 
					if(check = (encPwd != null)) {//아이디가 null이 아니고(암호화된 비번이 null이 아니고),
						if(check = enc.matches(ai.getUserPass(), encPwd)) { //암호화된 비번이랑, 사용자비번이랑 비교
							if(check = dao.insHistory(ai)) { //로그기록이 insert 됐다면
								mav.setViewName("dashboard");
								pu.setAttribute("userId", ai.getUserId()); //Session 생성! 

								mav.addObject("userId" , enc.aesEncode(ai.getUserId(), "innew")); //로그인할때 아이디 암호화
								mav.addObject("uName" , enc.aesDecode(dao.getUserInfo(ai).get(0).getUserName(), ai.getUserId()));//복호화한 데이터를 setAttribute
								pu.setAttribute("uName", dao.getUserInfo(ai).get(0).getUserName());

							}
						}
					}
				}
				if(!check) {
					try {
						mav.setViewName("logIn");
						mav.addObject("message", "아이디나 비밀번호가 일치하지 않습니다.");
					}catch(Exception e) {e.printStackTrace();}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();}

		return mav;
	}	



	public ModelAndView logOut(AccessInfo ai) {
		boolean check = false;
		mav = new ModelAndView();

		try {
			ai.setUserId(enc.aesDecode(ai.getUserId(), "innew")); //로그아웃할때, 복호화
			if(pu.getAttribute("userId")!=null) { //Session이 null이 아니라면 즉, 살아있다면.

				while(!check) {
					check = dao.insHistory(ai); //insert하고.. 좋은 방법은 아님! 
					System.out.println(check);
				}

				pu.removeAttribute("userId"); // Session 지우고
				pu.setAttribute("logout", true); //로그아웃이 됐다 안됐다를 구분하기위해서
				mav.setViewName("redirect:/"); // 모든 정보를 단절시켜버리는것이다.	
				mav.addObject("message", "정상적으로 로그아웃이 되었습니다.");
			}else { //로그인 기록이 0임
				mav.setViewName("redirect:/");
				mav.addObject("message", "이미 로그아웃 되었습니다.");
			}

		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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


		try {
			ub.setUserMail(ub.getUserMail()+ub.getMailAdd());
			ub.setUserName(enc.aesEncode(ub.getUserName(), ub.getUserId()));//이름 암호화
			ub.setUserPhone(enc.aesEncode(ub.getUserPhone(), ub.getUserId()));//폰번호 암호화
			ub.setUserMail(enc.aesEncode(ub.getUserMail(), ub.getUserId())); //이메일 암호화

		} catch (InvalidKeyException e) {

			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		} catch (NoSuchPaddingException e) {

			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {

			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {

			e.printStackTrace();
		} catch (BadPaddingException e) {

			e.printStackTrace();
		}

		ub.setUserPass(enc.encode(ub.getUserPass())); //사용자가 입력한 비번을 가져와서 암호화된 비밀번호로 encode해서 set함
		if(dao.insMember(ub)) { //인서트가 됐으면
			mav.setViewName("logIn");
			mav.addObject("message", "회원가입을 축하합니다.");
		}

		return mav;
	}

}