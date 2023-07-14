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

		String sql = "SELECT rank, user_score, user_name"
				   + " FROM ("
				   + " SELECT RANK() OVER (ORDER BY user_score DESC, user_no ASC) AS rank, user_score, user_name"
				   + " FROM ranking ORDER BY rank )"
				   + " WHERE ROWNUM <= 10";

		rankList = jdbc.selectAll(sql);

		return rankList;
	}

}
