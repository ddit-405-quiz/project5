package util;

import java.awt.Font;

public class PrintUtil {
	
	static String bar = "□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□";
	static String bar2 = "-----------------------------------------------------------------";
    private static final Font font = new Font("Arial", Font.PLAIN, 12);
    
	/**
	 * □■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□
	 * 를 콘솔창에 출력
	 */
	public static void bar() {
		System.out.println(bar);
	}
	
	/**
	 * 공백을 콘솔창에 출력
	 */
	public static void bar2() {
		System.out.println();
	}
	
	/**
	 * -----------------------------------------------------------------
	 * 를 콘솔창에 출력
	 */
	public static void bar3() {
		System.out.println(bar2);
	}
	
	/**

	 * 가운데 정렬하고싶은 문자열을 매개변수로 입력하면 가운데로 정렬해서 출력시켜줌
	 * @param 정렬하고싶은 문자열
	 */
	public static void centerAlignment(String str) {
		int strLength = str.length();
		int totalLength = bar.length();
		int padding = (totalLength-strLength) / 2;
		
		if(str.charAt(0) >= 44032 && str.charAt(0) <= 55203)
		{
			padding = (int)(padding * 3.0/2) + 1;
		}
		
		for(int i = 0; i < padding; i++) {
			System.out.print(" ");
		}
		
		System.out.println(str);
	}
	
	public static void loginScreen() {
		System.out.println("ID : ");
		System.out.println("PW : ");
	}
}	
//	public static void startScreen() {
//		String[] str= {"Q","U","I","Z","G","A","M","E"};
//		String[] name1= {"김","영","남"};
//		String[] name2= {"김","가","람"};
//		String[] name3= {"윤","하"};
//		String[] name4= {"전","영","균"};
//		
//		int n1=0, n2=0, n3=0, n4=0;
//		String pressEnter="";
//		for(int i=0; i<str.length+name1.length+name2.length+name3.length+name4.length; i++) {
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		System.out.println("==================================");
//		System.out.println();
//		System.out.println();
//		System.out.println();
//		
//	}
//}
