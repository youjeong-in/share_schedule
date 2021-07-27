package one.schedule.project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	
	
}
