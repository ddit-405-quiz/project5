package service;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.QuizListDAO;
import util.PrintUtil;
import util.ScanUtil;
import util.View;

public class QuizListService {

	private static QuizListService instance = null;

	private QuizListService() {
	}

	public static QuizListService getInstance() {
		if (instance == null)
			instance = new QuizListService();
		return instance;
	}

	QuizListDAO quizListDAO = QuizListDAO.getInstance();
	int linesPerPage = 5;

	// 선택한 장르의 문제를 얻어옴
	public List<Map<String, Object>> getQuizList(int genre) {
		
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
		String sql = "SELECT * FROM QUIZ" + " WHERE SUBSTR(QUIZ_NO, 1, 1) = " + quizCategory + " ORDER BY QUIZ_NO";

		return quizListDAO.getQuizList(sql);
	}

	// 퀴즈 리스트를 매개변수로 받고 그 퀴즈를 나눠서 linesPerPage만큼 쪼개고 List에 담음
	public List<List<Map<String, Object>>> paginateData(List<Map<String, Object>> quizList) {
		List<List<Map<String, Object>>> quizPages = new ArrayList<>();

		int totalLines = quizList.size();
		int totalPages = (int) Math.ceil((double) totalLines / linesPerPage);

		for (int i = 0; i < totalPages; i++) {
			int startIndex = i * linesPerPage;
			int endIndex = Math.min(startIndex + linesPerPage, totalLines);
			List<Map<String, Object>> pageData = quizList.subList(startIndex, endIndex);
			quizPages.add(pageData);
		}

		return quizPages;
	}

	// 쪼개진 페이지들을 출력
	public void printPage(List<Map<String, Object>> pageData, int currentPage, int totalPage) {

		PrintUtil.bar();
		PrintUtil.bar2();
		PrintUtil.centerAlignment("현재 페이지 : " + (currentPage + 1));
		PrintUtil.centerAlignment("전체 페이지 : " + totalPage);

		for (int i = 0; i < pageData.size(); i++) {
			System.out.println("번호 : " + pageData.get(i).get("QUIZ_NO"));
			System.out.println("내용 : " + pageData.get(i).get("QUIZ_DETAIL"));
			System.out.println("정답 : " + pageData.get(i).get("QUIZ_ANSWER"));
			System.out.println();
		}

		PrintUtil.bar2();
		PrintUtil.bar();
	}

	// 퀴즈 추가
	public void addQuiz() {
		try {
			int result = 0;
			int genre = 0;

			PrintUtil.bar2();
			System.out.println("<< 문 제 추 가 >>");

			System.out.println("문제 유형: ");
			System.out.println("1.일반상식  2.우리말   3.역사   4.넌센스");

			try {
				genre = ScanUtil.nextInt();

			} catch (NumberFormatException e) {
				PrintUtil.bar3();
				PrintUtil.centerAlignment("올바른 숫자를 입력하세요");
				PrintUtil.bar3();
				addQuiz();
				return;
			}

			if (!(genre >= 1 && genre <= 4)) {
				PrintUtil.bar3();
				PrintUtil.centerAlignment("올바른 숫자를 입력하세요");
				PrintUtil.bar3();
				addQuiz();
				return;
			}

			int quizNo = searchEmptyNum(genre);
			
			System.out.print("문제 내용: ");
			String quizDetail = ScanUtil.nextLine();

			System.out.print("문제 정답: ");
			String quizAnswer = ScanUtil.nextLine();

			System.out.print("문제 힌트: ");
			String quizHint = ScanUtil.nextLine();
			PrintUtil.bar2();

			PrintUtil.bar3();
			PrintUtil.centerAlignment("문제를 추가하겠습니까?  (y / n)");
			PrintUtil.bar3();
			System.out.print("\n 【  선택  】 ");
			String flag = ScanUtil.nextLine();
			if (flag.equalsIgnoreCase("y")) {
				String sql = "INSERT INTO QUIZ" + " VALUES(" + quizNo + ", '" + quizHint + "', '"
						+ quizDetail + "', '" + quizAnswer + "')";
				result = quizListDAO.updateQuiz(sql);
			}
			if (result != 0) {
				PrintUtil.bar3();
				PrintUtil.centerAlignment("문제 추가가 정상 처리되었습니다.");
				PrintUtil.centerAlignment("다음 화면으로 이동하려면 Enter 키를 입력하세요.");
				PrintUtil.bar3();
				ScanUtil.nextLine();
			} else {
				PrintUtil.bar3();
				PrintUtil.centerAlignment("문제 추가가 취소되었습니다.");
				PrintUtil.centerAlignment("다음 화면으로 이동하려면 Enter 키를 입력하세요.");
				PrintUtil.bar3();
				ScanUtil.nextLine();
			}
		} catch (SQLSyntaxErrorException e) {
			PrintUtil.bar3();
			PrintUtil.centerAlignment("올바른 입력값을 입력하세요");
			PrintUtil.bar3();
			addQuiz();
			return;
		} catch (Exception e) {
			PrintUtil.bar3();
			PrintUtil.centerAlignment("올바른 입력값을 입력하세요");
			PrintUtil.bar3();
			addQuiz();
			return;
		}
	}

