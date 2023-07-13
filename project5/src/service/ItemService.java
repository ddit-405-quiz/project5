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
	

	// itemNo�� �������� ������ üũ���ִ� �޼ҵ�
	public Map<String, Object> checkItem(String userNo) {
		String sql = "SELECT * FROM ITEM " + "WHERE USER_NO = " + userNo;
		return itemDAO.checkItem(sql);
	}

	// �Ű����� 2���� �Է¹ް� itemNo�� �������� quantity��ŭ ����
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

	// ������ ����ϴ� �޼ҵ�
	public void useItem() {
		PrintUtil.bar();
		PrintUtil.centerAlignment("������ 1�� ����");
		PrintUtil.bar2();
		String userNo = UserService.getInstance().getUserInfo().get("USER_NO").toString();
		PrintUtil.centerAlignment("����2��  : " + checkItem(userNo).get("ITEM_DOUBLE") + " �� ������ ");
		PrintUtil.centerAlignment("�ʼ���Ʈ : " + checkItem(userNo).get("ITEM_HINT") + " �� ������ ");
		PrintUtil.centerAlignment("��� +2 : " + checkItem(userNo).get("ITEM_LIFE") + " �� ������ ");
		PrintUtil.centerAlignment("�����������");
		PrintUtil.bar2();
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n ��  ����  �� ");

		switch (ScanUtil.nextInt()) {
		case 1:
			// �������� ������ �����ϸ�
			if (Integer.parseInt(checkItem(userNo).get("ITEM_DOUBLE").toString()) <= 0) {
				PrintUtil.centerAlignment("����2�� �������� ������ �����մϴ�, �������� ������� �ʰ� �����մϴ�");
				return;
			}
			PrintUtil.centerAlignment("����2�踦 ����ϼ̽��ϴ�!");
			gameManager.useItem(View.ITEM_DOUBLE);
			itemDAO.decreaseItem(View.ITEM_DOUBLE);
			break;
		case 2:
			if (Integer.parseInt(checkItem(userNo).get("ITEM_HINT").toString()) <= 0) {
				PrintUtil.centerAlignment("�ʼ���Ʈ �������� ������ �����մϴ�, �������� ������� �ʰ� �����մϴ�");
				return;
			}
			PrintUtil.centerAlignment("�ʼ���Ʈ�� ����ϼ̽��ϴ�!");
			gameManager.useItem(View.ITEM_HINT);
			itemDAO.decreaseItem(View.ITEM_HINT);
			break;
		case 3:
			if (Integer.parseInt(checkItem(userNo).get("ITEM_LIFE").toString()) <= 0) {
				PrintUtil.centerAlignment("��� +2 �������� ������ �����մϴ�, �������� ������� �ʰ� �����մϴ�");
				return;
			}
			PrintUtil.centerAlignment("��� +2�� ����ϼ̽��ϴ�!");
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
