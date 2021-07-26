package one.services.friends;


import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import one.database.mapper.FriendsInterface;
import one.schedule.util.Encryption;
import one.schedule.util.ProjectUtil;
import one.services.beans.TDetailBean;
import one.services.beans.TeamBean;

@Service
public class FriendsRelation implements FriendsInterface{

	@Autowired
	Encryption enc;
	@Autowired
	ProjectUtil pu;
	@Autowired 
	SqlSessionTemplate sqlSession;

	public FriendsRelation() {

	}


	public List<TeamBean> getTeamList(TeamBean tb){

		List<TeamBean> list;
		list = sqlSession.selectList("getTeamList", tb);
		//System.out.println(list);

		return list;
	}


	public List<TDetailBean> getMemberList(TDetailBean tdb) {

		List<TDetailBean> memberList;
		memberList = sqlSession.selectList("getMemberList", tdb);

		for(int index = 0; index < memberList.size(); index++) {
			try {
				memberList.get(index).setMsName(enc.aesDecode(memberList.get(index).getMsName(), memberList.get(index).getMsId()));

			} catch (Exception e) {

				e.printStackTrace();
			} 
		}
		//System.out.println(memberList);
		return memberList;
	}

	@Transactional(rollbackFor = Exception.class) //그중에서 하나가 안됐으면 rollback해주세요
	public List<TeamBean> addTeam(TeamBean tb) {
		
	
		
		//Select 오늘날짜의 몇번까지 만들어졌는지 확인
		if(this.getCount()!=null) {
			tb.setTCodeNum(getCount());

			//T 테이블에 추가
			if(this.insTeam(tb)) {
				try {
					tb.setMsId((String)pu.getAttribute("userId"));
					tb.setTCode(this.getNewCode());

					System.out.println(tb);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//TD 테이블에 추가
				if(this.insMb(tb)) {
					System.out.println("성공");
				}
			}
		}

		//getTeamList 호출

		return this.getTeamList(tb);
	}

//오늘날짜 몇번까지 만들어졌는지 확인 + 숫자 증가
	public String getCount() {
		
		int number;

		if(sqlSession.selectOne("getCount")!=null) {
			number = sqlSession.selectOne("getCount");
		}else {
			number = 0;
		}
		 String result = (number+1) + ""; 
		 
		 	for(int add = result.length(); add<3; add++) {
				result = "0" + result;
			}

		return result;
	}

	public String getNewCode() {

		return sqlSession.selectOne("getNewCode");
	}

	public boolean insTeam(TeamBean tb) {
		return this.convert(sqlSession.insert("insTeam", tb));
	}

	public boolean insMb(TeamBean tb) {
		return this.convert(sqlSession.insert("insMb", tb));
	}

	public boolean convert(int data) {
		return (data > 0 ) ? true : false;
	}
}
