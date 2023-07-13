package dao;

import java.util.Map;
import util.GameManager;
import util.JDBCUtil;
import util.View;

public class ItemDAO {
	
	private static ItemDAO instance = null;
	private ItemDAO() {}
	public static ItemDAO getInstance() {
		if(instance == null) instance = new ItemDAO();
		return instance;
	}
	
	GameManager gameManager = GameManager.getInstance();
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	// 아이템 수량 체크 쿼리 질문
	public Map<String, Object> checkItem(String sql) {
		return jdbc.selectOne(sql);
	}
	
	// 아이템 수량 증가 업데이트
	public int setUserItem(String sql) {
		return jdbc.update(sql);
	}
}