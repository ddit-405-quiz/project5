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

	// 퀴즈 10개를 얻어오는 메소드
	public List<Map<String, Object>> getQuiz(int genre) {

		List<Map<String, Object>> quizList = new ArrayList<>();

		quizList = quizDAO.getQuiz(genre);

		return quizList;
	}

	// 퀴즈시작
	public int startQuiz(int genre) {

		// 퀴즈 시작시 아이템을 사용
		ItemService.getInstance().useItem();

		// 내가 맞춘 정답의 개수, 게임 시작시 0으로 초기화 되면서 정답을 맞출수록 1씩 증가
		gameManager.resetCorrectCount();

		// 목숨은 일단 게임 시작시 0으로 초기화하고
		int life;

		// useLife = boolean타입 인데 useItem()메소드에서 목숨+2 아이템을 사용하기로 했다면 목숨이 4개로, 아니면 2개로
		// 값을줌
		if (gameManager.hasItem(View.ITEM_LIFE)) {
			life = 4;
		} else {
			life = 2;
		}

		// quizList라는 리스트를 하나 만들고
		List<Map<String, Object>> quizList = new ArrayList<>();
		// quizList에 getQuiz메소드로 장르를 전달해주고 퀴즈 10개를 받아옴
		quizList = getQuiz(genre);

		for (int i = 0; i < 10; i++) {

			// 2번 틀리면 게임오버
			if (life <= 0) {
				return View.QUIZ_FAIL;
			}

			PrintUtil.bar();
			PrintUtil.centerAlignment("Q" + (i + 1));
			PrintUtil.bar2();
			PrintUtil.centerAlignment(quizList.get(i).get("QUIZ_DETAIL").toString());
			if (gameManager.hasItem(View.ITEM_HINT)) {
				PrintUtil.bar3();
				PrintUtil.centerAlignment("초성힌트 : " + quizList.get(i).get("QUIZ_HINT"));
			}
			PrintUtil.bar();
			System.out.print("\n 【  정답입력 】 ");
			String answer = ScanUtil.nextLine();

			if (answer.equals(quizList.get(i).get("QUIZ_ANSWER"))) {
				PrintUtil.bar3();
				PrintUtil.centerAlignment("정답입니다!");
				PrintUtil.bar3();
				gameManager.setCorrectCount();
			} else {
				PrintUtil.bar3();
				PrintUtil.centerAlignment("오답입니다!");
				life--;
				PrintUtil.centerAlignment("남은목숨 : " + life);
				PrintUtil.bar3();
			}
		}

		return View.QUIZ_SUCCESS;
	}

	// 실패시 실행되는 메소드
	public int failMenu() {
		PrintUtil.bar();
		PrintUtil.centerAlignment("퀴즈 풀기에 실패하셨습니다!");
		PrintUtil.bar2();
		if (gameManager.hasItem(View.ITEM_DOUBLE)) {
			userService.setUserScore(gameManager.getCorrectCount() * 2);
		} else {
			userService.setUserScore(gameManager.getCorrectCount());
		}
		userService.setUserGameMoney(gameManager.getCorrectCount() * 10);
		Object score = userService.getUserInfo().get("USER_SCORE");
		PrintUtil.centerAlignment("맞춘문제   " + gameManager.getCorrectCount() + " / 10" + "       내 점수 : " + score);
		PrintUtil.bar2();
		PrintUtil.centerAlignment("① 퀴즈풀기   ② 메인메뉴   ");
		PrintUtil.bar();
		System.out.print("\n 【  선택 】 ");

		gameManager.resetItem();

		try {
			switch (ScanUtil.nextInt()) {
			case 1:
				return View.QUIZ_START;
			case 2:
				return View.HOME_MAIN;
			default:
				return View.HOME_MAIN;
			}
		} catch (NumberFormatException e) {

			return View.HOME_MAIN;
			
		}
	}

	// 성공시 실행되는 메소드
	public int success() {
		PrintUtil.bar();
		PrintUtil.centerAlignment("퀴즈 풀기에 성공하셨습니다!");
		PrintUtil.bar2();
		if (gameManager.hasItem(View.ITEM_DOUBLE)) {
			userService.setUserScore(gameManager.getCorrectCount() * 2);
		} else {
			userService.setUserScore(gameManager.getCorrectCount());
		}
		userService.setUserGameMoney(gameManager.getCorrectCount() * 10);
		Object score = userService.getUserInfo().get("USER_SCORE");
		PrintUtil.centerAlignment("맞춘문제   " + gameManager.getCorrectCount() + " / 10" + "       내 점수 : " + score);
		PrintUtil.bar2();
		PrintUtil.centerAlignment("① 퀴즈풀기   ② 메인메뉴   ");
		PrintUtil.bar();
		System.out.print("\n 【  선택 】 ");

		gameManager.resetItem();

		try {
			switch (ScanUtil.nextInt()) {
			case 1:
				return View.QUIZ_START;
			case 2:
				return View.HOME_MAIN;
			default:
				return View.HOME_MAIN;
			}
		} catch (NumberFormatException e) {
			return View.HOME_MAIN;
		}
	}

	// 선택한 장르의 문제 보여주기
	public void searchQuiz(int genre) {

		List<Map<String, Object>> quizList = quizDAO.searchQuiz(genre);

		switch (genre) {
		case View.QUIZ_COMMON_SENSE:
			PrintUtil.centerAlignment("상식 퀴즈 목록");
			break;
		case View.QUIZ_KOREAN:
			PrintUtil.centerAlignment("우리말 퀴즈 목록");
			break;
		case View.QUIZ_HISTORY:
			PrintUtil.centerAlignment("역사 퀴즈 목록");
			break;
		case View.QUIZ_NONSENSE:
			PrintUtil.centerAlignment("넌센스 퀴즈 목록");
			break;
		}

		quizList = quizDAO.searchQuiz(genre);

		for (Map<String, Object> quiz : quizList) {
			int quizId = Integer.parseInt(quiz.get("QUIZ_NO").toString());
			String question = (String) quiz.get("QUIZ_DETAIL");
			String answer = (String) quiz.get("QUIZ_ANSWER");
			PrintUtil.centerAlignment("문제 ID : " + quizId);
			PrintUtil.centerAlignment("질문 : " + question);
			PrintUtil.centerAlignment("정답 : " + answer);
			System.out.println();
		}
	}
}
