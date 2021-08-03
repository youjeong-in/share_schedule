package one.services.auth;


import java.io.FileOutputStream;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

	private static final String SAVE_PATH = "/FireBase/Source";

	public ModelAndView rootCtl(AccessInfo ai) {
		mav = new ModelAndView();


		String view = "logIn";

		try {
			if(pu.getAttribute("userId")!=null){
				mav.addObject("userId" , enc.aesEncode((String)pu.getAttribute("userId"),"innew"));
				view = "dashboard";
				//mav.addObject("userId", enc.aesDecode((String)pu.getAttribute("userId"), "innew"));
				mav.addObject("uName", enc.aesDecode((String)pu.getAttribute("uName"),(String) pu.getAttribute("userId")));
				if(ai.getUserId()!=null){
					if(!dao.isCurrentAccess(ai)){
						pu.removeAttribute("userId");
						view="logIn";
						mav.addObject("message", "다른브라우저 로그인 아웃");
					}
				}
			}

		}catch (Exception e) {e.printStackTrace();}
		mav.setViewName(view);
		return mav;
	}

	public ModelAndView joinForm() {
		mav = new ModelAndView();

		mav.setViewName("signUp");
		return mav;
	}

	//로그인
	public ModelAndView logInCtl(AccessInfo ai) {
		boolean check=true;
		mav = new ModelAndView();
		String message = null;
		String encPwd=null; //암호화된 비번


		try {

			if(pu.getAttribute("userId")==null) {//세션이 없고,
				if(dao.isAccess(ai)) { //method 기록이있는가 1 또는 0 isAccess가 true(1)이면
					check = dao.forceLogOut(ai); // 강제종료해라!

					System.out.println("강제종료");

				}
				if(check) {
					encPwd = dao.getEncPwd(ai);
					if(check = (encPwd!= null)){ //아이디가 있어서 암호화된 비번이 null이 아니고
						if(check = enc.matches(ai.getUserPass(), encPwd)) { //암호화된 비번과 사용자가 보낸 비번이 일치하면
							if(check = dao.insHistory(ai)) { //로그인 기록을 남겨졌으면
								pu.setAttribute("userId", ai.getUserId()); //Session 생성! 

								mav.setViewName("dashboard");
								mav.addObject("userId" , enc.aesEncode(ai.getUserId(), "innew")); //로그인할때 아이디 암호화
								mav.addObject("uName" , enc.aesDecode(dao.getUserInfo(ai).get(0).getUserName(), ai.getUserId()));//복호화한 데이터를 setAttribute
								mav.addObject("browser", ai.getBrowser());
								mav.addObject("publicIp", ai.getPublicIp());
								mav.addObject("privateIp", ai.getPrivateIp());

								pu.setAttribute("uName", dao.getUserInfo(ai).get(0).getUserName());



							}
						}
					}
					message = (check)?"로그인 성공" : "로그인 정보를 확인해주세요";
				}else {
					message = "다른 브라우저에서 로그인 되어있습니다.";
				}
			}else {

			}
		}
		catch(Exception e) {e.printStackTrace();}
		finally {
			mav.setViewName("redirect:/");
			mav.addObject("message", message);
			mav.addObject("publicIp", ai.getPublicIp());
			mav.addObject("privateIp", ai.getPrivateIp());
			mav.addObject("browser", ai.getBrowser());
			mav.addObject("userId", ai.getUserId());
		}
		return mav;

	}	



	public ModelAndView logOut(AccessInfo ai) {
		boolean check = false;
		mav = new ModelAndView();

		try {
			ai.setUserId(enc.aesDecode(ai.getUserId(), "innew")); //로그아웃할때, 아이디복호화
			if(pu.getAttribute("userId")!=null) { //Session이 null이 아니라면 즉, 살아있다면.

				if(dao.isCurrentAccess(ai)) {

					while(!check) {
						check = dao.insHistory(ai); //insert하고.. 좋은 방법은 아님! 
					}
				}
				pu.removeAttribute("userId"); // Session 지우고
				mav.setViewName("redirect:/"); // 모든 정보를 단절시켜버리는것이다.	
				mav.addObject("message", "정상적으로 로그아웃이 되었습니다.");
			}else { //이미 로그아웃되었는데 다른페이지에서 또 로그아웃 버튼을 눌렀을떄
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


	//회원가입 + 파일 업로드 작업
	public ModelAndView joinCtl(UserBeans ub) {
		mav = new ModelAndView();

		mav.setViewName("signUp");
		mav.addObject("message", "회원가입에 실패했습니다. 다시 시도해주세요");
		String extName = null;


		if(ub.getMpFile().isEmpty()){
			System.out.println("값이없다.");
			ub.setStickerPath("");
			
		}else {
			String originalName = ub.getMpFile().getOriginalFilename();
			extName = originalName.substring(originalName.lastIndexOf("."), originalName.length());
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
			Calendar cal = Calendar.getInstance();

			ub.setStickerPath(SAVE_PATH+ "/" +ub.getUserId()+sdf.format(cal.getTime()));
			try {
				byte[] data = ub.getMpFile().getBytes();

				FileOutputStream fos = new FileOutputStream(SAVE_PATH+ "/" +ub.getUserId()+sdf.format(cal.getTime()) + extName);

				fos.write(data);
				fos.close();
				System.out.println("성공");
				System.out.println(SAVE_PATH+ "/" +ub.getUserId()+sdf.format(cal.getTime()));
			} catch (IOException e1) {

				e1.printStackTrace();
			} 

		}



		try {
			ub.setUserMail(ub.getUserMail()+ub.getMailAdd());
			ub.setUserName(enc.aesEncode(ub.getUserName(), ub.getUserId()));//이름 암호화
			ub.setUserPhone(enc.aesEncode(ub.getUserPhone(), ub.getUserId()));//폰번호 암호화
			ub.setUserMail(enc.aesEncode(ub.getUserMail(), ub.getUserId())); //이메일 암호화

		} catch (Exception e) {

			e.printStackTrace();
		} 

		ub.setUserPass(enc.encode(ub.getUserPass())); //사용자가 입력한 비번을 가져와서 암호화된 비밀번호로 encode해서 set함
		if(dao.insMember(ub)) { //인서트가 됐으면
			mav.setViewName("logIn");
			mav.addObject("message", "회원가입을 축하합니다.");
		}

		return mav;
	}

	//Link 타고 인증타고 들어왔을때, 로그인
	public ModelAndView linkAccess(AccessInfo ai) {
		boolean check=true;
		mav = new ModelAndView();
		String message = null;
		String encPwd=null; //암호화된 비번

		if(check) {
			encPwd = dao.getEncPwd(ai);
			if(check = (encPwd!= null)){ //아이디가 있어서 암호화된 비번이 null이 아니고
				if(check = enc.matches(ai.getUserPass(), encPwd)) { //암호화된 비번과 사용자가 보낸 비번이 일치하면
					if(check = dao.insHistory(ai)) { //로그인 기록을 남겨졌으면
						try {
							pu.setAttribute("userId", ai.getUserId());//Session 생성! 

							mav.setViewName("certification");
							mav.addObject("userId" , ai.getUserId()); //로그인할때 아이디 암호화
							mav.addObject("uName" , enc.aesDecode(dao.getUserInfo(ai).get(0).getUserName(), ai.getUserId()));//복호화한 데이터를 setAttribute
							mav.addObject("tCode",ai.getTCode());
							mav.addObject("publicIp", ai.getPublicIp());
							mav.addObject("privateIp", ai.getPrivateIp());
							mav.addObject("browser", ai.getBrowser());
							pu.setAttribute("uName", dao.getUserInfo(ai).get(0).getUserName());

						} catch (Exception e) {
							e.printStackTrace();
						} 
					}
				}
			}
			message = (check)?"로그인 성공" : "로그인 정보를 확인해주세요";
		}

		return mav;
	}

}
