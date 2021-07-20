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
	
	
	//암호화된 비밀번호 가져오기
	String getEncPwd(AccessInfo ai) {
		return sqlSession.selectOne("getEncPwd", ai);
	}

	//아이디 유무 중복체크를 위한
	boolean isUserId(AccessInfo ai) {
		return this.convert(sqlSession.selectOne("isUserId" , ai));
	
	}
	//로그인 기록 insert됐는지
	boolean insHistory(AccessInfo ai) {
		return this.convert(sqlSession.insert("insHistory", ai));
	}

	boolean isAccess(AccessInfo ai) {
		return this.convert(sqlSession.selectOne("isAccess", ai));
	}
	
	boolean insMember(UserBeans ub) {
		return this.convert(sqlSession.insert("insMember", ub));
			
	}
	
	List<UserBeans> getUserInfo(AccessInfo ai){
		return sqlSession.selectList("getUserInfo", ai);
	}
	
	boolean checkBrowser(AccessInfo ai) {
		int result = sqlSession.selectOne("checkBrowser", ai);
		
		return this.convert(sqlSession.selectOne("checkBrowser", ai));//true는 1이상- 로그인기록있음 

	}
	


	//컨버트
	private boolean convert(int value) {
		return (value>0)? true : false;
	}

}
