package one.database.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import one.services.beans.AccessInfo;
import one.services.beans.UserBeans;

@Component
public interface AuthInterface {
	
	boolean isUserId(AccessInfo ai);
	boolean isAccess(AccessInfo ai);
	boolean insHistory(AccessInfo ai);
	boolean insMember(UserBeans ub);
	List<UserBeans> getUserInfo(AccessInfo ai);
}
