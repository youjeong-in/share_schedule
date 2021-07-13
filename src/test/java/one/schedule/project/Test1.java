package one.schedule.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

import lombok.extern.log4j.Log4j;

//db가 연결이되는지.
@Log4j
public class Test1 {

	@Test
	public void Test() {
		String conInfo = "jdbc:oracle:thin:@106.243.194.229:9999:xe";
		String Id = "INNEW";
		String Pwd = "1234";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection = DriverManager.getConnection(conInfo,Id,Pwd);
			log.info(connection);
			System.out.println(connection);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		
		} catch (SQLException e) {
			e.printStackTrace();
	}
		
		
	}
}
