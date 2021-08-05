package one.database.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import one.services.beans.AccessInfo;
import one.services.beans.UserBeans;

@Component
public interface AuthInterface {
	
	
	int getEncPwd(AccessInfo ai); 
	boolean isUserId(AccessInfo ai); //for 중복체크
	boolean isAccess(AccessInfo ai);
	boolean insHistory(AccessInfo ai);
	boolean insMember(UserBeans ub);
	List<UserBeans> getUserInfo(AccessInfo ai);
	int checkBrowser(AccessInfo ai); 
	boolean isCurrentAccess(AccessInfo ai);
	boolean forceLogOut(AccessInfo ai);
	String getTcode(AccessInfo ai);
}
