package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class UserDAO {
	private static UserDAO instance = null;
	private UserDAO () {}
	public static UserDAO getInstance() {
		if(instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}
	
JDBCUtil jdbc = JDBCUtil.getInstance();
	
	//로그인
	public Map<String, Object> login(List<Object> param) { 
		String sql = "SELECT * FROM USERS "
				+ "WHERE USER_ID = ? AND USER_PW = ?";	
		return jdbc.selectOne(sql, param); 
	}
	
	//회원가입
	public int signUp(String name, String userId, String userPw, String userTel) {
		String sql = "INSERT INTO USERS(USER_NO, USER_ID, USER_PW, USER_PH, USER_NAME)";
		sql = sql + "VALUES(fn_create_user_no, ?, ?, ?, ?)";
		
		List<Object> param = new ArrayList<Object>();
		param.add(userId);
		param.add(userPw);
		param.add(userTel);
		param.add(name);
		
		return jdbc.update(sql, param);
	}
}