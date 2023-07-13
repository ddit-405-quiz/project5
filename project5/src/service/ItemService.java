package service;

import java.util.Map;

import dao.ItemDAO;
import util.PrintUtil;
import util.ScanUtil;
import util.View;
import util.GameManager;;

public class ItemService {

	private static ItemService instance = null;

	private ItemService() {
	}

	public static ItemService getInstance() {
		if (instance == null)
			instance = new ItemService();
		return instance;
	}

	GameManager gameManager = GameManager.getInstance();
	ItemDAO itemDAO = ItemDAO.getInstance();
	

	// itemNo의 아이템의 수량을 체크해주는 메소드
	public Map<String, Object> checkItem(String userNo) {
		String sql = "SELECT * FROM ITEM " + "WHERE USER_NO = " + userNo;
		return itemDAO.checkItem(sql);
	}

	// 매개변수 2개를 입력받고 itemNo의 아이템을 quantity만큼 더함
	public void setUserItem(int itemNo, int quantity, String userNo) {

		String itemName = "";
		switch (itemNo) {
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

		String sql = "UPDATE ITEM" + " SET " + itemName + " = " + itemName + " + " + quantity + " WHERE USER_NO = "
				+ userNo;
		itemDAO.increaseItem(sql);
	}

	// 아이템 사용하는 메소드
	public void useItem() {
		PrintUtil.bar();
		PrintUtil.centerAlignment("아이템 1개 선택");
		PrintUtil.bar2();
		String userNo = UserService.getInstance().getUserInfo().get("USER_NO").toString();
		PrintUtil.centerAlignment("점수2배  : " + checkItem(userNo).get("ITEM_DOUBLE") + " 개 보유중 ");
		PrintUtil.centerAlignment("초성힌트 : " + checkItem(userNo).get("ITEM_HINT") + " 개 보유중 ");
		PrintUtil.centerAlignment("목숨 +2 : " + checkItem(userNo).get("ITEM_LIFE") + " 개 보유중 ");
		PrintUtil.centerAlignment("사용하지않음");
		PrintUtil.bar2();
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n 【  선택  】 ");

		switch (ScanUtil.nextInt()) {
		case 1:
			// 아이템의 개수가 부족하면
			if (Integer.parseInt(checkItem(userNo).get("ITEM_DOUBLE").toString()) <= 0) {
				PrintUtil.centerAlignment("점수2배 아이템의 개수가 부족합니다, 아이템을 사용하지 않고 시작합니다");
				return;
			}
			PrintUtil.centerAlignment("점수2배를 사용하셨습니다!");
			gameManager.useItem(View.ITEM_DOUBLE);
			itemDAO.decreaseItem(View.ITEM_DOUBLE);
			break;
		case 2:
			if (Integer.parseInt(checkItem(userNo).get("ITEM_HINT").toString()) <= 0) {
				PrintUtil.centerAlignment("초성힌트 아이템의 개수가 부족합니다, 아이템을 사용하지 않고 시작합니다");
				return;
			}
			PrintUtil.centerAlignment("초성힌트를 사용하셨습니다!");
			gameManager.useItem(View.ITEM_HINT);
			itemDAO.decreaseItem(View.ITEM_HINT);
			break;
		case 3:
			if (Integer.parseInt(checkItem(userNo).get("ITEM_LIFE").toString()) <= 0) {
				PrintUtil.centerAlignment("목숨 +2 아이템의 개수가 부족합니다, 아이템을 사용하지 않고 시작합니다");
				return;
			}
			PrintUtil.centerAlignment("목숨 +2를 사용하셨습니다!");
			gameManager.useItem(View.ITEM_LIFE);
			itemDAO.decreaseItem(View.ITEM_LIFE);
			break;
		case 4:
			return;
		default:
			return;
		}
	}
}
