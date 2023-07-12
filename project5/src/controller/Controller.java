package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import dao.UserDAO;
import service.*;
import util.*;

public class Controller {

	public static Map<String, Object> sessionStorage = new HashMap<>();

	UserService userService = UserService.getInstance();
	QuizService quizService = QuizService.getInstance();
	ItemService itemService = ItemService.getInstance();
	Scanner sc = new Scanner(System.in);
	
	public int correctCount = 0;
	// ������ ��뿩�� üũ�� ���� ����
	public boolean useDouble = false;
	public boolean useHint = false;
	public boolean useLife = false;

	public static void main(String[] args) {
		 new Controller().start();
	}

	
	private void start() {

		int view = View.HOME;

		while (true) {
			switch (view) {
			case View.HOME:
				view = home();
				break;
			case View.HOME_MAIN:
				view = mainMenu();
				break;
			case View.USER_LOGIN:
				view = userService.logIn();
				break;
			case View.USER_SIGNUP:
				view = userService.signUp();
				break;
			case View.QUIZ_START:
				view = quizMenu();
				break;
			case View.QUIZ_FAIL:
				view = failMenu();
				break;
			case View.QUIZ_SUCCESS:
				view = success();
				break;
			case View.SHOP_MAIN:
				view = shopMain();
				break;
			}
		}
	}

	// Ȩ�޴�
	private int home() {
		PrintUtil.bar();
		System.out.println("\t            ����");
		PrintUtil.bar2();
		System.out.println("  �� �α���          �� ȸ�� ����          �� ������ ����      ");
		PrintUtil.bar2();
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n ��  ����  �� ");

		switch (ScanUtil.nextInt()) {
		case 1:
			return View.USER_LOGIN;
		case 2:
			return View.USER_SIGNUP;
		case 3:
			return View.ADMIN_LOGIN;
		default:
			return View.HOME;
		}

	}

	// ���θ޴�
	private int mainMenu() {
		PrintUtil.bar();
		System.out.println("\t            MAIN");
		PrintUtil.bar2();
		System.out.println("  �� ����Ǯ��   �� Ŀ�´�Ƽ   �� ������   �� ����������  �� ����  ��  �α׾ƿ� ");
		PrintUtil.bar2();
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n ��  ����  �� ");

		switch (ScanUtil.nextInt()) {
		case 1:
			return View.QUIZ_START;
//		case 2:
//			return View.;
//		case 3:
//			return View.;
//		case 4:
//			return View.;
		case 5:
			return View.SHOP_MAIN;
		case 6:
			return View.USER_LOGOUT;
		default:
			return View.QUIZ;
		}
	}
	
