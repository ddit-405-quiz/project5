package dao;

import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class QuizListDAO {

	private static QuizListDAO instance = null;
	private QuizListDAO() {}
	public static QuizListDAO getInstance() {
		if(instance == null) instance = new QuizListDAO();
		return instance;
	}
	
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> getQuizList(String sql){
		return jdbc.selectAll(sql);
	}
	
	public int updateQuiz(String sql) throws Exception {
		return jdbc.update(sql);
	}
}