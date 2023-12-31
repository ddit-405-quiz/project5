package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import service.BoardService;
import util.JDBCUtil;

public class BoardDAO {
	private static BoardDAO instance = null;

	private BoardDAO() {
	}

	public static BoardDAO getInstance() {
		if (instance == null)
			instance = new BoardDAO();
		return instance;
	}

	JDBCUtil jdbc = JDBCUtil.getInstance();

	public List<Map<String, Object>> boardList() {
		String sql = "SELECT * FROM REQUEST " + "ORDER BY REQ_NO ASC";
		return jdbc.selectAll(sql);
	}

	public Map<String, Object> selectBoard(int reqNo) {
		String sql = "SELECT REQ_NO, REQ_TITLE, REQ_DETAIL, REQ_WRITER, USER_NO FROM REQUEST WHERE REQ_NO = ?";
		List<Object> params = new ArrayList<>();
		params.add(reqNo);
		List<Map<String, Object>> result = jdbc.selectAll(sql, params);
		if (result != null && result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}

	// 게시물 수정
	public int updateBoard(Map<String, Object> board) {
		String sql = "UPDATE REQUEST SET REQ_TITLE=?, REQ_DETAIL=?, REQ_WRITER=? WHERE REQ_NO=?";
		List<Object> params = new ArrayList<>();
		params.add(board.get("REQ_TITLE"));
		params.add(board.get("REQ_DETAIL"));
		params.add(board.get("REQ_WRITER"));
		params.add(board.get("REQ_NO"));
		return jdbc.update(sql, params);
	}

	// 게시물 삭제
	public int deleteBoard(int reqNo) {
		String sql = "DELETE FROM REQUEST WHERE REQ_NO=?";
		List<Object> params = new ArrayList<>();
		params.add(reqNo);
		return jdbc.update(sql, params);
	}

	// 게시글 생성
	public int createBoard(Map<String, Object> board) {
		String sql = "INSERT INTO REQUEST (REQ_NO, REQ_TITLE, REQ_DETAIL, USER_NO, REQ_WRITER) "
				+ "VALUES (fn_create_req_no, ?, ?, ?, ?)";
		List<Object> params = new ArrayList<>();
		params.add(board.get("REQ_TITLE"));
		params.add(board.get("REQ_DETAIL"));
		params.add(board.get("USER_NO"));
		params.add(board.get("REQ_WRITER"));
		return jdbc.update(sql, params);
	}

	public List<Map<String, Object>> getBoardListByPage(int start, int end) {
		String sql = "SELECT * FROM (SELECT ROWNUM AS rnum, A.* FROM (SELECT * FROM REQUEST ORDER BY TO_NUMBER(REQ_NO) ASC) A WHERE ROWNUM <= ?) WHERE rnum >= ?";
		List<Object> params = new ArrayList<>();
		params.add(end);
		params.add(start);
		return jdbc.selectAll(sql, params);
	}

	// 게시판의 레코드 수 반환
	public int getTotalBoardCount() {
		String sql = "SELECT COUNT(*) FROM REQUEST";
		Map<String, Object> result = jdbc.selectOne(sql);
		if (result != null && result.containsKey("COUNT(*)")) {
			return Integer.parseInt(result.get("COUNT(*)").toString());
		} else {
			return 0;
		}
	}

	// 관리자 댓글 생성
	public int createReply(Map<String, Object> reply) {
		String sql = "INSERT INTO REPLY (REQ_NO, REQ_RESP) " + "VALUES (?, ?)";
		List<Object> params = new ArrayList<>();
		params.add(reply.get("REQ_NO"));
		params.add(reply.get("REQ_RESP"));
		return jdbc.update(sql, params);
	}
}