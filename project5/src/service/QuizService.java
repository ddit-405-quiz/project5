package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import dao.QuizDAO;
import util.PrintUtil;
import util.ScanUtil;
import util.View;
import util.GameManager;

public class QuizService {

	private static QuizService instance = null;

	private QuizService() {
	}

	public static QuizService getInstance() {
		if (instance == null)
			instance = new QuizService();
		return instance;
	}

	GameManager gameManager = GameManager.getInstance();
	UserService userService = UserService.getInstance();
	QuizDAO quizDAO = QuizDAO.getInstance();

	
	// ���� 10���� ������ �޼ҵ�
	public List<Map<String, Object>> getQuiz(int genre) {

		List<Map<String, Object>> quizList = new ArrayList<>();

		quizList = quizDAO.getQuiz(genre);

		return quizList;
	}

	// �������
	public int startQuiz(int genre) {

		// ���� ���۽� �������� ���
		ItemService.getInstance().useItem();

		// ���� ���� ������ ����, ���� ���۽� 0���� �ʱ�ȭ �Ǹ鼭 ������ ������� 1�� ����
		gameManager.resetCorrectCount();

		// ����� �ϴ� ���� ���۽� 0���� �ʱ�ȭ�ϰ�
		int life;

		// useLife = booleanŸ�� �ε� useItem()�޼ҵ忡�� ���+2 �������� ����ϱ�� �ߴٸ� ����� 4����, �ƴϸ� 2����
		// ������
		if (gameManager.hasItem(View.ITEM_LIFE)) {
			life = 4;
		} else {
			life = 2;
		}

		// quizList��� ����Ʈ�� �ϳ� �����
		List<Map<String, Object>> quizList = new ArrayList<>();
		// quizList�� getQuiz�޼ҵ�� �帣�� �������ְ� ���� 10���� �޾ƿ�
		quizList = getQuiz(genre);

		for (int i = 0; i < 5; i++) {

			// 2�� Ʋ���� ���ӿ���
			if (life <= 0) {
				return View.QUIZ_FAIL;
			}

			PrintUtil.bar();
			System.out.println("\t        Q" + (i + 1));
			PrintUtil.bar2();
			System.out.println(quizList.get(i).get("QUIZ_DETAIL"));
			if (gameManager.hasItem(View.ITEM_HINT)) {
				PrintUtil.bar2();
				System.out.println("�ʼ���Ʈ : " + quizList.get(i).get("QUIZ_HINT"));
			}
			PrintUtil.bar();
			System.out.print("\n ��  �����Է� �� ");
			String answer = ScanUtil.nextLine();

			if (answer.equals(quizList.get(i).get("QUIZ_ANSWER"))) {
				PrintUtil.bar2();
				System.out.println("\t          �����Դϴ�!");
				PrintUtil.bar2();
				gameManager.setCorrectCount();
			} else {
				PrintUtil.bar2();
				System.out.println("\t          �����Դϴ�!");
				life--;
				System.out.println("\t ������� : " + life);
				PrintUtil.bar2();
			}
		}

		return View.QUIZ_SUCCESS;
	}

	// ���н� ����Ǵ� �޼ҵ�
	public int failMenu() {
		PrintUtil.bar();
		System.out.println("\t  ���� Ǯ�⿡ �����ϼ̽��ϴ�!");
		PrintUtil.bar2();
		if (gameManager.hasItem(View.ITEM_DOUBLE)) {
			userService.setUserScore(gameManager.getCorrectCount() * 2);
		} else {
			userService.setUserScore(gameManager.getCorrectCount());
		}
		userService.setUserGameMoney(gameManager.getCorrectCount() * 10);
		Object score = userService.getUserInfo().get("USER_SCORE");
		System.out.println("    ���Ṯ��   " + gameManager.getCorrectCount() + " / 10" + "       �� ���� : " + score);
		PrintUtil.bar2();
		System.out.println("\t�� ����Ǯ��   �� ���θ޴�   ");
		PrintUtil.bar();
		System.out.print("\n ��  ���� �� ");

		gameManager.resetItem();

		switch (ScanUtil.nextInt()) {
		case 1:
			return View.QUIZ_START;
		case 2:
			return View.HOME_MAIN;
		default:
			return View.HOME_MAIN;
		}
	}

	// ������ ����Ǵ� �޼ҵ�
	public int success() {
		PrintUtil.bar();
		System.out.println("\t  ���� Ǯ�⿡ �����ϼ̽��ϴ�!");
		PrintUtil.bar2();
		if (gameManager.hasItem(View.ITEM_DOUBLE)) {
			userService.setUserScore(gameManager.getCorrectCount() * 2);
		} else {
			userService.setUserScore(gameManager.getCorrectCount());
		}
		userService.setUserGameMoney(gameManager.getCorrectCount() * 10);
		Object score = userService.getUserInfo().get("USER_SCORE");
		System.out.println("    ���Ṯ��   " + gameManager.getCorrectCount() + " / 10" + "       �� ���� : " + score);
		PrintUtil.bar2();
		System.out.println("\t�� ����Ǯ��   �� ���θ޴�   ");
		PrintUtil.bar();
		System.out.print("\n ��  ���� �� ");

		gameManager.resetItem();

		switch (ScanUtil.nextInt()) {
		case 1:
			return View.QUIZ_START;
		case 2:
			return View.HOME_MAIN;
		default:
			return View.HOME_MAIN;
		}
	}

	// ������ �帣�� ���� �����ֱ�
	public void searchQuiz(int genre) {

		List<Map<String, Object>> quizList = quizDAO.searchQuiz(genre);

		switch (genre) {
		case View.QUIZ_COMMON_SENSE:
			System.out.println("��� ���� ���");
			break;
		case View.QUIZ_KOREAN:
			System.out.println("�츮�� ���� ���");
			break;
		case View.QUIZ_HISTORY:
			System.out.println("���� ���� ���");
			break;
		case View.QUIZ_NONSENSE:
			System.out.println("�ͼ��� ���� ���");
			break;
		}

		quizList = quizDAO.searchQuiz(genre);

		for (Map<String, Object> quiz : quizList) {
			int quizId = Integer.parseInt(quiz.get("QUIZ_NO").toString());
			String question = (String) quiz.get("QUIZ_DETAIL");
			String answer = (String) quiz.get("QUIZ_ANSWER");
			System.out.println("���� ID: " + quizId);
			System.out.println(" ����   : " + question);
			System.out.println(" ����   : " + answer);
			System.out.println();
		}
	}
}
