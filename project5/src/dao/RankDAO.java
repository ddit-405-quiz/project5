package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class RankDAO {
	private static RankDAO instance = null;

	private RankDAO() {
	}

	public static RankDAO getInstance() {
		if (instance == null) {
			instance = new RankDAO();
		}
		return instance;
	}

	JDBCUtil jdbc = JDBCUtil.getInstance();

	public List<Map<String, Object>> getRank() {

		List<Map<String, Object>> rankList = new ArrayList<>();

		String sql = "select rank() over (order by user_score desc, user_no asc) rank, user_score, user_name" + 
					" from ranking";

		rankList = jdbc.selectAll(sql);

		return rankList;
	}

}
