package one.database.mapper;

import java.util.List;

import one.services.beans.SearchBean;
import one.services.beans.TDetailBean;
import one.services.beans.TeamBean;
import one.services.beans.UserBeans;

public interface FriendsInterface {
	
	public List<TeamBean> getTeamList(TeamBean tb);
	public List<TDetailBean> getMemberList(TDetailBean tdb);
	public String getCount(); //오늘 날짜의 몇번팀까지 만들어졌는지 확인하는 메서드
	public boolean insTeam(TeamBean tb);
	public String getNewCode();
	public boolean insMb(TeamBean tb);
	public List<TDetailBean> getFriends(TDetailBean td);
	public String getMail(TDetailBean tdb);
	public boolean insGeneral(TeamBean tb);
	public List<SearchBean> search(SearchBean sb);
	public List<SearchBean> allmember();
	public boolean askFr(SearchBean sb);
	public boolean acceptFr(SearchBean sb);
	
}