	// 퀴즈 삭제
	public void deleteQuiz() {
		try {
			int result = 0;

			PrintUtil.bar2();
			System.out.println("<< 문 제 삭 제 >>");

			System.out.print("삭제하고싶은 문제 번호를 입력하세요: ");
			String quizNo = ScanUtil.nextLine();

			if (quizNo == null || quizNo.trim().isEmpty()) {
				PrintUtil.bar3();
				PrintUtil.centerAlignment("문제 번호를 입력하세요.");
				PrintUtil.bar3();
				return;
			}

			PrintUtil.bar3();
			PrintUtil.centerAlignment(quizNo + "번 문제를 삭제하겠습니까?  (y / n)");
			PrintUtil.bar3();
			System.out.print("\n 【  선택  】 ");
			String flag = ScanUtil.nextLine();
			if (flag.equalsIgnoreCase("y")) {
				String sql = "DELETE FROM QUIZ" + " WHERE QUIZ_NO = " + quizNo;
				result = quizListDAO.updateQuiz(sql);
			}
			if (result != 0) {
				PrintUtil.bar3();
				PrintUtil.centerAlignment("문제 삭제가 정상 처리되었습니다.");
				PrintUtil.centerAlignment("다음 화면으로 이동하려면 Enter 키를 입력하세요.");
				PrintUtil.bar3();
				ScanUtil.nextLine();
			} else {
				PrintUtil.bar3();
				PrintUtil.centerAlignment("문제 삭제가 취소되었습니다.");
				PrintUtil.centerAlignment("다음 화면으로 이동하려면 Enter 키를 입력하세요.");
				PrintUtil.bar3();
				ScanUtil.nextLine();
			}
		} catch (SQLSyntaxErrorException e) {
			PrintUtil.bar3();
			PrintUtil.centerAlignment("올바른 입력값을 입력하세요");
			PrintUtil.bar3();
			addQuiz();
			return;
		} catch (Exception e) {
			PrintUtil.bar3();
			PrintUtil.centerAlignment("올바른 입력값을 입력하세요");
			PrintUtil.bar3();
			addQuiz();
			return;
		}
	}

	// 퀴즈 수정
	public void editQuiz() {
		try {
			int result = 0;

			PrintUtil.bar2();
			System.out.println("<< 문 제 수 정 >>");

			System.out.print("수정하고싶은 문제 번호를 입력하세요: ");
			String quizNo = ScanUtil.nextLine();

			if (quizNo == null || quizNo.trim().isEmpty()) {
				PrintUtil.bar3();
				PrintUtil.centerAlignment("문제 번호를 입력하세요.");
				PrintUtil.bar3();
				return;
			}

			System.out.print("문제 내용: ");
			String quizDetail = ScanUtil.nextLine();

			System.out.print("문제 정답: ");
			String quizAnswer = ScanUtil.nextLine();

			System.out.print("문제 힌트: ");
			String quizHint = ScanUtil.nextLine();
			PrintUtil.bar2();

			PrintUtil.bar3();
			PrintUtil.centerAlignment("문제를 수정하겠습니까?  (y / n)");
			PrintUtil.bar3();
			System.out.print("\n 【  선택  】 ");
			String flag = ScanUtil.nextLine();
			if (flag.equalsIgnoreCase("y")) {
				String sql = "UPDATE QUIZ" + " SET QUIZ_DETAIL = '" + quizDetail + "'," + " QUIZ_ANSWER = '"
						+ quizAnswer + "'," + " QUIZ_HINT  =  '" + quizHint + "'" + " WHERE QUIZ_NO =  " + quizNo + "";
				result = quizListDAO.updateQuiz(sql);
			}
			if (result != 0) {
				PrintUtil.bar3();
				PrintUtil.centerAlignment("문제 수정이 정상 처리되었습니다.");
				PrintUtil.centerAlignment("다음 화면으로 이동하려면 Enter 키를 입력하세요.");
				PrintUtil.bar3();
				ScanUtil.nextLine();
			} else {
				PrintUtil.bar3();
				PrintUtil.centerAlignment("문제 수정이 취소되었습니다.");
				PrintUtil.centerAlignment("다음 화면으로 이동하려면 Enter 키를 입력하세요.");
				PrintUtil.bar3();
				ScanUtil.nextLine();
			}
		} catch (SQLSyntaxErrorException e) {
			PrintUtil.bar3();
			PrintUtil.centerAlignment("올바른 입력값을 입력하세요");
			PrintUtil.bar3();
			addQuiz();
			return;
		} catch (Exception e) {
			PrintUtil.bar3();
			PrintUtil.centerAlignment("올바른 입력값을 입력하세요");
			PrintUtil.bar3();
			addQuiz();
			return;
		}
	}

	// 퀴즈를 추가할때 빈 번호를 찾기 위한 메소드
	public int searchEmptyNum(int genre) {
		
		String sql = "SELECT QUIZ_NO FROM QUIZ" + " WHERE SUBSTR(QUIZ_NO, 1, 1) = " + genre + " ORDER BY QUIZ_NO";
		List<Map<String, Object>> quizList = quizListDAO.getQuizList(sql);
		
	    int index = 0;
	    int emptyNum = -1;

	    while (index < quizList.size()) {
	        int currentNum = Integer.parseInt(quizList.get(index).get("QUIZ_NO").toString());

	        if (currentNum != genre * 1000 + index + 1) {
	            emptyNum = genre * 1000 + index + 1;
	            break;
	        }

	        index++;
	    }

	    if (emptyNum == -1) {
	        emptyNum = genre * 1000 + index + 1;
	    }
	    
	    return emptyNum;
	}
}