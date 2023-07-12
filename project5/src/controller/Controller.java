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
	// 아이템 사용여부 체크를 위한 변수
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
			quantity = sc.nextInt();
			
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
			quantity = sc.nextInt();
			
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
			quantity = sc.nextInt();
			
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

	// 퀴즈시작
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
			
			// 2번 틀리면 게임오버
			if(life <= 0) {
				return View.QUIZ_FAIL;
			}
			
			PrintUtil.bar();
			System.out.println("\t        Q" + (i+1));
			PrintUtil.bar2();
			System.out.println(quizList.get(i).get("QUIZ_DETAIL"));
			if(useHint) {
				PrintUtil.bar2();
				System.out.println("초성힌트 : " + quizList.get(i).get("QUIZ_HINT"));
			}
			PrintUtil.bar();
			System.out.print("\n 【  정답입력 】 ");
			String answer = sc.nextLine();
			
			if(answer.equals(quizList.get(i).get("QUIZ_ANSWER"))) {
				PrintUtil.bar2();
				System.out.println("\t          정답입니다!");
				PrintUtil.bar2();
				correctCount++;
			} else {
				PrintUtil.bar2();
				System.out.println("\t          오답입니다!");
				life--;
				System.out.println("\t 남은목숨 : " + life);
				PrintUtil.bar2();
			}
		}
		return View.QUIZ_SUCCESS;
	}
	
	// 아이템 사용하는 메소드
	private void useItem() {
		PrintUtil.bar();
		System.out.println("\t     아이템 1개 선택");
		PrintUtil.bar2();
		String userNo = userService.getUserInfo().get("USER_NO").toString();
		System.out.println("\t① 점수2배 : " + itemService.checkItem(userNo).get("ITEM_DOUBLE") + " 개 보유중 ");
		System.out.println("\t① 초성힌트 : " + itemService.checkItem(userNo).get("ITEM_HINT") +" 개 보유중 ");
		System.out.println("\t① 목숨 +2 : " + itemService.checkItem(userNo).get("ITEM_LIFE") + " 개 보유중 ");
		System.out.println("\t     ① 사용하지않음");
		PrintUtil.bar2();
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n 【  선택  】 ");
		
		switch (ScanUtil.nextInt()) {
		case 1:
			// 아이템의 개수가 부족하면
			if(Integer.parseInt(itemService.checkItem(userNo).get("ITEM_DOUBLE").toString()) <= 0) {
				System.out.println("점수2배 아이템의 개수가 부족합니다, 아이템을 사용하지 않고 시작합니다");
				return;
			}
			System.out.println("점수2배를 사용하셨습니다!");
			useDouble = true;
			break;
		case 2:
			if(Integer.parseInt(itemService.checkItem(userNo).get("ITEM_HINT").toString()) <= 0) {
				System.out.println("초성힌트 아이템의 개수가 부족합니다, 아이템을 사용하지 않고 시작합니다");
				return;
			}
			System.out.println("초성힌트를 사용하셨습니다!");
			useHint = true;
			break;
		case 3:
			if(Integer.parseInt(itemService.checkItem(userNo).get("ITEM_LIFE").toString()) <= 0) {
				System.out.println("목숨 +2 아이템의 개수가 부족합니다, 아이템을 사용하지 않고 시작합니다");
				return;
			}
			System.out.println("목숨 +2를 사용하셨습니다!");
			useLife = true;
			break;
		case 4:
			return;
		default:
			return;
		}
	}

	// 실패시 실행되는 메소드
	private int failMenu() {
		PrintUtil.bar();
		System.out.println("\t  퀴즈 풀기에 실패하셨습니다!");
		PrintUtil.bar2();
		if(useDouble) {
			userService.setUserScore(correctCount*2);
		} else {
			userService.setUserScore(correctCount);
		}
		userService.setUserGameMoney(correctCount * 10);
		Object score = userService.getUserInfo().get("USER_SCORE");
		System.out.println("    맞춘문제   " + correctCount + " / 10" + "       내 점수 : " + score);
		PrintUtil.bar2();
		System.out.println("\t① 퀴즈풀기   ② 메인메뉴   ");
		PrintUtil.bar();
		System.out.print("\n 【  선택 】 ");
		
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
	
	// 성공시 실행되는 메소드
	private int success() {
		PrintUtil.bar();
		System.out.println("\t  퀴즈 풀기에 성공하셨습니다!");
		PrintUtil.bar2();
		userService.setUserScore(correctCount);
		userService.setUserGameMoney(correctCount * 10);
		Object score = userService.getUserInfo().get("USER_SCORE");
		System.out.println("    맞춘문제   " + correctCount + " / 10" + "       내 점수 : " + score);
		PrintUtil.bar2();
		System.out.println("\t① 퀴즈풀기   ② 메인메뉴   ");
		PrintUtil.bar();
		System.out.print("\n 【  선택 】 ");
		
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