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
	
	// itemNo의 아이템의 수량을 체크해주는 메소드
	public Map<String, Object> checkItem(String sql) {
		return jdbc.selectOne(sql);
	}
	
	public int increaseItem(String sql) {
		return jdbc.update(sql);
	}
	
	public int decreaseItem(int itemCode) {
		
		String itemName = "";
		switch(itemCode) {
		case View.ITEM_DOUBLE:
			itemName = "ITEM_DOUBLE";
			break;
		case View.ITEM_HINT:
			itemName = "ITEM_HINT";
			break;
		case View.ITEM_LIFE:
			itemName = "ITEM_LIFE";
			break;
		}
		
		String sql = "UPDATE ITEM" + " SET " + itemName + " = " + itemName + " - 1 WHERE USER_NO = "
				    + gameManager.getUserInfo().get("USER_NO");
		
		return jdbc.update(sql);
	}
}
