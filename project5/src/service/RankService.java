package service;

import java.util.List;
import java.util.Map;

import dao.RankDAO;
import util.PrintUtil;
import util.ScanUtil;
import util.View;


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
	
	
	public int rankingPage() {
		
		List<Map<String, Object>> rankList = rankDAO.getRank();
		
		PrintUtil.bar();
		System.out.println();
		PrintUtil.centerAlignment("\tR  A  N  K  I  N  G");
		PrintUtil.bar2();
		PrintUtil.centerAlignment("\t\t    순위                           유저                         총점");
		System.out.println();
		
		for (Map<String, Object> ranking : rankList) {
			int rank = Integer.parseInt(ranking.get("RANK").toString());
			String ranker = (String) ranking.get("USER_NAME");
			int score = Integer.parseInt(ranking.get("USER_SCORE").toString());
			System.out.println("\t\t  " + rank + "\t\t" + ranker + "\t     " + score);
		}
		
		System.out.println();
		PrintUtil.bar();
		PrintUtil.centerAlignment("이전 화면으로 이동하려면 Enter 키를 입력하세요.");
		ScanUtil.nextLine();
		return View.RANKING;
	}
}
