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

	// itemNo�� �������� ������ üũ���ִ� �޼ҵ�
	public Map<String, Object> checkItem(String userNo) {
		String sql = "SELECT * FROM ITEM " +
					 "WHERE USER_NO = " + userNo;
		return itemDAO.checkItem(sql);
	}
	
	// �Ű����� 2���� �Է¹ް� itemNo�� �������� quantity��ŭ ����
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
