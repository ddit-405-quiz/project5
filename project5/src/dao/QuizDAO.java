package dao;

import java.util.*;

import util.JDBCUtil;
import util.View;

// DB 퀴즈풀에 연동해서 퀴즈 분야별로 불러오기, 정답 비교, 힌트 제공
public class QuizDAO {

	private static QuizDAO instance = null;
	private QuizDAO() {}
	public static QuizDAO getInstance() {
		if (instance == null)
			instance = new QuizDAO();
		return instance;
	}

	JDBCUtil jdbc = JDBCUtil.getInstance();

	/**
	 * 입력된 장르를 바탕으로 랜덤으로 10개의 퀴즈를 뽑아오며, 뽑아온 퀴즈들을 테이블 정보를 리턴
	 * @param 퀴즈의 장르 입력
	 * @return 뽑아온 퀴즈들을 List<Map<String,Object>> 타입으로 반환
	 */
	public List<Map<String, Object>> getQuiz(int genre) {
		
		String sql = "SELECT DISTINCT * FROM (SELECT * FROM QUIZ ORDER BY DBMS_RANDOM.VALUE)" +
					 " WHERE ROWNUM <= 10 AND SUBSTR(QUIZ_NO, 1, 1) = " + genre;
		
		return jdbc.selectAll(sql);
	}
}