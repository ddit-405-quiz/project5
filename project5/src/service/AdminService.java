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
	
	// 유저의 정보를 저장하고, 외부에서 확인할 수 있게 하기 위한 변수와 메소드
		private Map<String, Object> adminInfo = null;
		public Map<String, Object> getAdminInfo(){
			return adminDAO.getAdminInfo(adminInfo.get("ADMIN_NO").toString());
		}
	
	public int adminLogin() {
		PrintUtil.loginScreen();
		PrintUtil.bar2();
		System.out.print("ID >> ");
		String adminId = ScanUtil.nextLine();
		System.out.print("비밀번호 >> ");
		String adminPass = ScanUtil.nextLine();
		
		List<Object> param = new ArrayList<>(); 
		param.add(adminId); 
		param.add(adminPass);
		
		adminInfo = adminDAO.adminlogin(param);
		
		if(adminInfo != null) { //정상적 로그인 o
			Controller.sessionStorage.put("adminInfo", adminInfo); 
			System.out.println(adminInfo.get("ADMIN_NAME") + " 관리자님 환영합니다.");
			System.out.println("다음 화면으로 이동하려면 Enter 키를 입력하세요.");
			ScanUtil.nextLine();
			return View.ADMIN_MAIN;
		} else { // 로그인x
			System.out.println("【 관리자 로그인에 실패했습니다. 】");
			System.out.println();
			return View.HOME;
		}
			
	}
}