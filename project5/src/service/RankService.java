package service;

import java.util.List;
import java.util.Map;

import dao.RankDAO;
import util.PrintUtil;


public class RankService {
	private static RankService instance = null;
	private RankService () {}
	public static RankService getInstance() {
		if(instance == null) {
			instance = new RankService();
		}
		return instance;
	}
	
	RankDAO rankDAO = RankDAO.getInstance();
	
	
	public void rankingPage() {
		
		List<Map<String, Object>> quizList = rankDAO.getRank();
		
		PrintUtil.bar();
		PrintUtil.centerAlignment("R  A  N  K  I  N  G");
		PrintUtil.bar2();
		PrintUtil.centerAlignment("      순위          유저          점수      ");
		
		for (Map<String, Object> quiz : quizList) {
			int rank = Integer.parseInt(quiz.get("RANK").toString());
			String ranker = (String) quiz.get("USER_NO");
			int score = Integer.parseInt(quiz.get("USER_SCORE").toString());
			System.out.print("\t" + rank + "\t");
			System.out.print(ranker+ "\t");
			System.out.println(score+ "\t");
			System.out.println();
		}
	}
}
