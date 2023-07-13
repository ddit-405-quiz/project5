package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.UserDAO;
import util.BufferUtil;
import util.Connect;
import util.JDBCUtil;
import util.PrintUtil;
import util.ScanUtil;
import util.SessionUtil;
import util.View;

public class UserService {

	private static UserService instance = null;

	private UserService() {
	}

	public static UserService getInstance() {
		if (instance == null) {
			instance = new UserService();
		}
		return instance;
	}

	UserDAO userDAO = UserDAO.getInstance();

	// 유저의 정보를 저장하고, 외부에서 확인할 수 있게 하기 위한 변수와 메소드
	private Map<String, Object> userInfo = null;

	public Map<String, Object> getUserInfo() {
		return userDAO.getUserInfo(userInfo.get("USER_NO").toString());
	}

	// 로그인
	public int logIn() {
		PrintUtil.loginScreen();
		System.out.print("ID >> ");
		String memId = ScanUtil.nextLine();

		// id확인
		if (memId == null || !userDAO.checkId(memId)) {
			PrintUtil.bar3();
			PrintUtil.centerAlignment("해당 ID는 존재하지 않습니다.");
			PrintUtil.bar3();
			return View.HOME;
		}

		System.out.print("비밀번호 >> ");
		String memPass = ScanUtil.nextLine();

		List<Object> param = new ArrayList<>();
		param.add(memId);
		param.add(memPass);

		userInfo = userDAO.login(param);

		if (userInfo != null) { //로그인o
			
			String userNo = (String) userInfo.get("USER_NO");
			SessionUtil.setCurrentUserNo(userNo);

			Controller.sessionStorage.put("loginInfo", userInfo);
			PrintUtil.bar3();
			PrintUtil.centerAlignment(userInfo.get("USER_NAME") + "님 환영합니다.");
			PrintUtil.centerAlignment("다음 화면으로 이동하려면 Enter 키를 입력하세요.");
			PrintUtil.bar3();
			ScanUtil.nextLine();
			return View.HOME_MAIN;
		} else { // 로그인x
			boolean run = true;
			while (run) {
				PrintUtil.bar3();
				PrintUtil.centerAlignment("비밀번호가 틀렸습니다");
				PrintUtil.centerAlignment("새 비밀번호를 만드시겠습니까?");
				PrintUtil.bar3();
				System.out.println("① 예    ② 아니오");
				System.out.printf("입력 ☞ ");
				try {
					int select = ScanUtil.nextInt();

					switch (select) {
					case 1:
						try {
							userPasswordReset();
						} catch (Exception e) {
							e.printStackTrace();
						}
						run = false;
						break;
					case 2:
						PrintUtil.bar3();
						PrintUtil.centerAlignment("메인 메뉴로 돌아갑니다");
						PrintUtil.bar3();
						System.out.println();
						run = false;
						break;
					default:
						PrintUtil.centerAlignment("\"예\" 또는 \"아니오\"를 선택하세요");
						System.out.println();
						break;
					}
				} catch (NumberFormatException e) {
					PrintUtil.bar3();
					PrintUtil.centerAlignment("올바른 숫자를 입력하세요");
					PrintUtil.bar3();
				}

			}
			return View.HOME;
		}

	}

	// 회원가입
	public int signUp() {
		int result = 0;
		System.out.println("<< 회 원 가 입 >>");

		System.out.print("이름: ");
		String name = ScanUtil.nextLine();

		System.out.print("아이디: ");
		String userId = ScanUtil.nextLine();

		System.out.print("비밀번호: ");
		String userPw = ScanUtil.nextLine();

		System.out.print("전화번호: ");
		String userTel = ScanUtil.nextLine();

		PrintUtil.bar3();
		PrintUtil.centerAlignment("정보를 저장하겠습니까?  (y / n)");
		PrintUtil.bar3();
		String flag = ScanUtil.nextLine();
		if (flag.equalsIgnoreCase("y")) {
			result = userDAO.signUp(name, userId, userPw, userTel);
		}
		if (result != 0) {
			PrintUtil.bar3();
			PrintUtil.centerAlignment((name + "회원님의 자료 입력이 정상 처리되었습니다."));
			PrintUtil.bar3();
			return View.USER_LOGIN;
		} else {
			PrintUtil.bar3();
			PrintUtil.centerAlignment(name + "회원님의 자료 입력이 취소되었습니다.");
			PrintUtil.bar3();
			return View.HOME;
		}
	}

