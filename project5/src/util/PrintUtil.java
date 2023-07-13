package util;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class PrintUtil {
	
	static String bar = "□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□";
	static String bar2 = "-----------------------------------------------------------------";
    private static final Font font = new Font("Arial", Font.PLAIN, 12);
    
	public static void bar() {
		System.out.println(bar);
	}
	
	public static void bar2() {
		System.out.println();
	}
	
	public static void bar3() {
		System.out.println(bar2);
	}
	
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
