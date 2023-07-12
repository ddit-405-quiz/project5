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
	
	// itemNo�� �������� ������ üũ���ִ� �޼ҵ�
	public Map<String, Object> checkItem(String sql) {
		return jdbc.selectOne(sql);
	}
}