	// ���� �̿�
	private int shopMain() {
		PrintUtil.bar();
		System.out.println("\t            SHOP");
		PrintUtil.bar2();
		System.out.println("\t ���� �ݾ� : " + userService.getUserInfo().get("USER_GM"));
		PrintUtil.bar2();
		System.out.println("  �� ����2�� ����   �� �ʼ���Ʈ ����  �� ��� +2 ����  �� ������ ������  �� �ڷΰ���");
		System.out.println("      200g         100g        100g  ");
		PrintUtil.bar2();
		String userNo = userService.getUserInfo().get("USER_NO").toString();
		System.out.println("\t       �������� ������ ");
		System.out.println("����2�� : " + itemService.checkItem(userNo).get("ITEM_DOUBLE") 
						 + "    �ʼ���Ʈ : " + itemService.checkItem(userNo).get("ITEM_HINT")  
						 + "    ��� +2 : " + itemService.checkItem(userNo).get("ITEM_LIFE"));
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n ��  ����  �� ");
		
		int quantity = 0;
		
		switch (ScanUtil.nextInt()) {
		case 1:
			System.out.println("��� �����Ͻðڽ��ϱ�?");
			System.out.print("\n ��  ����  �� ");
			quantity = sc.nextInt();
			
			if(userService.purchaseItem(200 * quantity)){
				itemService.setUserItem(1, quantity, userService.getUserInfo().get("USER_NO").toString());
				System.out.println("���� 2�踦 " + quantity + " ��ŭ �����Ͽ����ϴ�");
			} else {
				System.out.println("�ݾ��� �����մϴ�");
			}
			break;
		case 2:
			System.out.println("��� �����Ͻðڽ��ϱ�?");
			System.out.print("\n ��  ����  �� ");
			quantity = sc.nextInt();
			
			if(userService.purchaseItem(100 * quantity)){
				itemService.setUserItem(2, quantity, userService.getUserInfo().get("USER_NO").toString());
				System.out.println("�ʼ���Ʈ�� " + quantity + " ��ŭ �����Ͽ����ϴ�");
			} else {
				System.out.println("�ݾ��� �����մϴ�");
			}
			break;
		case 3:
			System.out.println("��� �����Ͻðڽ��ϱ�?");
			System.out.print("\n ��  ����  �� ");
			quantity = sc.nextInt();
			
			if(userService.purchaseItem(100 * quantity)){
				itemService.setUserItem(3, quantity, userService.getUserInfo().get("USER_NO").toString());
				System.out.println("��� +2�� " + quantity + " ��ŭ �����Ͽ����ϴ�");
			} else {
				System.out.println("�ݾ��� �����մϴ�");
			}
			break;
		case 4:
			System.out.println("�������� ���Ӵ� 1���� ����� �� ������ ������ ���� ȿ���� �����ϴ�");
			System.out.println("����2�� : ������ ������ ��� ������ 2��� ����ϴ�");
			System.out.println("�ʼ���Ʈ : ���� �ش� ���ӳ��� �ʼ� ��Ʈ�� ����ϴ�");
			System.out.println("��� +2 : �����ϴ� ����� ������ 2�� �þ�ϴ�");
			break;
		case 5:
			return View.HOME_MAIN;
		default:
			return View.HOME_MAIN;
		}
		
		System.out.println("��� ���� �Ͻðڽ��ϱ�? (y/n)");
		switch (ScanUtil.nextLine()) {
		case "y":
			return View.SHOP_MAIN;
		case "n":
			return View.HOME_MAIN;
			default:
		return View.HOME_MAIN;
		}
	}

	// ����ø޴�
	private int quizMenu() {
		PrintUtil.bar();
		System.out.println("\t     ���� ī�װ� ����");
		PrintUtil.bar2();
		System.out.println("  �� �ͼ��� ����   �� �츮�� ���߱�   �� ��� ����   �� ���� ����  �� �ڷΰ���   ");
		PrintUtil.bar2();
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n ��  ����  �� ");

		switch (ScanUtil.nextInt()) {
		case 1:
			return startQuiz(1);
		case 2:
			return startQuiz(2);
		case 3:
			return startQuiz(3);
		case 4:
			return startQuiz(4);
		case 5:
			return View.HOME_MAIN;
		default:
			return View.QUIZ;
		}
	}

	// �������
	private int startQuiz(int genre) {
		
		useItem();
				
		correctCount = 0;
		int life = 0;
		
		if(useLife) {
			life = 4;
		} else {
			life = 2;
		}

		List<Map<String, Object>> quizList = new ArrayList<>();
		quizList = quizService.getQuiz(genre);
		
		for (int i = 0; i < 5; i++) {
			
			// 2�� Ʋ���� ���ӿ���
			if(life <= 0) {
				return View.QUIZ_FAIL;
			}
			
			PrintUtil.bar();
			System.out.println("\t        Q" + (i+1));
			PrintUtil.bar2();
			System.out.println(quizList.get(i).get("QUIZ_DETAIL"));
			if(useHint) {
				PrintUtil.bar2();
				System.out.println("�ʼ���Ʈ : " + quizList.get(i).get("QUIZ_HINT"));
			}
			PrintUtil.bar();
			System.out.print("\n ��  �����Է� �� ");
			String answer = sc.nextLine();
			
			if(answer.equals(quizList.get(i).get("QUIZ_ANSWER"))) {
				PrintUtil.bar2();
				System.out.println("\t          �����Դϴ�!");
				PrintUtil.bar2();
				correctCount++;
			} else {
				PrintUtil.bar2();
				System.out.println("\t          �����Դϴ�!");
				life--;
				System.out.println("\t ������� : " + life);
				PrintUtil.bar2();
			}
		}
		return View.QUIZ_SUCCESS;
	}
	
