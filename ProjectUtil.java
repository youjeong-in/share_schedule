package one.schedule.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ProjectUtil {
	
	/* RequestContetHolder Class --> Spring 3.0 이상
	 *  : ThreadLocal를 사용해서 현재 쓰레드에 RequestAttributes 인스턴스를 바인딩 해두었다가 요청을 하면 이 인스턴스를 돌려주는 역할을 합니다.
	 *  : ThreadLocal을 사용하는 경우 ThreadLocal 변수에 보관된 데이터의 사용이 끝나면 반드시 삭제 해야 재사용되는 쓰레드의 잘못된 참조를 방지
	 *    그렇지 않으면, 재사용되는 쓰레드가 올바르지 않은 데이터를 참조할 수 있다.
	 *    
	 * org.springframework.mobile   spring-mobile-device
	 */
	
	public int screenType(int serviceCode){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return serviceCode * (isMobile(request)? -1 : 1);
	}
	
	private boolean isMobile(HttpServletRequest request){
		return DeviceUtils.getCurrentDevice(request).isMobile();
	}
	
	/* Session영역으로부터 attribute 값을 가져 오기 위한 method  */
    public Object getAttribute(String name) throws Exception {
        return (Object) RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
    }
 
    /* Session영역에 attribute 설정 method */
    public void setAttribute(String name, Object object) throws Exception {
        RequestContextHolder.getRequestAttributes().setAttribute(name, object, RequestAttributes.SCOPE_SESSION);
    }
 
    /* Session영역에 설정된 attribute 삭제 */
    public void removeAttribute(String name) throws Exception { //Session 삭제 끊기
        RequestContextHolder.getRequestAttributes().removeAttribute(name, RequestAttributes.SCOPE_SESSION);
    }
 
    /* Session영역의 SessionId값을 가져오기 위한 Method */
    public String getSessionId() throws Exception  {
        return RequestContextHolder.getRequestAttributes().getSessionId();
    }
    
    /*upLoad된 파일을 Local Repository에 저장하는 메서드 : savingFile(MultipartFile) ,savingFile(MultipartFile[])*/
    public String savingFile(MultipartFile file) {
    	String fileInfo =null;
    	String uploadFileLocation = "C:" + File.separator + "FireBase" + File.separator + "SpringWorkSpace" +  File.separator + "schedule" +  File.separator
    			+ "src" +  File.separator + "main" +  File.separator + "webapp" +  File.separator + "resources" +  File.separator + "image" +  File.separator;
    	
    	
    	
   /* 중복을 피해서 파일이름 저장 //대부분 : 파일이 넘어오면 그 파일이름을 겹치지않게 임의의이름을 저장한다.(uuId) 우리 : 파일이름 primaryKey로 저장*/
    	UUID uuid = UUID.randomUUID(); //내장형이여서 import하지않아도된다.
    	String savingFileName = uuid + "_" +file.getOriginalFilename(); //파일이름 정책 정하기

    	// storage 에 실제 파일을 저장하기 위해 자바의 파일 객체 생성
    	File sfile = new File(uploadFileLocation, savingFileName);
    	try {
			file.transferTo(sfile);
			fileInfo = savingFileName;
		} catch (IllegalStateException e) {
		
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

    	return fileInfo;
    }
 
}
