package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class RankDAO {
	private static RankDAO instance = null;
	private RankDAO () {}
	public static RankDAO getInstance() {
		if(instance == null) {
			instance = new RankDAO();
		}
		return instance;
	}
	
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> getRank() {

		List<Map<String, Object>> rankList = new ArrayList<>();

		for (int i =0; i <= 10; i++) {

			String sql = "SELECT rank, user_no, user_score " +
						 "from ranking ORDER BY rank";

			rankList.add(jdbc.selectOne(sql));
		}

		return rankList;
	}

}
