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
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import one.database.mapper.AuthInterface;
import one.schedule.util.Encryption;
import one.schedule.util.ProjectUtil;
import one.services.beans.AccessInfo;
import one.services.beans.SearchBean;
import one.services.beans.TDetailBean;
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

	@Autowired
	JavaMailSenderImpl javaMail;

	private ModelAndView mav = null;
	AuthInterface auth;

	HttpSession session;


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
		StringBuffer tCode = new StringBuffer();
		StringBuffer tName = new StringBuffer();


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
								//mav.addObject("stickerPath", dao.getUserInfo(ai).get(0).getStickerPath());
								pu.setAttribute("uName", dao.getUserInfo(ai).get(0).getUserName());
								pu.setAttribute("stickerPath", dao.getUserInfo(ai).get(0).getStickerPath());

								for(int i=0; i<dao.getTcode(ai).size(); i++) {
									tCode.append(dao.getTcode(ai).get(i).getTCode()+",");
									tName.append(dao.getTcode(ai).get(i).getTName()+",");
								}

								//System.out.println(tCode.toString());
								//System.out.println(tName.toString());
								pu.setAttribute("tCode", tCode.toString().substring(0,tCode.toString().length()-1));
								pu.setAttribute("tName", tName.toString().substring(0,tName.toString().length()-1));


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



		if(ub.getMpFile().isEmpty()) { //비어있다면
			System.out.println("넘어온 파일이 없다.");
			ub.setStickerPath("");
		}else {
			System.out.println("파일 있다.");
			ub.setStickerPath("resources/image/"+pu.savingFile(ub.getMpFile()));
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
							mav.addObject("stickerPath", dao.getUserInfo(ai).get(0).getStickerPath()); //프로필사진 경로
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

	//모든 멤버들을 불러옴


	public ModelAndView findId(UserBeans ub) {
		List<UserBeans> allMs;
		allMs= dao.allmembers();

		//모든멤버를 가져와서 이름과 이메일을 복호화했다.
		for(int i=0; i<allMs.size(); i++) {
			try {
				allMs.get(i).setUserName(enc.aesDecode(allMs.get(i).getUserName(), allMs.get(i).getUserId()));
				allMs.get(i).setUserMail(enc.aesDecode(allMs.get(i).getUserMail(), allMs.get(i).getUserId()));

				if(allMs.get(i).getUserName().equals(ub.getUserName()) && allMs.get(i).getUserMail().equals(ub.getUserMail())) {
					ub.setUserName(allMs.get(i).getUserName());
					ub.setUserMail(allMs.get(i).getUserMail());
					ub.setUserId(allMs.get(i).getUserId());
					this.sendMail(ub);	
					System.out.println("성공");

				}else if(!allMs.get(i).getUserName().equals(ub.getUserName()) || !allMs.get(i).getUserMail().equals(ub.getUserMail())) {
					mav.setViewName("idForget");
					mav.addObject("message", "회원정보가 일치하지않습니다.");
					System.out.println("실패");
				}
			} catch (Exception e) {
				e.printStackTrace();} 
		}

		mav.setViewName("idForget");
		mav.addObject("message", "메일을 전송했습니다.");
		return mav;
	}

	public ModelAndView findPwd(UserBeans ub) {

		return null;
	}


	private void sendMail(UserBeans ub) {
		String subject = "[ONE]회원정보 찾기를 위한 인증안내입니다. ";
		String content = "<a href='http://192.168.219.199/logIn'>회원님의 아이디는 '" +ub.getUserId() + "' 입니다.</a>";

		String from = "i_innew0731@naver.com";
		String to = ub.getUserMail();

		MimeMessage mail = javaMail.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail, "UTF-8");


		try {
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content,true);


			javaMail.send(mail);
		} catch (MessagingException e) {

			e.printStackTrace();
		}
	}

}
