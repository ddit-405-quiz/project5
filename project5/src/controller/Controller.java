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
	BoardService boardService = BoardService.getInstance();

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
				view = quizService.failMenu();
				break;
			case View.QUIZ_SUCCESS:
				view = quizService.success();
				break;
			case View.SHOP_MAIN:
				view = shopMain();
				break;
			case View.USER_LOGOUT:
				break;
			case View.BOARD:
				view = list();
				break;
			}
		}
	}

	// Ȩ�޴�
	private int home() {
		PrintUtil.bar();
		System.out.println();
		System.out.println();
		PrintUtil.centerAlignment("����� ����!");
		PrintUtil.bar2();
		System.out.println();
		PrintUtil.centerAlignment("1.�α���     2.ȸ�� ����     3.������ ����");
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
		System.out.println();
		PrintUtil.centerAlignment("MAIN");
		PrintUtil.bar2();
		System.out.println();
		PrintUtil.centerAlignment("�� ����Ǯ��   �� Ŀ�´�Ƽ   �� ������   �� ����������  �� ����  �� �α׾ƿ�");
		PrintUtil.bar2();
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n ��  ����  �� ");

		switch (ScanUtil.nextInt()) {
		case 1:
			return View.QUIZ_START;
		case 2:
			return View.BOARD;
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
		System.out.println();
		PrintUtil.centerAlignment("SHOP");
		PrintUtil.bar2();
		String str = "���� �ݾ� : " + userService.getUserInfo().get("USER_GM");
		PrintUtil.centerAlignment(str);
		PrintUtil.bar2();
		PrintUtil.centerAlignment("�� ����2�� ����   �� �ʼ���Ʈ ����  �� ��� +2 ����  �� ������ ������  �� �ڷΰ���");
		System.out.println("        200g         100g        100g  ");
		PrintUtil.bar2();
		String userNo = userService.getUserInfo().get("USER_NO").toString();
		PrintUtil.centerAlignment("�������� ������ ");
		PrintUtil.centerAlignment("����2�� : " + itemService.checkItem(userNo).get("ITEM_DOUBLE") 
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
			quantity = ScanUtil.nextInt();
			
			if(userService.purchaseItem(200 * quantity)){
				itemService.setUserItem(1, quantity, userService.getUserInfo().get("USER_NO").toString());
				PrintUtil.bar3();
				PrintUtil.centerAlignment("���� 2�踦 " + quantity + "�� ��ŭ �����Ͽ����ϴ�");
				PrintUtil.bar3();
			} else {
				PrintUtil.bar3();
				PrintUtil.centerAlignment("�ݾ��� �����մϴ�");
				PrintUtil.bar3();
			}
			break;
		case 2:
			System.out.println("��� �����Ͻðڽ��ϱ�?");
			System.out.print("\n ��  ����  �� ");
			quantity = ScanUtil.nextInt();
			
			if(userService.purchaseItem(100 * quantity)){
				itemService.setUserItem(2, quantity, userService.getUserInfo().get("USER_NO").toString());
				PrintUtil.bar3();
				PrintUtil.centerAlignment("�ʼ���Ʈ�� " + quantity + "�� ��ŭ �����Ͽ����ϴ�");
				PrintUtil.bar3();
			} else {
				PrintUtil.bar3();
				PrintUtil.centerAlignment("�ݾ��� �����մϴ�");
				PrintUtil.bar3();
			}
			break;
		case 3:
			PrintUtil.bar3();
			PrintUtil.centerAlignment("��� �����Ͻðڽ��ϱ�?");
			PrintUtil.bar3();
			System.out.print("\n ��  ����  �� ");
			quantity = ScanUtil.nextInt();
			
			if(userService.purchaseItem(100 * quantity)){
				itemService.setUserItem(3, quantity, userService.getUserInfo().get("USER_NO").toString());
				PrintUtil.bar3();
				PrintUtil.centerAlignment("��� +2�� " + quantity + "�� ��ŭ �����Ͽ����ϴ�");
				PrintUtil.bar3();
			} else {
				PrintUtil.bar3();
				PrintUtil.centerAlignment("�ݾ��� �����մϴ�");
				PrintUtil.bar3();
			}
			break;
		case 4:
			PrintUtil.bar3();
			PrintUtil.centerAlignment("�������� ���Ӵ� 1���� ����� �� ������ ������ ���� ȿ���� �����ϴ�");
			PrintUtil.centerAlignment("����2�� : ������ ������ ��� ������ 2��� ����ϴ�");
			PrintUtil.centerAlignment("�ʼ���Ʈ : ���� �ش� ���ӳ��� �ʼ� ��Ʈ�� ����ϴ�");
			PrintUtil.centerAlignment("��� +2 : �����ϴ� ����� ������ 2�� �þ�ϴ�");
			PrintUtil.bar3();
			break;
		case 5:
			return View.HOME_MAIN;
		default:
			return View.HOME_MAIN;
		}
		PrintUtil.bar3();
		PrintUtil.centerAlignment("��� ���� �Ͻðڽ��ϱ�? (y/n)");
		PrintUtil.bar3();
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
		PrintUtil.centerAlignment("���� ī�װ� ����");
		PrintUtil.bar2();
		PrintUtil.centerAlignment("�� ��� ����  �� �츮�� ���߱�   �� ���� ����   �� �ͼ��� ����  �� �ڷΰ���   ");
		PrintUtil.bar2();
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n ��  ����  �� ");

		switch (ScanUtil.nextInt()) {
		case 1:
			return quizService.startQuiz(1);
		case 2:
			return quizService.startQuiz(2);
		case 3:
			return quizService.startQuiz(3);
		case 4:
			return quizService.startQuiz(4);
		case 5:
			return View.HOME_MAIN;
		default:
			return View.QUIZ;
		}
	}
	
	// ������ �α��ν� ��µǴ� ȭ��
	private int adminMain() {
		PrintUtil.bar();
		System.out.println("\t\tADMIN MAIN");
		PrintUtil.bar2();
		System.out.println("\t�� ��������   �� �Խ���   �� ����   �� �α׾ƿ� ");
		PrintUtil.bar2();
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n ��  ����  �� ");
		
		switch (ScanUtil.nextInt()) {
		case 1:
			return View.QUIZ_MANAGE;
		case 2:
			return View.BOARD;
		case 3:
			return View.USER_MANAGE;
		case 4:
			return View.ADMIN_LOGOUT;
		default:
			return View.ADMIN_MAIN;
		}
	}

	// ���� ��ȸ�� ��µǴ� ȭ��
	public int questionList() {
		System.out.println();
		PrintUtil.bar();
		System.out.println("\t\t���� ���");
		System.out.println("  1. ��� ���� ��ȸ   2. �츮�� ���߱� ���� ��ȸ   3. ���� ���� ��ȸ    4. �ͼ��� ���� ��ȸ   5. �ڷΰ���   ");
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n ��  ����  �� ");

		switch (ScanUtil.nextInt()) {
		case 1:
			quizService.searchQuiz(View.QUIZ_COMMON_SENSE);
			return View.ADMIN_MAIN;
		case 2:
			quizService.searchQuiz(View.QUIZ_KOREAN);
			return View.ADMIN_MAIN;
		case 3:
			quizService.searchQuiz(View.QUIZ_HISTORY);
			return View.ADMIN_MAIN;
		case 4:
			quizService.searchQuiz(View.QUIZ_NONSENSE);
			return View.ADMIN_MAIN;
		case 5:
			return View.ADMIN_MAIN;
		default:
			return View.ADMIN_MAIN;
		}

	}
	
	public int list() {
	    int currentPage = 1; // ���� ������
	    int totalPage = boardService.getTotalPage(); // ��ü ������ ��
	    while (true) {
	        PrintUtil.bar();
	        System.out.println("[�Խù� ���]");
	        System.out.println("no\t\ttitle\t\twriter");
	        
	        List<Map<String, Object>> boardList = BoardService.getInstance().getBoardListByPage(currentPage);
	        for (int i = 0; i < boardList.size(); i++) {
	            Map<String, Object> map = boardList.get(i);
	            System.out.print(map.get("REQ_NO")
	                    + "\t\t" + map.get("REQ_TITLE")
	                    + "\t\t" + map.get("REQ_WRITER"));
	            System.out.println();
	        }
	        System.out.println();
	        PrintUtil.bar();
	        System.out.println("\t\t\t���� ������: " + currentPage + "/" + totalPage);
	        System.out.println("�� �б� �� ���� �� �ڷΰ��� �� ���������� �� ���������� ");
	        System.out.print("����  : ");

	        switch (ScanUtil.nextInt()) {
	            case 1:
	                System.out.print("�Խù� ��ȣ �Է�: ");
	                int reqNo = ScanUtil.nextInt();
	                return boardService.read(reqNo);
	            case 2:
	                return boardService.create();
	            case 3:
	                return View.HOME_MAIN;
	            case 4:
	            	if(currentPage==1) {
	            		System.out.println("���� �������� �����ϴ�");
	            	}else {
	            		currentPage--;
	            	}
	            	break;
	            case 5:
	            	if(currentPage==totalPage) {
	            		System.out.println("���� �������� �����ϴ�");
	            	}else {
	            		currentPage++;
	            	}
	            	break;
	            default:
	                System.out.println("�߸��� �Է��Դϴ�.");
	                break;
	        }
	    }
	}

}