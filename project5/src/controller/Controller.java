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
			}
		}
	}

	// 홈메뉴
	private int home() {
		PrintUtil.bar();
		System.out.println("\t            퀴즈");
		PrintUtil.bar2();
		System.out.println("  ① 로그인          ② 회원 가입          ③ 관리자 접속      ");
		PrintUtil.bar2();
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n 【  선택  】 ");

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

	// 메인메뉴
	private int mainMenu() {
		PrintUtil.bar();
		System.out.println("\t            MAIN");
		PrintUtil.bar2();
		System.out.println("  ① 문제풀기   ② 커뮤니티   ③ 문제집   ④ 마이페이지  ⑤ 상점  ⑤  로그아웃 ");
		PrintUtil.bar2();
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n 【  선택  】 ");

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
	
	// 상점 이용
	private int shopMain() {
		PrintUtil.bar();
		System.out.println("\t            SHOP");
		PrintUtil.bar2();
		System.out.println("\t 현재 금액 : " + userService.getUserInfo().get("USER_GM"));
		PrintUtil.bar2();
		System.out.println("  ① 점수2배 구매   ② 초성힌트 구매  ③ 목숨 +2 구매  ④ 아이템 설명듣기  ⑤ 뒤로가기");
		System.out.println("      200g         100g        100g  ");
		PrintUtil.bar2();
		String userNo = userService.getUserInfo().get("USER_NO").toString();
		System.out.println("\t       보유중인 아이템 ");
		System.out.println("점수2배 : " + itemService.checkItem(userNo).get("ITEM_DOUBLE") 
						 + "    초성힌트 : " + itemService.checkItem(userNo).get("ITEM_HINT")  
						 + "    목숨 +2 : " + itemService.checkItem(userNo).get("ITEM_LIFE"));
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n 【  선택  】 ");
		
		int quantity = 0;
		
		switch (ScanUtil.nextInt()) {
		case 1:
			System.out.println("몇개를 구매하시겠습니까?");
			System.out.print("\n 【  선택  】 ");
			quantity = ScanUtil.nextInt();
			
			if(userService.purchaseItem(200 * quantity)){
				itemService.setUserItem(1, quantity, userService.getUserInfo().get("USER_NO").toString());
				System.out.println("점수 2배를 " + quantity + " 만큼 구매하였습니다");
			} else {
				System.out.println("금액이 부족합니다");
			}
			break;
		case 2:
			System.out.println("몇개를 구매하시겠습니까?");
			System.out.print("\n 【  선택  】 ");
			quantity = ScanUtil.nextInt();
			
			if(userService.purchaseItem(100 * quantity)){
				itemService.setUserItem(2, quantity, userService.getUserInfo().get("USER_NO").toString());
				System.out.println("초성힌트를 " + quantity + " 만큼 구매하였습니다");
			} else {
				System.out.println("금액이 부족합니다");
			}
			break;
		case 3:
			System.out.println("몇개를 구매하시겠습니까?");
			System.out.print("\n 【  선택  】 ");
			quantity = ScanUtil.nextInt();
			
			if(userService.purchaseItem(100 * quantity)){
				itemService.setUserItem(3, quantity, userService.getUserInfo().get("USER_NO").toString());
				System.out.println("목숨 +2를 " + quantity + " 만큼 구매하였습니다");
			} else {
				System.out.println("금액이 부족합니다");
			}
			break;
		case 4:
			System.out.println("아이템은 게임당 1번만 사용할 수 있으며 다음과 같은 효과를 가집니다");
			System.out.println("점수2배 : 게임이 끝날시 얻는 점수를 2배로 얻습니다");
			System.out.println("초성힌트 : 사용시 해당 게임내내 초성 힌트를 얻습니다");
			System.out.println("목숨 +2 : 시작하는 목숨의 개수가 2개 늘어납니다");
			break;
		case 5:
			return View.HOME_MAIN;
		default:
			return View.HOME_MAIN;
		}
		
		System.out.println("계속 구매 하시겠습니까? (y/n)");
		switch (ScanUtil.nextLine()) {
		case "y":
			return View.SHOP_MAIN;
		case "n":
			return View.HOME_MAIN;
			default:
		return View.HOME_MAIN;
		}
	}

	// 퀴즈선택메뉴
	private int quizMenu() {
		PrintUtil.bar();
		System.out.println("\t     퀴즈 카테고리 선택");
		PrintUtil.bar2();
		System.out.println("  ① 넌센스 퀴즈   ② 우리말 맞추기   ③ 상식 퀴즈   ④ 역사 퀴즈  ⑤ 뒤로가기   ");
		PrintUtil.bar2();
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n 【  선택  】 ");

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

}