	// 비밀번호찾기
	public void userPasswordReset() throws Exception {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		con = Connect.getConnection();
		stmt = con.createStatement();

		PrintUtil.bar3();
		PrintUtil.centerAlignment("아래의 회원 정보를 입력하세요");
		PrintUtil.bar3();
		System.out.println();
		System.out.print("아이디를 입력하세요 ☞  ");
		String user_id = ScanUtil.nextLine();
		System.out.print("이름을 입력하세요 ☞  ");
		String user_name = ScanUtil.nextLine();
		System.out.print("전화번호를 입력하세요 ☞  ");
		String user_ph = ScanUtil.nextLine();

		String sql = "SELECT USER_ID, USER_NAME, USER_PH" + " FROM USERS" + " WHERE USER_ID = " + "'" + user_id + "'"
				+ " AND USER_NAME = " + "'" + user_name + "'" + " AND USER_PH = " + "'" + user_ph + "'";

		rs = stmt.executeQuery(sql);

		try {
			if (rs.next()) {

				boolean run = true;

				while (run) {
					PrintUtil.bar3();
					PrintUtil.centerAlignment("회원번호가 일치합니다");
					PrintUtil.centerAlignment("임시로 비밀번호를 생성하시겠습니까?");
					PrintUtil.centerAlignment("① 예    ② 아니오");
					PrintUtil.bar3();

					System.out.print("입력 ☞ ");

					int select = ScanUtil.nextInt();

					switch (select) {
					case 1:
						createRandomPw(user_id);
						run = false;
					case 2:
						PrintUtil.bar3();
						PrintUtil.centerAlignment("메인 메뉴로 돌아갑니다");
						PrintUtil.bar3();
						System.out.println();
						run = false;
						break;
					default:
						PrintUtil.bar3();
						PrintUtil.centerAlignment("\"예\" 또는 \"아니오\"를 선택하세요");
						PrintUtil.bar3();
						System.out.println();
						break;
					}
				}

			} else {
				PrintUtil.bar3();
				PrintUtil.centerAlignment("회원정보가 틀렸습니다");
				PrintUtil.centerAlignment("메인 메뉴로 돌아갑니다");
				PrintUtil.bar3();

			}
		} catch (NumberFormatException e) {
			PrintUtil.bar3();
			PrintUtil.centerAlignment("새 비밀번호 만들기가 취소되었습니다.");
			PrintUtil.centerAlignment("첫 화면으로 돌아갑니다.");
			PrintUtil.bar3();
		}
	}

	// 임시비밀번호
	public void createRandomPw(String user_id) throws Exception {

		String[] strSet = { "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k",
				"l", "z", "x", "c", "v", "b", "n", "m", "1", "2", "3", "4", "5", "6", "7", "8", "9", "~", "!", "@", "#",
				"$", "%", "^", "&", "*" };
		String randomPw = "";
		for (int i = 0; i < 5; i++) {
			int idx = (int) (Math.random() * 44) + 1;
			randomPw += strSet[idx];
		}

		Connection con = null;
		Statement stmt = null;
		int rs;

		con = Connect.getConnection();
		stmt = con.createStatement();

		String sql = "UPDATE USERS SET USER_PW = " + "'" + randomPw + "'" + " WHERE USER_ID = " + "'" + user_id + "'";

		rs = stmt.executeUpdate(sql);

		if (rs != 0) {
			PrintUtil.bar3();
			PrintUtil.centerAlignment("임시 비밀번호가 생성되었습니다");
			PrintUtil.centerAlignment("임시 비밀번호 : " + randomPw);
			PrintUtil.centerAlignment("마이페이지에서 꼭 변경하세요");
			PrintUtil.bar3();
			System.out.println();
		}
	}

