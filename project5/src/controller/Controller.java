package controller;

import java.util.HashMap;
import java.util.Map;


import service.UserService;
import util.PrintUtil;
import util.ScanUtil;
import util.View;

public class Controller {

	public static Map<String, Object> sessionStorage = new HashMap<>();
				      
	UserService userservice = UserService.getInstance();
				      
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
				view = main();
				break;
			case View.USER_LOGIN:
				view = userservice.getInstance().logIn();
				break;
			case View.USER_SIGNUP:
				view = userservice.getInstance().signUp();
				break;
			}
		}
	}


	//홈메뉴
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
	//메인메뉴
	private int main() {
		PrintUtil.bar();
		System.out.println("\t            MAIN");		
		PrintUtil.bar2();
		System.out.println("  ① 문제풀기   ② 커뮤니티   ③ 문제집   ④ 마이페이지  ⑤ 로그아웃    ");
		PrintUtil.bar2();
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n 【  선택  】 ");
		
		switch (ScanUtil.nextInt()) {
		case 1:
			return View.QUIZ;
//		case 2:
//			return View.;
//		case 3:
//			return View.;
//		case 4:
//			return View.;
		case 5:
			return View.USER_LOGOUT;
		default:
			return View.QUIZ;
		
		}
	}
}