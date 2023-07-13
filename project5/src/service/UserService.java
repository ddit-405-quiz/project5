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
	// �ʿ��� �����͸� �����ϴ� Ŭ����
	private static UserService instance = null;
	private UserService () {}
	public static UserService getInstance() {
		if(instance == null) {
			instance = new UserService();
		}
		return instance;
	}
	
	UserDAO userDAO = UserDAO.getInstance();
	
	// ������ ������ �����ϰ�, �ܺο��� Ȯ���� �� �ְ� �ϱ� ���� ������ �޼ҵ�
	private Map<String, Object> userInfo = null;
	public Map<String, Object> getUserInfo(){
		return userDAO.getUserInfo(userInfo.get("USER_NO").toString());
	}
	
	//�α���
	public int logIn() {
		PrintUtil.loginScreen();
		System.out.print("ID >> ");
		String memId = ScanUtil.nextLine();
		System.out.print("��й�ȣ >> ");
		String memPass = ScanUtil.nextLine();
		
		List<Object> param = new ArrayList<>(); 
		param.add(memId); 
		param.add(memPass);
		
		userInfo = userDAO.login(param);
		
		if(userInfo != null) { //������ �α��� o
			// ���� �α��ε� ȸ���� USER_NO ����
			String userNo = (String) userInfo.get("USER_NO");
            SessionUtil.setCurrentUserNo(userNo);
			
			Controller.sessionStorage.put("loginInfo", userInfo); 
			System.out.println(userInfo.get("USER_NAME") + "�� ȯ���մϴ�.");
			System.out.println("���� ȭ������ �̵��Ϸ��� Enter Ű�� �Է��ϼ���.");
			ScanUtil.nextLine();
			return View.HOME_MAIN;
		} else { // �α���x
			boolean run = true;
			while(run) {
				PrintUtil.bar();
				System.out.println("��й�ȣ�� Ʋ�Ƚ��ϴ�");
				System.out.println("�� ��й�ȣ�� ����ðڽ��ϱ�?");
				PrintUtil.bar();
				System.out.println("�� ��    �� �ƴϿ�");
				System.out.printf("�Է� �� ");
				int select = ScanUtil.nextInt();
			
				switch(select) {
					case 1:
					try {
						userPasswordReset();
					} catch (Exception e) {
						
						e.printStackTrace();
					}
						run = false;
						break;
					case 2:
						System.out.println("------------------");
						System.out.println("���� �޴��� ���ư��ϴ�");
						System.out.println("------------------");
						System.out.println();
						run = false;
						break;
					default:
						System.out.print("\"��\" �Ǵ� \"�ƴϿ�\"�� �����ϼ���");
						System.out.println();
						break;
				}
				
			}
			return View.HOME;
		}
		
	}
	//ȸ������
	public int signUp() {
		int result = 0;
		System.out.println("<< ȸ �� �� �� >>");
		
		System.out.print("�̸�: ");
		String name = ScanUtil.nextLine();
		
		System.out.print("���̵�: ");
		String userId = ScanUtil.nextLine();
		
		System.out.print("��й�ȣ: ");
		String userPw = ScanUtil.nextLine();
		
		System.out.print("��ȭ��ȣ: ");
		String userTel = ScanUtil.nextLine();
		
		System.out.println("������ �����ϰڽ��ϱ�?  (y / n)");
		String flag = ScanUtil.nextLine();
		if(flag.equalsIgnoreCase("y")) {
			result = userDAO.signUp(name, userId, userPw, userTel);
		}
		if(result != 0) {
			System.out.println(name + "ȸ������ �ڷ� �Է��� ���� ó���Ǿ����ϴ�.");
			return View.USER_LOGIN;
		} else {
			System.out.println(name + "ȸ������ �ڷ� �Է��� ��ҵǾ����ϴ�.");
			return View.HOME;
		}
	}
	//��й�ȣã��
	public void userPasswordReset() throws Exception{
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		con = Connect.getConnection();
		stmt = con.createStatement();
		
		System.out.println("------------------");
        System.out.println("�Ʒ��� ȸ�� ������ �Է��ϼ���");
        System.out.println("------------------");
        System.out.println();
        System.out.println("���̵� �Է��ϼ��� �� ");
		String user_id = ScanUtil.nextLine();
		System.out.println("�̸��� �Է��ϼ��� �� ");
		String user_name = ScanUtil.nextLine();
		System.out.println("��ȭ��ȣ�� �Է��ϼ��� �� ");
		String user_ph = ScanUtil.nextLine();
		
		String sql = "SELECT USER_ID, USER_NAME, USER_PH"+
					 " FROM USERS"+
					 " WHERE USER_ID = "+"'"+user_id+"'"+
					 " AND USER_NAME = "+"'"+user_name+"'"+
					 " AND USER_PH = "+"'"+user_ph+"'";
		
		rs = stmt.executeQuery(sql);
	
		if(rs.next()) {
			
			boolean run = true;
			
			while(run) {
				
				
			System.out.println("------------------");
			System.out.println("ȸ����ȣ�� ��ġ�մϴ�");
			System.out.println("�ӽ÷� ��й�ȣ�� �����Ͻðڽ��ϱ�?");
			System.out.println("�� ��    �� �ƴϿ�");
			System.out.println("------------------");
				
			System.out.println("�Է� �� ");
			
			int select = ScanUtil.nextInt();
			
			switch(select) {
				case 1:
					createRandomPw(user_id);
					run = false;
				case 2:
					System.out.println("------------------");
					System.out.println("���� �޴��� ���ư��ϴ�");
					System.out.println("------------------");
					System.out.println();
					run = false;
					break;
				default:
					System.out.println("------------------");
					System.out.print("\"��\" �Ǵ� \"�ƴϿ�\"�� �����ϼ���");
					System.out.println("------------------");
					System.out.println();
					break;
			}
			
				
			
		}
			
		}else {
			System.out.println("------------------");
	        System.out.println("ȸ�������� Ʋ�Ƚ��ϴ�");
	        System.out.println("���� �޴��� ���ư��ϴ�");
	        System.out.println("------------------");
	        System.out.println();
		}
	}
	//�ӽú�й�ȣ
	public void createRandomPw(String user_id) throws Exception{
	
		String[] strSet = {"q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m",
						"1", "2", "3", "4", "5", "6", "7", "8", "9", "~", "!", "@", "#", "$", "%", "^", "&", "*"};
		String randomPw = "";
		for(int i = 0; i<5; i++) {
			int idx = (int)(Math.random()*44)+1;
			randomPw += strSet[idx];
		}
	
		Connection con = null;
		Statement stmt = null;
		int rs;
	
		con = Connect.getConnection();
		stmt = con.createStatement();
	
		String sql = "UPDATE USERS SET USER_PW = "+"'"+randomPw+"'"+
				 	" WHERE USER_ID = "+"'"+user_id+"'";
	
		rs = stmt.executeUpdate(sql);
	
		if(rs !=0 ) {
			System.out.println("------------------");
			System.out.println("�ӽ� ��й�ȣ�� �����Ǿ����ϴ�");
			System.out.println("�ӽ� ��й�ȣ : "+randomPw);
			System.out.println("�������������� �� �����ϼ���");
			System.out.println("------------------");
			System.out.println();		
		}
	}
	// ������ ���ھ �Ű������� �Է¹��� ������ŭ ����
	public void setUserScore(int score) {
		if(score >= 0) {
			String sql =  "UPDATE USERS" +
						  " SET USER_SCORE = USER_SCORE + " + score +
						  " WHERE USER_NO = " + userInfo.get("USER_NO");

			userDAO.setUserScore(sql);
		} 
	}
	// ������ ���ӸӴϸ� �Ű������� �Է¹��� ������ŭ ����
	public void setUserGameMoney(int money) {
		if(money >= 0) {
			String sql =  "UPDATE USERS" +
						  " SET USER_GM = USER_GM + " + money +
						  " WHERE USER_NO = " + userInfo.get("USER_NO");

			userDAO.setUserScore(sql);
		} 
	}
	//	�������� ������ ���� �� �ݾ� üũ �� �ݾ��� ������ ������ ����
	public boolean purchaseItem(int money) {
		
		if (Integer.parseInt(getUserInfo().get("USER_GM").toString()) >= money){
			String sql =  "UPDATE USERS" +
					  " SET USER_GM = USER_GM - " + money +
					  " WHERE USER_NO = " + userInfo.get("USER_NO");
			
			userDAO.setUserScore(sql);
			return true;
		} else {
			return false;
		}
	}
}