	/**
	 * 유저의 스코어를 매개변수로 입력받은 만큼 증가시킴
	 * 
	 * @param 증가시킬 스코어
	 */
	public void setUserScore(int score) {
		if (score >= 0) {
			String sql = "UPDATE USERS" + " SET USER_SCORE = USER_SCORE + " + score + " WHERE USER_NO = "
					+ userInfo.get("USER_NO");

			userDAO.setUserScore(sql);
		}
	}

	/**
	 * 유저의 게임머니를 매개변수로 입력받은 만큼 증가시킴
	 * 
	 * @param 증가시킬 금액
	 */
	public void setUserGameMoney(int money) {
		if (money >= 0) {
			String sql = "UPDATE USERS" + " SET USER_GM = USER_GM + " + money + " WHERE USER_NO = "
					+ userInfo.get("USER_NO");

			userDAO.setUserScore(sql);
		}
	}

	/**
	 * 상점에서 아이템 구매 시 금액 체크 후 금액이 맞으면 아이템 구매
	 * 
	 * @param 구매할 금액
	 * @return 금액이 존재하면 구매후 true를 반환, 존재하지 않으면 false를 반환
	 */
	public boolean purchaseItem(int money) {

		if (Integer.parseInt(getUserInfo().get("USER_GM").toString()) >= money) {
			String sql = "UPDATE USERS" + " SET USER_GM = USER_GM - " + money + " WHERE USER_NO = "
					+ userInfo.get("USER_NO");

			userDAO.setUserScore(sql);
			return true;
		} else {
			return false;
		}
	}

	//회원정보수정
	public int update() {

		Map<String, Object> userInfo = (Map<String, Object>) Controller.sessionStorage.get("loginInfo");
		System.out.print("아이디를 입력하시오: ");
		String userid = ScanUtil.nextLine();
		
		if(!userInfo.get("USER_ID").equals(userid)) {
			System.out.println("변경 안돼 돌아가");
			return View.MYPAGE_UPDATE;
		}
		
		//로그인 아이디랑 입력받은 아이디가 같으면 아래가 진행된다. 
		
		int result = 0;
		String userpw = "";
		String name = "";
		String telNum = "";
		String str = "";
		
		System.out.println("<< 회원 정보 수정 >>");
		
		System.out.print("이름을 변경하겠습니까? ( y / n ) : ");
		if(ScanUtil.nextLine().equalsIgnoreCase("y")) {
			System.out.print("변경할 이름: ");
			name = ScanUtil.nextLine();
			str = str + " USER_NAME = '" + name + "' ,";
		}
		System.out.print("비밀번호를 변경하겠습니까? ( y / n ) : ");
		if(ScanUtil.nextLine().equalsIgnoreCase("y")) {
			System.out.print("변경할 비밀번호: ");
			userpw = ScanUtil.nextLine();
			str = str + " USER_PW = '" + userpw + "' ,";
		}
		System.out.print("전화번호를 변경하겠습니까? ( y / n ) : ");
		if(ScanUtil.nextLine().equalsIgnoreCase("y")) {
			System.out.print("변경할 전화번호: ");
			telNum = ScanUtil.nextLine();
			str = str + " USER_PH = '" + telNum + "' ,";
		}
		
		int length = str.length();
		if(length != 0) {
			str = str.substring(0, length-2);
		}
		
		result = userDAO.update(str, userid);
		
		if(result != 0) {
			PrintUtil.bar3();
			PrintUtil.centerAlignment("정보가 정상적으로 수정되었습니다.");
			PrintUtil.bar3();
		} else {
			PrintUtil.bar3();
			PrintUtil.centerAlignment("정보 변경이 취소 되었습니다.");
			PrintUtil.bar3();
		}
		
		return View.HOME_MAIN;
	}
	
	//회원 탈퇴
	
	

	
}