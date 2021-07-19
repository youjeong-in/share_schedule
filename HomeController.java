package one.schedule.project;



import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import one.schedule.util.Encryption;
import one.schedule.util.ProjectUtil;
import one.services.auth.Authentication;
import one.services.beans.AccessInfo;
import one.services.beans.UserBeans;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	Authentication auth;
	@Autowired
	ProjectUtil pu;
	@Autowired
	Encryption enc;

	ModelAndView mav =null;

	@RequestMapping(value = {"/","/logIn"}, method = {RequestMethod.GET,RequestMethod.POST})//get,post 둘다사용경우
	public ModelAndView logInForm() {
		mav = new ModelAndView();
		
		try {
			if(pu.getAttribute("userId") != null) { //Session이 생성되어있는 경우
				mav.setViewName("dashboard");
				mav.addObject("userId" , enc.aesEncode((String)pu.getAttribute("userId"), "innew"));
				mav.addObject("uName", enc.aesDecode((String)pu.getAttribute("uName"),(String) pu.getAttribute("userId")));
				
				
			}else {
				mav.setViewName("logIn");}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return mav;
	}

	@GetMapping("/signUpForm") //get방식 a태그 눌렀을때 오는 잡코드
	//@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public String signUpForm() {
		return "signUp";
	}


	@PostMapping("/Access") //post방식
	//@RequestParam("userId") String uId
	public ModelAndView logIn(@ModelAttribute AccessInfo ai){ //한가지로 여러개를 불러올수있음
		mav=auth.logInCtl(ai);
		return mav;

	}


	@PostMapping("/signUp") 
	public ModelAndView signUp(@ModelAttribute UserBeans ub){
		mav=auth.joinCtl(ub);
		return mav;
	}

	//중복체크
	@PostMapping("/isDup") 
	@ResponseBody  
	public String isDuplicateCheck(@ModelAttribute AccessInfo ai){
		return auth.isDupCheck(ai);

	}

	@PostMapping("/logOut")	
	public ModelAndView logOut(@ModelAttribute AccessInfo ai) {
		mav = auth.logOut(ai);

		return mav;
	}

}




