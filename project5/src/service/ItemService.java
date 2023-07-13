package service;

import java.util.Map;

import dao.ItemDAO;
import util.PrintUtil;
import util.ScanUtil;
import util.View;
import util.GameManager;;

public class ItemService {

	static ItemService instance = null;

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

	/**
	 * 매개변수 4개를 입력받고 userNo의 itemNo의 아이템을 quantity만큼 increase를 통해 더하거나 뺌
	 * 
	 * @param itemNo
	 * @param quantity
	 * @param userNo
	 * @param increase
	 */
	public void setUserItem(int itemNo, int quantity, String userNo, boolean increase) {

		String itemName = "";
		switch (itemNo) {
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


//		String operator = increase ? "+" : "-";
//		String sql = "UPDATE ITEM" + " SET " + itemName + " = " + itemName + " + " + operator + quantity
//				+ " WHERE USER_NO = " + userNo;


		int operator = increase ? 1 : -1;
		String sql = "UPDATE ITEM "
				   + " SET " + itemName + "  =  " + itemName + " + " + quantity
				   + " WHERE USER_NO = " + gameManager.getUserInfo().get("USER_NO");
		itemDAO.setUserItem(sql);
	}

	/**
	 * 아이템 사용 메소드, 실행시 아이템을 사용할건지 물어보고, 아이템중 하나를 사용하면 해당 아이템의 useItem을 true로 변경한다
	 */
	public void useItem() {
		PrintUtil.bar();
		PrintUtil.bar2();
		PrintUtil.centerAlignment("아이템 1개 선택");
		PrintUtil.bar2();
		String userNo = UserService.getInstance().getUserInfo().get("USER_NO").toString();
		System.out.println("\t\t     1.점수2배  : " + checkItem(userNo).get("ITEM_DOUBLE") + " 개 보유중 ");
		System.out.println("\t\t     2.초성힌트 : " + checkItem(userNo).get("ITEM_HINT") + " 개 보유중 ");
		System.out.println("\t\t     3.목숨 +2 : " + checkItem(userNo).get("ITEM_LIFE") + " 개 보유중 ");
		System.out.println("\t\t     4.사용하지않음");
		PrintUtil.bar2();
		PrintUtil.bar();
		System.out.print("\n 【  선택  】 ");

		try {
			switch (ScanUtil.nextInt()) {
			case 1:
				// 아이템의 개수가 부족하면
				if (Integer.parseInt(checkItem(userNo).get("ITEM_DOUBLE").toString()) <= 0) {
					PrintUtil.centerAlignment("점수2배 아이템의 개수가 부족합니다, 아이템을 사용하지 않고 시작합니다");
					return;
				}
				PrintUtil.centerAlignment("점수2배를 사용하셨습니다!");
				gameManager.useItem(View.ITEM_DOUBLE);
				setUserItem(View.ITEM_DOUBLE, 1, gameManager.getUserInfo().get("USER_NO").toString(), false);
				break;
			case 2:
				if (Integer.parseInt(checkItem(userNo).get("ITEM_HINT").toString()) <= 0) {
					PrintUtil.centerAlignment("초성힌트 아이템의 개수가 부족합니다, 아이템을 사용하지 않고 시작합니다");
					return;
				}
				PrintUtil.centerAlignment("초성힌트를 사용하셨습니다!");
				gameManager.useItem(View.ITEM_HINT);
				setUserItem(View.ITEM_HINT, 1, gameManager.getUserInfo().get("USER_NO").toString(), false);
				break;
			case 3:
				if (Integer.parseInt(checkItem(userNo).get("ITEM_LIFE").toString()) <= 0) {
					PrintUtil.centerAlignment("목숨 +2 아이템의 개수가 부족합니다, 아이템을 사용하지 않고 시작합니다");
					return;
				}
				PrintUtil.centerAlignment("목숨 +2를 사용하셨습니다!");
				gameManager.useItem(View.ITEM_LIFE);
				setUserItem(View.ITEM_LIFE, 1, gameManager.getUserInfo().get("USER_NO").toString(), false);
				break;
			case 4:
				PrintUtil.bar3();
				PrintUtil.centerAlignment("아이템을 사용하지 않습니다");
				PrintUtil.bar3();
				return;
			default:
				PrintUtil.bar3();
				PrintUtil.centerAlignment("아이템을 사용하지 않습니다");
				PrintUtil.bar3();
				return;
			}
		} catch (NumberFormatException e) {
			PrintUtil.bar3();
	        PrintUtil.centerAlignment("올바른 숫자를 입력하세요");
			PrintUtil.bar3();
	        useItem(); // 예외 발생 시 홈 메인으로 돌아감

		}
	}
}