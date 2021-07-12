package one.schedule.project;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import one.services.beans.UserBeans;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = {"/","/logIn"}, method = {RequestMethod.GET,RequestMethod.POST})//get,post 둘다사용경우
	public String logInForm() {
		
		return "logIn";
	}
	
	@GetMapping("/signUp") //get방식
	//@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public String signUpForm() {
	
		
		return "signUp";
	}
	
	@PostMapping("/Access") //post방식
	public String logIn(@ModelAttribute UserBeans ub){
		System.out.println(ub.getUserId() + " : " + ub.getUserpass());
	//(@RequestParam("userId") String uId, @RequestParam("userPwd") String upw)
	//System.out.println(uId + ":" + upw);
		
		return "logIn";
	}
	
	@PostMapping("/Access2") //같은 name으로 받아오기
	public String logIn2(@RequestParam("user") ArrayList<String> list){
		System.out.println(list.get(0) + " : " + list.get(1));
		
		return "logIn";
	}

	
}
