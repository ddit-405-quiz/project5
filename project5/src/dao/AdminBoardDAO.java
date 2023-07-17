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
        String sql = "SELECT * FROM REQUEST ORDER BY TO_NUMBER(REQ_NO) ASC";
        return jdbc.selectAll(sql);
    }

    public Map<String, Object> selectBoard(int reqNo) {
        String sql = "SELECT * FROM REQUEST WHERE REQ_NO = ?";
        List<Object> params = new ArrayList<>();
        params.add(reqNo);
        List<Map<String, Object>> result = jdbc.selectAll(sql, params);
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
	               + " VALUES (fn_create_no, '" + board.get("REQ_TITLE") + "', '" + board.get("REQ_DETAIL") + "', '관리자', '" + board.get("REQ_WRITER") + "')";
	    return jdbc.update(sql);
	}
    
    public int createComment(int reqNo, String comment) {
        String sql = "UPDATE REQUEST SET REQ_RESP= '" + comment + "' WHERE REQ_NO=" + reqNo;
        return jdbc.update(sql);
    }
    
    public int updateAdminBoard(Map<String, Object> board) {
        String sql = "UPDATE REQUEST SET REQ_TITLE = ?, REQ_DETAIL = ?, REQ_WRITER = ?, REQ_RESP = ? WHERE REQ_NO = ?";
        List<Object> params = new ArrayList<>();
        params.add(board.get("REQ_TITLE"));
        params.add(board.get("REQ_DETAIL"));
        params.add(board.get("REQ_WRITER"));
        params.add(board.get("REQ_RESP"));
        params.add(board.get("REQ_NO"));
        return jdbc.update(sql, params);
    }
    
    public List<Map<String, Object>> getComments(int reqNo) {
        String sql = "SELECT * FROM REQUEST WHERE REQ_NO = " + reqNo;
        return jdbc.selectAll(sql);
    }
}