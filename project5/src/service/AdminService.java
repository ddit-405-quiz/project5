package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.AdminDAO;
import util.PrintUtil;
import util.ScanUtil;
import util.View;

public class AdminService {
	private static AdminService instance = null;
	private AdminService () {}
	public static AdminService getInstance() {
		if(instance == null) {
			instance = new AdminService();
		}
		return instance;
	}
	
	AdminDAO adminDAO = AdminDAO.getInstance();
	
	// ������ ������ �����ϰ�, �ܺο��� Ȯ���� �� �ְ� �ϱ� ���� ������ �޼ҵ�
		private Map<String, Object> adminInfo = null;
		public Map<String, Object> getAdminInfo(){
			return adminDAO.getAdminInfo(adminInfo.get("ADMIN_NO").toString());
		}
	
	public int adminLogin() {
		PrintUtil.loginScreen();
		PrintUtil.bar2();
		System.out.print("ID >> ");
		String adminId = ScanUtil.nextLine();
		System.out.print("��й�ȣ >> ");
		String adminPass = ScanUtil.nextLine();
		
		List<Object> param = new ArrayList<>(); 
		param.add(adminId); 
		param.add(adminPass);
		
		adminInfo = adminDAO.adminlogin(param);
		
		if(adminInfo != null) { //������ �α��� o
			Controller.sessionStorage.put("adminInfo", adminInfo); 
			System.out.println(adminInfo.get("ADMIN_NAME") + " �����ڴ� ȯ���մϴ�.");
			System.out.println("���� ȭ������ �̵��Ϸ��� Enter Ű�� �Է��ϼ���.");
			ScanUtil.nextLine();
			return View.ADMIN_MAIN;
		} else { // �α���x
			System.out.println("�� ������ �α��ο� �����߽��ϴ�. ��");
			System.out.println();
			return View.HOME;
		}
			
	}
}