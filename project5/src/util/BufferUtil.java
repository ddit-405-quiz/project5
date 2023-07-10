package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BufferUtil {
	
	private static BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
	public static String readLine() throws IOException	{
		return buffer.readLine();
	}
	
	public static int nextInt() throws IOException {
		return Integer.parseInt(buffer.readLine());
	}

}
