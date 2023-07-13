package dao;

import java.util.*;

import util.JDBCUtil;
import util.View;

// DB 퀴즈풀에 연동해서 퀴즈 분야별로 불러오기, 정답 비교, 힌트 제공
public class QuizDAO {

	private static QuizDAO instance = null;

	private QuizDAO() {
	}

	public static QuizDAO getInstance() {
		if (instance == null)
			instance = new QuizDAO();
		return instance;
	}

	JDBCUtil jdbc = JDBCUtil.getInstance();

	// 장르 + 랜덤번호를 토대로 퀴즈를 중복되지 않게 랜덤으로 10개 뽑아옴
	public List<Map<String, Object>> getQuiz(int genre) {
		
		String sql = "SELECT DISTINCT * FROM (SELECT * FROM QUIZ ORDER BY DBMS_RANDOM.VALUE)" +
					 " WHERE ROWNUM <= 10 AND SUBSTR(QUIZ_NO, 1, 1) = " + genre;
		
		return jdbc.selectAll(sql);
	}

	// 퀴즈 목록 조회
	public List<Map<String, Object>> searchQuiz(int genre) {
		List<Map<String, Object>> quizList = new ArrayList<>();

		String quizCategory = "";

		switch (genre) {
		case View.QUIZ_COMMON_SENSE:
			quizCategory = "1";
			break;
		case View.QUIZ_KOREAN:
			quizCategory = "2";
			break;
		case View.QUIZ_HISTORY:
			quizCategory = "3";
			break;
		case View.QUIZ_NONSENSE:
			quizCategory = "4";
			break;
		}

		String sql = "SELECT * FROM QUIZ" + " WHERE SUBSTR(QUIZ_NO, 1, 1) = " + quizCategory;

		quizList = jdbc.selectAll(sql);

		return quizList;
	}
}