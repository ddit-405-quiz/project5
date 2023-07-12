package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import dao.QuizDAO;

public class QuizService {
	
	private static QuizService instance = null;
	private QuizService() {}
	public static QuizService getInstance() {
		if(instance == null) instance = new QuizService();
		return instance;
	}
	
	Scanner sc = new Scanner(System.in);
	UserService userService = UserService.getInstance();
	QuizDAO quizDAO = QuizDAO.getInstance();
	
	public List<Map<String, Object>> getQuiz(int genre) {
		
		List<Map<String, Object>> quizList = new ArrayList<>();
		
		quizList = quizDAO.getQuiz(genre);
		
		return quizList;
	}
}
