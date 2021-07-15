package one.services.auth;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import one.database.mapper.AuthInterface;
import one.services.beans.AccessInfo;
import one.services.beans.UserBeans;

@Repository
public class AuthDAO{

	@Autowired //이미싱글톤으로 올라와있는 녀석을 연결한것이다!
	SqlSessionTemplate sqlSession;
	

	boolean isUserId(AccessInfo ai) {
		return this.convert(sqlSession.selectOne("isUserId" , ai));
	
	}

	
	boolean isAccess(AccessInfo ai) {
		return  this.convert(sqlSession.selectOne("isAccess" , ai));

	}

	
	boolean insHistory(AccessInfo ai) {
		return this.convert(sqlSession.insert("insHistory", ai));
	}

	
	boolean insMember(UserBeans ub) {
		return this.convert(sqlSession.insert("insMember", ub));
			
	}
	
	List<UserBeans> getUserInfo(AccessInfo ai){
		return sqlSession.selectList("getUserInfo", ai);
	}
	


	//컨버트
	private boolean convert(int value) {
		return (value>0)? true : false;
	}

}
