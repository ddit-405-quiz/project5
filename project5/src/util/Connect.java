package util;

import java.sql.*;

public class Connect {

	public static Connection getConnection() {
		
		Connection conn = null;
		
		try{
			
			String driver = "oracle.jdbc.driver.OracleDriver";	
			String url = "jdbc:oracle:thin:@192.168.145.29:1521:xe";  
			String user = "project5";
			String password = "java";
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			
		}catch(ClassNotFoundException e) {
			System.out.println("DB드라이버 로딩 실패 : " + e.toString());
		}catch(SQLException e) {
			System.out.println("DB 접속 실패 : " + e.toString());
		}catch(Exception e) {
			System.out.println("unknown error...");
			e.printStackTrace();
		}
		
		return conn;
	}
}
