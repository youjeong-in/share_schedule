package one.database.mapper;

import org.springframework.stereotype.Component;

import one.services.beans.AccessInfo;
import one.services.beans.UserBeans;

@Component
public interface AuthInterface {
	
	public int isUserId(AccessInfo ai);
	public int isAccess(AccessInfo ai);
	public int insHistory(AccessInfo ai);
	public int insMember(UserBeans ub);

}
