package util;

import java.util.Scanner;

public class ScanUtil {
		private static Scanner sc=new Scanner(System.in);
		
		public static String nextLine() {
			return sc.nextLine();
		}
		
		public static int nextInt() {
			return Integer.parseInt(sc.nextLine());
		}

}