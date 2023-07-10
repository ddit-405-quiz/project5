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


	//Ȩ�޴�
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
	//���θ޴�
	private int main() {
		PrintUtil.bar();
		System.out.println("\t            MAIN");		
		PrintUtil.bar2();
		System.out.println("  �� ����Ǯ��   �� Ŀ�´�Ƽ   �� ������   �� ����������  �� �α׾ƿ�    ");
		PrintUtil.bar2();
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n ��  ����  �� ");
		
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