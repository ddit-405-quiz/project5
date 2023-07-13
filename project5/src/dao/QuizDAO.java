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

		Set<Integer> quizNumbers = new HashSet<>();
		List<Map<String, Object>> quizList = new ArrayList<>();

		// 겹치지 않는 번호 n개 생성
		while (quizNumbers.size() < 5) {
			int number = (int) (Math.random() * 5) + 1;
			quizNumbers.add(number);
		}

		// 위에서 생성된 랜덤 번호만큼 퀴즈를 가져옴
		for (Integer element : quizNumbers) {

			String sql = "SELECT * FROM QUIZ " + "WHERE QUIZ_NO = " + genre + "000" + element;

			quizList.add(jdbc.selectOne(sql));
		}

		return quizList;
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