package service;

import java.util.Map;

import dao.ItemDAO;

public class ItemService {
	
	private static ItemService instance = null;
	private ItemService() {}
	public static ItemService getInstance() {
		if(instance == null) instance = new ItemService();
		return instance;
	}
	
	ItemDAO itemDAO = ItemDAO.getInstance();

	// itemNo의 아이템의 수량을 체크해주는 메소드
	public Map<String, Object> checkItem(String userNo) {
		String sql = "SELECT * FROM ITEM " +
					 "WHERE USER_NO = " + userNo;
		return itemDAO.checkItem(sql);
	}
	
	// 매개변수 2개를 입력받고 itemNo의 아이템을 quantity만큼 더함
	public void setUserItem(int itemNo, int quantity, String userNo) {
		
		String itemName = "";
		switch(itemNo) {
		case 1:
			itemName = "ITEM_DOUBLE";
			break;
		case 2:
			itemName = "ITEM_HINT";
			break;
		case 3:
			itemName = "ITEM_LIFE";
			break;
		}
		
		String sql =  "UPDATE ITEM" +
				  	  " SET " + itemName + " = " + itemName + " + " + quantity +
				  	  " WHERE USER_NO = " + userNo;
	}
}
