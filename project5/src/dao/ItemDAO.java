package dao;

import java.util.Map;

import util.JDBCUtil;

public class ItemDAO {
	
	private static ItemDAO instance = null;
	private ItemDAO() {}
	public static ItemDAO getInstance() {
		if(instance == null) instance = new ItemDAO();
		return instance;
	}
	
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	// itemNo의 아이템의 수량을 체크해주는 메소드
	public Map<String, Object> checkItem(String sql) {
		return jdbc.selectOne(sql);
	}
}