	// ������ ����ϴ� �޼ҵ�
	private void useItem() {
		PrintUtil.bar();
		System.out.println("\t     ������ 1�� ����");
		PrintUtil.bar2();
		String userNo = userService.getUserInfo().get("USER_NO").toString();
		System.out.println("\t�� ����2�� : " + itemService.checkItem(userNo).get("ITEM_DOUBLE") + " �� ������ ");
		System.out.println("\t�� �ʼ���Ʈ : " + itemService.checkItem(userNo).get("ITEM_HINT") +" �� ������ ");
		System.out.println("\t�� ��� +2 : " + itemService.checkItem(userNo).get("ITEM_LIFE") + " �� ������ ");
		System.out.println("\t     �� �����������");
		PrintUtil.bar2();
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n ��  ����  �� ");
		
		switch (ScanUtil.nextInt()) {
		case 1:
			// �������� ������ �����ϸ�
			if(Integer.parseInt(itemService.checkItem(userNo).get("ITEM_DOUBLE").toString()) <= 0) {
				System.out.println("����2�� �������� ������ �����մϴ�, �������� ������� �ʰ� �����մϴ�");
				return;
			}
			System.out.println("����2�踦 ����ϼ̽��ϴ�!");
			useDouble = true;
			break;
		case 2:
			if(Integer.parseInt(itemService.checkItem(userNo).get("ITEM_HINT").toString()) <= 0) {
				System.out.println("�ʼ���Ʈ �������� ������ �����մϴ�, �������� ������� �ʰ� �����մϴ�");
				return;
			}
			System.out.println("�ʼ���Ʈ�� ����ϼ̽��ϴ�!");
			useHint = true;
			break;
		case 3:
			if(Integer.parseInt(itemService.checkItem(userNo).get("ITEM_LIFE").toString()) <= 0) {
				System.out.println("��� +2 �������� ������ �����մϴ�, �������� ������� �ʰ� �����մϴ�");
				return;
			}
			System.out.println("��� +2�� ����ϼ̽��ϴ�!");
			useLife = true;
			break;
		case 4:
			return;
		default:
			return;
		}
	}

	// ���н� ����Ǵ� �޼ҵ�
	private int failMenu() {
		PrintUtil.bar();
		System.out.println("\t  ���� Ǯ�⿡ �����ϼ̽��ϴ�!");
		PrintUtil.bar2();
		if(useDouble) {
			userService.setUserScore(correctCount*2);
		} else {
			userService.setUserScore(correctCount);
		}
		userService.setUserGameMoney(correctCount * 10);
		Object score = userService.getUserInfo().get("USER_SCORE");
		System.out.println("    ���Ṯ��   " + correctCount + " / 10" + "       �� ���� : " + score);
		PrintUtil.bar2();
		System.out.println("\t�� ����Ǯ��   �� ���θ޴�   ");
		PrintUtil.bar();
		System.out.print("\n ��  ���� �� ");
		
		useDouble = false;
		useHint = false;
		useLife = false;
		
		switch (ScanUtil.nextInt()) {
		case 1:
			return View.QUIZ_START;
		case 2:
			return View.HOME_MAIN;
		default:
			return View.HOME_MAIN;
		}
	}
	
	// ������ ����Ǵ� �޼ҵ�
	private int success() {
		PrintUtil.bar();
		System.out.println("\t  ���� Ǯ�⿡ �����ϼ̽��ϴ�!");
		PrintUtil.bar2();
		userService.setUserScore(correctCount);
		userService.setUserGameMoney(correctCount * 10);
		Object score = userService.getUserInfo().get("USER_SCORE");
		System.out.println("    ���Ṯ��   " + correctCount + " / 10" + "       �� ���� : " + score);
		PrintUtil.bar2();
		System.out.println("\t�� ����Ǯ��   �� ���θ޴�   ");
		PrintUtil.bar();
		System.out.print("\n ��  ���� �� ");
		
		useDouble = false;
		useHint = false;
		useLife = false;
		
		switch (ScanUtil.nextInt()) {
		case 1:
			return View.QUIZ_START;
		case 2:
			return View.HOME_MAIN;
		default:
			return View.HOME_MAIN;
		}
	}
}