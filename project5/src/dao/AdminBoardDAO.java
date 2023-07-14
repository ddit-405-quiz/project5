package dao;

import util.JDBCUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminBoardDAO {
    private static AdminBoardDAO instance = null;
    private AdminBoardDAO() {}
    public static AdminBoardDAO getInstance() {
        if (instance == null) instance = new AdminBoardDAO();
        return instance;
    }

    JDBCUtil jdbc = JDBCUtil.getInstance();

    public List<Map<String, Object>> boardList(){
        String sql = "SELECT * FROM REQUEST ORDER BY REQ_NO ASC";
        return jdbc.selectList(sql);
    }

    public Map<String, Object> selectBoard(int reqNo) {
        String sql = "SELECT REQ_NO, REQ_TITLE, REQ_DETAIL, REQ_WRITER, USER_NO FROM REQUEST WHERE REQ_NO = ?";
        List<Object> params = new ArrayList<>();
        params.add(reqNo);
        List<Map<String, Object>> result = jdbc.selectList(sql, params);
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }
    }
    
    public int updateBoard(Map<String, Object> board) {
        String sql = "UPDATE REQUEST SET REQ_TITLE = ?, REQ_DETAIL = ?, REQ_WRITER = ? WHERE REQ_NO = ?";
        List<Object> params = new ArrayList<>();
        params.add(board.get("REQ_TITLE"));
        params.add(board.get("REQ_DETAIL"));
        params.add(board.get("REQ_WRITER"));
        params.add(board.get("REQ_NO"));
        return jdbc.update(sql, params);
    }

    public int deleteBoard(int reqNo) {
        String sql = "DELETE FROM REQUEST WHERE REQ_NO = ?";
        List<Object> params = new ArrayList<>();
        params.add(reqNo);
        return jdbc.update(sql, params);
    }
    
    public int createBoard(Map<String, Object> board) {
	    String sql = "INSERT INTO REQUEST (REQ_NO, REQ_TITLE, REQ_DETAIL, USER_NO, REQ_WRITER) "
	            + "VALUES (fn_create_no, ?, ?, ?, ?)";
	    List<Object> params = new ArrayList<>();
	    params.add(board.get("REQ_TITLE"));
	    params.add(board.get("REQ_DETAIL"));
	    params.add(board.get("USER_NO"));
	    params.add(board.get("REQ_WRITER"));
	    return jdbc.update(sql, params);
	}
}