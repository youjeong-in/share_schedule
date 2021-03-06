package one.schedule.project;




import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import one.schedule.util.Encryption;
import one.schedule.util.ProjectUtil;
import one.services.auth.Authentication;
import one.services.auth.FileUploadService;
import one.services.beans.AccessInfo;
import one.services.beans.ScheduleBean;
import one.services.beans.TeamBean;
import one.services.beans.UserBeans;
import one.services.friends.FriendsRelation;
import one.services.schedule.ScheduleManage;

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
	@Autowired 
	FriendsRelation fr;
	@Autowired
	ScheduleManage sm;
	
	@Autowired
	FileUploadService fus;

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
	
	@PostMapping("/linkAccess") //post방식
	public ModelAndView linkAccess(@ModelAttribute AccessInfo ai){ //한가지로 여러개를 불러올수있음
		System.out.println(ai);
		mav=auth.linkAccess(ai);
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
	//아이디 찾기 페이지
	@GetMapping("/idForget")
	public String idForget () {
		
		return "idForget";
	}
	
   //아이디찾기 데이터 넘어옴
	@PostMapping("/findId")	
	public ModelAndView findId(@ModelAttribute UserBeans ub) {
		//System.out.println(ub);
		return auth.findId(ub);
	}
	

	@PostMapping("/findPwd")	
	public ModelAndView findPwd(@ModelAttribute UserBeans ub) {
		System.out.println(ub);

		return auth.findPwd(ub);
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
		return "redirect:/";
	}
	
	
	@GetMapping("/EmailAuth")
	public ModelAndView emaiAuth(@ModelAttribute TeamBean list) {
		mav = new ModelAndView();
		mav.setViewName("emailAuth");
		mav.addObject("tCode",list.getTCode());
		
		return mav;
	}
	

	@PostMapping("/authConfirm")
	public ModelAndView authConfirm(@ModelAttribute TeamBean list) {
		mav = new ModelAndView();
		return fr.authConfirm(list);
	}

	@PostMapping("/sendFile")
	public ModelAndView sendFile(@ModelAttribute ScheduleBean sb) {
		mav = new ModelAndView();
//		for(int i=0; i<sb.getSdFile().size(); i++) {
//		System.out.println(sb.getSdFile().get(i).getOriginalFilename());
//		}    
	       		
		mav = sm.sendFile(sb);
		return mav;
	}
	
	@PostMapping("/addSd")
	public ModelAndView addSd(@ModelAttribute ScheduleBean sb) {
		System.out.println(sb);
		
			mav = sm.addSd(sb);
		return mav;
	}
	
	@GetMapping("/askMonthSd")
	@ResponseBody //HomeController지만, ajax쓸때 꼭 써줘야함. 데이터만 보낼때 쓰는거 
	public List<ScheduleBean> askMonthSd(@ModelAttribute ScheduleBean sb) {
		return sm.askMonthSd(sb);
	}
	
	@GetMapping("/getDaySd")
	@ResponseBody //HomeController지만, ajax쓸때 꼭 써줘야함. 데이터만 보낼때 쓰는거 
	public List<ScheduleBean> getDaySd(@ModelAttribute ScheduleBean sb) {
		//System.out.println(sb);
		return sm.askDaySd(sb);
	}

}




