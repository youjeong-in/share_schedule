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
	public ModelAndView logInForm(AccessInfo ai) {
		
		return auth.rootCtl(ai);
	}

	@GetMapping("/signUpForm") //get방식 a태그 눌렀을때 오는 잡코드
	//@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public ModelAndView signUpForm() {
		return auth.joinForm();
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
	
	@GetMapping("/scheduleManage")
	public String scheduleManage () {
		
		return "scheduleManage";
	}
	
	@GetMapping("/teamManage")
	public String teamManage () {
		
		return "teamManage";
	}
	
	@GetMapping("/back")
	public String dashboard() {
		return "dashboard";
	}
	

	

}




