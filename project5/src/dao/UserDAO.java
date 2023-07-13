package dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import service.LoginService;
import service.UserService;
import util.JDBCUtil;
import util.ScanUtil;

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

	public  Map<String, Object> getUserInfo(String user_NO){
		return jdbc.selectOne("SELECT * FROM USERS " + 
							  "WHERE USER_NO = " + user_NO);
	}

	//idȮ��
	public boolean checkId(String memId) {
		String sql = "SELECT USER_NAME FROM USERS WHERE USER_ID = ?";
		Map<String, Object> result = jdbc.selectOne(sql, Collections.singletonList(memId));
		
		return result != null;
	}
	
	//�α���
	public Map<String, Object> login(List<Object> param) { 
		String sql = "SELECT * FROM USERS "
				+ "WHERE USER_ID = ? AND USER_PW = ?";	
		return jdbc.selectOne(sql, param); 
	}
	
	
	//ȸ������
	public int signUp(String name, String userId, String userPw, String userTel) {
		String sql = "INSERT INTO USERS(USER_NO, USER_ID, USER_PW, USER_PH, USER_NAME, USER_GM, USER_SCORE)";
		sql = sql + "VALUES(fn_create_user_no, ?, ?, ?, ?, 100, 0)";
		
		List<Object> param = new ArrayList<Object>();
		param.add(userId);
		param.add(userPw);
		param.add(userTel);
		param.add(name);
		
		return jdbc.update(sql, param);
	}


	public void setUserScore(String sql) {
		jdbc.update(sql);
	}
	
	public void setUserGameMoney(String sql) {
		jdbc.update(sql);
	}
	
	public int update(String str, String userid) {
		String sql = "UPDATE tbl_member SET ";
		sql = sql + str;
		sql = sql + "WHERE MID = " + "'" + userid + "'";
	}
	
		
		
//		USER_ID
//		USER_PW
//		USER_PH
//		USER_NAME
}