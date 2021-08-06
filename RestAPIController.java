package one.schedule.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import one.services.beans.MailBean;
import one.services.beans.SearchBean;
import one.services.beans.TDetailBean;
import one.services.beans.TeamBean;
import one.services.friends.FriendsRelation;

//특정부분만 바꿀때 / 페이지 변화 없음 Responsebody 따로 안써줘도 되는 컨트롤러
@RestController 
@RequestMapping("/schedule")
public class RestAPIController {

	@Autowired
	FriendsRelation fr;
	
	@PostMapping("/teamList")
	public List<TeamBean> getTeamList(@RequestBody List<TeamBean> list){
		//System.out.println("Restcontroller진입");
		
		return fr.getTeamList(list.get(0));
		
	}
	
	
	@PostMapping("/memberList")
	public List<TDetailBean> getMemberList(@RequestBody List<TDetailBean> list){
		
		//System.out.println(list.get(0).getTCode());
		return fr.getMemberList(list.get(0));
		
	}
	
	@PostMapping("/addTeam")
	public List<TeamBean> addTeam(@RequestBody List<TeamBean> list){
		
		return fr.addTeam(list.get(0));
		
	}
	
	@PostMapping("/friendsList")
	public List<TDetailBean> friendsList (@RequestBody List<TDetailBean> list){
		
		return fr.getFriends(list.get(0));
	}
	
	@PostMapping("/sendMail")
	public List<TDetailBean> sendMail (@RequestBody List<TeamBean> list){
		
		System.out.println(list.get(0).getTdetails());
		return fr.addMember(list.get(0));
	}
	
	@PostMapping("/search")
	public List<SearchBean> search (@RequestBody List<SearchBean> sb){
		//System.out.println(sb.get(0).getWord());
		return fr.search(sb.get(0));
	}
	
	//기존에 있던 친구에게 친구신청
	@PostMapping("/askFriend")
	public boolean askFriend (@RequestBody List<SearchBean> list){
		return fr.askFriend(list.get(0));
	}
	
	//새친구 초대메일보내기
	@GetMapping("/askMail")
	public Map<String, String> askMail (@ModelAttribute MailBean mb){
		//System.out.println(mb.getTo());
		return fr.askMail(mb);
	}
	
	
	
	
	
	
}
