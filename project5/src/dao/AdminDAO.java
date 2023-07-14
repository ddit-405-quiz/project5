 package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class AdminDAO {
	
	private static AdminDAO instance = null;
	private AdminDAO() {}
	public static AdminDAO getInstance() {
		if (instance == null)
			instance = new AdminDAO();
		return instance;
	}

	JDBCUtil jdbc = JDBCUtil.getInstance();

	public Map<String, Object> getAdminInfo(String admin_NO) {
		return jdbc.selectOne("SELECT * FROM ADMIN " + "WHERE ADMIN_NO = " + admin_NO);
	}

	public Map<String, Object> adminlogin(List<Object> param) {
		String sql = "SELECT * FROM ADMIN " + "WHERE ADMIN_ID = ? AND ADMIN_PW = ?";
		return jdbc.selectOne(sql, param);
	}

}