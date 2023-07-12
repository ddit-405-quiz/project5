package dao;

import java.util.*;

import util.JDBCUtil;

// DB ����Ǯ�� �����ؼ� ���� �оߺ��� �ҷ�����, ���� ��, ��Ʈ ����
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
		
		//��ġ�� �ʴ� ��ȣ n�� ����
		while (quizNumbers.size() < 5) {
	        int number = (int)(Math.random()*5) + 1;
	        quizNumbers.add(number);
	    }

		//������ ������ ���� ��ȣ��ŭ ��� ������
		for(Integer element : quizNumbers) {
			
			String sql = "SELECT * FROM QUIZ "
					   + "WHERE QUIZ_NO = " + genre + "000" + element;	
			
			quizList.add(jdbc.selectOne(sql)); 
		}
		
		return quizList;
	}
}