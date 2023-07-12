package dao;

import java.util.*;

import util.JDBCUtil;
import util.View;

// DB ����Ǯ�� �����ؼ� ���� �оߺ��� �ҷ�����, ���� ��, ��Ʈ ����
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

	// �帣 + ������ȣ�� ���� ��� �ߺ����� �ʰ� �������� 10�� �̾ƿ�
	public List<Map<String, Object>> getQuiz(int genre) {

		Set<Integer> quizNumbers = new HashSet<>();
		List<Map<String, Object>> quizList = new ArrayList<>();

		// ��ġ�� �ʴ� ��ȣ n�� ����
		while (quizNumbers.size() < 5) {
			int number = (int) (Math.random() * 5) + 1;
			quizNumbers.add(number);
		}

		// ������ ������ ���� ��ȣ��ŭ ��� ������
		for (Integer element : quizNumbers) {

			String sql = "SELECT * FROM QUIZ " + "WHERE QUIZ_NO = " + genre + "000" + element;

			quizList.add(jdbc.selectOne(sql));
		}

		return quizList;
	}

	// ���� ��� ��ȸ
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