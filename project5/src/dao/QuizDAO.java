package dao;

import java.util.*;

import util.JDBCUtil;

// DB 퀴즈풀에 연동해서 퀴즈 분야별로 불러오기, 정답 비교, 힌트 제공
public class QuizDAO {
	
	private static QuizDAO instance = null;
	private QuizDAO() {}
	public static QuizDAO getInstance() {
		if(instance == null) instance = new QuizDAO();
		return instance;
	}
	
	JDBCUtil jdbc = JDBCUtil.getInstance();
	

	
	public List<Map<String, Object>> getQuiz(int genre) { 
		
		Set<Integer> quizNumbers = new HashSet<>();
		List<Map<String, Object>> quizList = new ArrayList<>();
		
		//겹치지 않는 번호 n개 생성
		while (quizNumbers.size() < 5) {
	        int number = (int)(Math.random()*5) + 1;
	        quizNumbers.add(number);
	    }

		//위에서 생성된 랜덤 번호만큼 퀴즈를 가져옴
		for(Integer element : quizNumbers) {
			
			String sql = "SELECT * FROM QUIZ "
					   + "WHERE QUIZ_NO = " + genre + "000" + element;	
			
			quizList.add(jdbc.selectOne(sql)); 
		}
		
		return quizList;
	}
}