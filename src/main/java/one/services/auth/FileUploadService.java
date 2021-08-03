package one.services.auth;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

	private static final String SAVE_PATH = "/FireBase/Source";

	
	
	public void restore(MultipartFile file) {
		
		try {
			String originFilename = file.getOriginalFilename();
			
			String extName = originFilename.substring(originFilename.lastIndexOf("."),originFilename.length());
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
			Calendar cal = Calendar.getInstance();
			String saveFileName ="upload" + sdf.format(cal.getTime())+extName;
			
			byte[] data = file.getBytes();
			FileOutputStream fos = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
			fos.write(data);
			fos.close();
			System.out.println("성공");
			
		}catch(Exception e) {
			System.out.println("실패");
		}
		
	}

}
