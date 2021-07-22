package one.schedule.project;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import one.services.beans.TeamBean;

//특정부분만 바꿀때 / 페이지 변화 없음 Responsebody 따로 안써줘도 되는 컨트롤러
@RestController 

public class RestAPIController {

	TeamBean tb = new TeamBean();
	@PostMapping("/add")
	public List<TeamBean> addFriends(){
		
		return null;
	}
	
}
