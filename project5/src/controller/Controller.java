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

	GameManager gameManager = GameManager.getInstance();
	UserService userService = UserService.getInstance();
	QuizService quizService = QuizService.getInstance();
	ItemService itemService = ItemService.getInstance();
	AdminService adminService = AdminService.getInstance();
	AdminBoardService adminBoardService = AdminBoardService.getInstance();
	BoardService boardService = BoardService.getInstance();

	public static void main(String[] args) {
		new Controller().start();
	}

	// 화면 이동을 제어하는 메소드, view의 값에 따라 이동하고 싶은 화면을 출력하는걸 무한반복한다
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
			case View.QUIZ_MANAGE:
				view = questionList();
				break;
			case View.SHOP_MAIN:
				view = shopMain();
				break;
			case View.ADMIN_LOGIN:
				view = adminService.adminLogin();
				break;
			case View.ADMIN_MAIN:
				view = adminMain();
			case View.USER_LOGOUT:
				break;
			case View.BOARD:
				view = list();
				break;
			case View.ADMIN_BOARD:
				view= adminlist();
                break;
			}
		}

	}

	// 홈메뉴
	private int home() {
		PrintUtil.bar();
		System.out.println();
		System.out.println();
		PrintUtil.centerAlignment("퀴즈야 놀자!");
		PrintUtil.bar2();
		System.out.println();
		PrintUtil.centerAlignment("1.로그인     2.회원 가입     3.관리자 접속");
		PrintUtil.bar2();
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n 【  선택  】 ");

		try {
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
		} catch (NumberFormatException e) {
			PrintUtil.bar3();
			PrintUtil.centerAlignment("올바른 숫자를 입력하세요");
			PrintUtil.bar3();
			return View.HOME; // 예외 발생 시 홈 메인으로 돌아감
		}
	}

	// 메인메뉴
	private int mainMenu() {
		PrintUtil.bar();
		System.out.println();
		PrintUtil.centerAlignment("MAIN");
		PrintUtil.bar2();
		System.out.println();
		PrintUtil.centerAlignment("① 문제풀기   ② 커뮤니티   ③ 문제집   ④ 마이페이지  ⑤ 상점  ⑤ 로그아웃");
		PrintUtil.bar2();
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n 【  선택  】 ");

		try {
			switch (ScanUtil.nextInt()) {
			case 1:
				return View.QUIZ_START;
			case 2:
				return View.BOARD;
//			case 3:
//				return View.;
//			case 4:
//				return View.;
			case 5:
				return View.SHOP_MAIN;
			case 6:
				return View.HOME;
//				return View.USER_LOGOUT;
			default:
				return View.QUIZ;

			}
		} catch (NumberFormatException e) {
			PrintUtil.bar3();
			PrintUtil.centerAlignment("올바른 숫자를 입력하세요");
			PrintUtil.bar3();
			return View.HOME_MAIN; // 예외 발생 시 홈 메인으로 돌아감
		}
	}

	// 상점 이용
	private int shopMain() {
		PrintUtil.bar();
		System.out.println();
		PrintUtil.centerAlignment("SHOP");
		PrintUtil.bar2();
		String str = "현재 금액 : " + userService.getUserInfo().get("USER_GM");
		PrintUtil.centerAlignment(str);
		PrintUtil.bar2();
		PrintUtil.centerAlignment("① 점수2배 구매   ② 초성힌트 구매  ③ 목숨 +2 구매  ④ 아이템 설명듣기  ⑤ 뒤로가기");
		System.out.println("        200g         100g        100g  ");
		PrintUtil.bar2();
		String userNo = userService.getUserInfo().get("USER_NO").toString();
		PrintUtil.centerAlignment("보유중인 아이템 ");
		PrintUtil.centerAlignment("점수2배 : " + itemService.checkItem(userNo).get("ITEM_DOUBLE") + "    초성힌트 : "
				+ itemService.checkItem(userNo).get("ITEM_HINT") + "    목숨 +2 : "
				+ itemService.checkItem(userNo).get("ITEM_LIFE"));
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n 【  선택  】 ");

		int quantity = 0;

		try {
			switch (ScanUtil.nextInt()) {
			case 1:
				System.out.println("몇개를 구매하시겠습니까?");
				System.out.print("\n 【  선택  】 ");
				quantity = ScanUtil.nextInt();

				if (userService.purchaseItem(200 * quantity)) {
					itemService.setUserItem(View.ITEM_DOUBLE, quantity, gameManager.getUserInfo().get("USER_NO").toString(), true);
					PrintUtil.bar3();
					PrintUtil.centerAlignment("점수 2배를 " + quantity + "개 만큼 구매하였습니다");
					PrintUtil.bar3();
				} else {
					PrintUtil.bar3();
					PrintUtil.centerAlignment("금액이 부족합니다");
					PrintUtil.bar3();
				}
				break;
			case 2:
				System.out.println("몇개를 구매하시겠습니까?");
				System.out.print("\n 【  선택  】 ");
				quantity = ScanUtil.nextInt();

				if (userService.purchaseItem(100 * quantity)) {
					itemService.setUserItem(View.ITEM_HINT, quantity, userService.getUserInfo().get("USER_NO").toString(), true);
					itemService.setUserItem(View.ITEM_DOUBLE, quantity,
							userService.getUserInfo().get("USER_NO").toString(), true);
					PrintUtil.bar3();
					PrintUtil.centerAlignment("점수 2배를 " + quantity + "개 만큼 구매하였습니다");
					PrintUtil.bar3();
				} else {
					PrintUtil.bar3();
					PrintUtil.centerAlignment("금액이 부족합니다");
					PrintUtil.bar3();
				}
				break;
			case 3:
				PrintUtil.bar3();
				PrintUtil.centerAlignment("몇개를 구매하시겠습니까?");
				PrintUtil.bar3();
				System.out.print("\n 【  선택  】 ");
				quantity = ScanUtil.nextInt();

				if (userService.purchaseItem(100 * quantity)) {
					itemService.setUserItem(View.ITEM_LIFE, quantity,
							userService.getUserInfo().get("USER_NO").toString(), true);
					PrintUtil.bar3();
					PrintUtil.centerAlignment("목숨 +2를 " + quantity + "개 만큼 구매하였습니다");
					PrintUtil.bar3();
				} else {
					PrintUtil.bar3();
					PrintUtil.centerAlignment("금액이 부족합니다");
					PrintUtil.bar3();
				}
				break;
			case 4:
				PrintUtil.bar3();
				PrintUtil.centerAlignment("아이템은 게임당 1번만 사용할 수 있으며 다음과 같은 효과를 가집니다");
				System.out.println("\t\t점수 2배 : 게임이 끝날시 얻는 점수를 2배로 얻습니다");
				System.out.println("\t\t초성힌트 : 사용시 해당 게임내내 초성 힌트를 얻습니다");
				System.out.println("\t\t목숨 +2 : 시작하는 목숨의 개수가 2개 늘어납니다");
				PrintUtil.bar3();
				break;
			case 5:
				return View.HOME_MAIN;
			default:
				return View.HOME_MAIN;
			}
			PrintUtil.bar3();
			PrintUtil.centerAlignment("계속 구매 하시겠습니까? (y/n)");
			PrintUtil.bar3();
			switch (ScanUtil.nextLine()) {
			case "y":
				return View.SHOP_MAIN;
			case "n":
				return View.HOME_MAIN;
			default:
				return View.HOME_MAIN;
			}
		} catch (NumberFormatException e) {
			PrintUtil.bar3();
			PrintUtil.centerAlignment("올바른 숫자를 입력하세요");
			PrintUtil.bar3();
			return View.SHOP_MAIN;
		}
	}

	// 퀴즈선택메뉴
	private int quizMenu() {
		PrintUtil.bar();
		PrintUtil.bar2();
		PrintUtil.bar2();
		PrintUtil.centerAlignment("퀴즈 카테고리 선택");
		PrintUtil.bar2();
		PrintUtil.centerAlignment("① 상식 퀴즈  ② 우리말 맞추기   ③ 역사 퀴즈   ④ 넌센스 퀴즈  ⑤ 뒤로가기   ");
		PrintUtil.bar2();
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n 【  선택  】 ");

		try {
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
		} catch (NumberFormatException e) {
			PrintUtil.bar3();
			PrintUtil.centerAlignment("올바른 숫자를 입력하세요");
			PrintUtil.bar3();
			return View.QUIZ_START; // 예외 발생 시 홈 메인으로 돌아감
		}
	}

	// 관리자 로그인시 출력되는 화면
	private int adminMain() {
		PrintUtil.bar();
		PrintUtil.bar2();
		PrintUtil.bar2();
		PrintUtil.centerAlignment("ADMIN MAIN");
		PrintUtil.bar2();
		PrintUtil.centerAlignment("1.문제관리   2.게시판   3.유저   4.로그아웃 ");
		PrintUtil.bar2();
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n 【  선택  】 ");

		try {
			switch (ScanUtil.nextInt()) {
			case 1:
				return View.QUIZ_MANAGE;
			case 2:
				return View.ADMIN_BOARD;
			case 3:
				return View.USER_MANAGE;
			case 4:
				return View.HOME;
//				return View.ADMIN_LOGOUT;
			default:
				return View.ADMIN_MAIN;
			}
		} catch (NumberFormatException e) {
			PrintUtil.bar3();
			PrintUtil.centerAlignment("올바른 숫자를 입력하세요");
			PrintUtil.bar3();
			return View.ADMIN_MAIN; // 예외 발생 시 홈 메인으로 돌아감
		}

	}

	
	
	// 문제 조회시 출력되는 화면
	public int questionList() {
		System.out.println();
		PrintUtil.bar();
		PrintUtil.bar2();
		PrintUtil.centerAlignment("퀴즈 목록");
		PrintUtil.bar2();
		PrintUtil.bar2();
		PrintUtil.centerAlignment("1.상식 퀴즈 조회   2.우리말 맞추기 문제 조회   3.역사 퀴즈 조회    4.넌센스 퀴즈 조회   5.뒤로가기");
		System.out.println();
		PrintUtil.bar();
		System.out.print("\n 【  선택  】 ");

		try {
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
		} catch (NumberFormatException e) {
			PrintUtil.bar3();
			PrintUtil.centerAlignment("올바른 숫자를 입력하세요");
			PrintUtil.bar3();
			return View.QUIZ_MANAGE; // 예외 발생 시 홈 메인으로 돌아감
		}

	}
	
	//커뮤니티 이용
	public int list() {
	    int currentPage = 1; // 현재 페이지
	    int totalPage = boardService.getTotalPage(); // 전체 페이지 수
	    while (true) {
	        PrintUtil.bar();
	        PrintUtil.centerAlignment(" 【 게시물 목록 】 ");
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
	        System.out.println("\t\t\t현재 페이지: " + currentPage + "/" + totalPage);
	        System.out.println("① 읽기 ② 생성 ③ 뒤로가기 ④ 이전페이지 ⑤ 다음페이지 ⑥ 나의글보기  ");
	        System.out.print("\n 【  선택  】 ");

	        switch (ScanUtil.nextInt()) {
	            case 1:
	                System.out.print("게시물 번호 입력: ");
	                int reqNo = ScanUtil.nextInt();
	                return boardService.read(reqNo);
	            case 2:
	                return boardService.create();
	            case 3:
	                return View.HOME_MAIN;
	            case 4:
	            	if(currentPage==1) {
	            		PrintUtil.bar3();
	        			PrintUtil.centerAlignment("이전 페이지가 없습니다");
	        			PrintUtil.bar3();
	            	}else {
	            		currentPage--;
	            	}
	            	break;
	            case 5:
	            	if(currentPage==totalPage) {
	            		PrintUtil.bar3();
	        			PrintUtil.centerAlignment("다음 페이지가 없습니다");
	        			PrintUtil.bar3();
	            	}else {
	            		currentPage++;
	            	}
	            	break;
	            case 6:
	            	int savedCurrentPage = currentPage;
	                int savedTotalPage = totalPage;
	            	
	                currentPage=1;
	                List<Map<String, Object>> myBoardList = new ArrayList<>();
	            	String currentUserNo = SessionUtil.getCurrentUserNo();

	            	for (int page = 1; page <= totalPage; page++) {
	                    // 현재 페이지 정보 업데이트
	                    currentPage = page;
	                    boardList = boardService.getBoardListByPage(currentPage);

	                    // 각 페이지의 글을 순회하면서 나의 글인지 확인하여 저장
	                    for (Map<String, Object> board : boardList) {
	                        String writerUserNo = board.get("USER_NO").toString();
	                        if (currentUserNo.equals(writerUserNo)) {
	                            myBoardList.add(board);
	                        }
	                    }
	                }
	            	PrintUtil.bar();
	                System.out.println("【 나의 글 목록 】");
	                System.out.println("no\t\ttitle\t\twriter");

	                for (int i = 0; i < myBoardList.size(); i++) {
	                    Map<String, Object> map = myBoardList.get(i);
	                    System.out.print(map.get("REQ_NO")
	                            + "\t\t" + map.get("REQ_TITLE")
	                            + "\t\t" + map.get("REQ_WRITER"));
	                    System.out.println();
	                }
	                System.out.println();
	                PrintUtil.bar();
	                System.out.println("① 뒤로가기");
	                System.out.print("\n 【  선택  】  ");
	                int choice = ScanUtil.nextInt();
	                if (choice == 1) {
	                    return View.BOARD;
	                } else {
	                	PrintUtil.bar3();
	        			PrintUtil.centerAlignment("잘못된 입력입니다.");
	        			PrintUtil.bar3();
	                }
	                break;
	            default:
	            	PrintUtil.bar3();
        			PrintUtil.centerAlignment("잘못된 입력입니다.");
        			PrintUtil.bar3();
	                break;
	        }
	    }
	}
	
	public int adminlist() {
		while(true) {
	        PrintUtil.bar();
	        PrintUtil.centerAlignment(" 【 게시물 관리 】 ");
	        System.out.println("no\t\ttitle\t\twriter");

	        List<Map<String, Object>> boardList = adminBoardService.boardList();
	        for (Map<String, Object> map : boardList) {
	            System.out.print(map.get("REQ_NO") + "\t\t" + map.get("REQ_TITLE") + "\t\t" + map.get("REQ_WRITER"));
	            System.out.println();
	        }
	        
	        System.out.println();
	        PrintUtil.bar();
	        System.out.println("① 읽기 ② 생성 ③ 뒤로가기");
	        System.out.print("\n 【  선택  】 ");

	        switch (ScanUtil.nextInt()) {
	            case 1:
	                System.out.print("게시물 번호 입력: ");
	                int reqNo = ScanUtil.nextInt();
	                return adminBoardService.read(reqNo);
	            case 2:
	                return adminBoardService.create();
	            case 3:
	                return View.ADMIN_MAIN;
	            default:
	                System.out.println("올바른 숫자를 입력하세요.");
	                break;
	        }
		}   
	}
}	            