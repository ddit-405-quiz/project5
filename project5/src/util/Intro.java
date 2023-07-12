package util;

public class Intro {
	
	String padding = "";
	
	String running1 = "ε=ε=┌(；　o_o)┘";
	String running2 = "ε＝ε＝ε＝ε＝┏(-ω-)┛";
	String running3 = "ε=┏(●´Д｀●)┛";
	String running4 = "ε＝ε＝ε＝┏( ・＿・)┛";

	public void startIntro() {
		
		for (int i = 0; i < 51; i++) {

			padding += " ";
			
			//51개의 바
			System.out.println("□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□");
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println(padding + running1);
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□");


			try {
			    // 1초(1000밀리초) 동안 기다리기
			    Thread.sleep(75);
			} catch (InterruptedException e) {
			    // InterruptedException 예외 처리
			    e.printStackTrace();
			}
		}
		
		padding = "";
		
		
		for (int i = 0; i < 51; i++) {


			padding += " ";
			
			//51개의 바
			System.out.println("□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□");
			System.out.println();
			System.out.println(padding + running1);
			System.out.println();
			System.out.println();
			System.out.println(padding + running2);
			System.out.println(padding + running3);
			System.out.println();
			System.out.println();
			System.out.println(padding + running4);
			System.out.println();
			System.out.println("□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□");


			try {
			    // 1초(1000밀리초) 동안 기다리기
			    Thread.sleep(250);
			} catch (InterruptedException e) {
			    // InterruptedException 예외 처리
			    e.printStackTrace();
			}
		}
	}
}
