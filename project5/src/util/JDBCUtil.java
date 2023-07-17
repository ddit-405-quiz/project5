package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.AdminDAO;

public class JDBCUtil {

	private static JDBCUtil instance = null;
	private JDBCUtil() {}
	public static JDBCUtil getInstance() {	
		if(instance == null) {
			instance = new JDBCUtil();
		}
		return instance;
	}

	private final String url = "jdbc:oracle:thin:@192.168.145.29:1521:xe";
	private final String user = "project5";
	private final String password = "java";

	private Connection conn = null;
	private ResultSet rs = null;
	private PreparedStatement pstmt = null;
	private Statement stmt = null;

	public Map<String, Object> selectOne(String sql, List<Object> param) {
		Map<String, Object> row = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < param.size(); i++) {
				pstmt.setObject(i + 1, param.get(i));
			}
			rs = pstmt.executeQuery(); 
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				row = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					String key = rsmd.getColumnLabel(i);
					Object value = rs.getObject(i);
					row.put(key, value);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try {rs.close();} catch (Exception e) {}
			if (pstmt != null) try {pstmt.close();} catch (Exception e) {}
			if (conn != null) try {conn.close();} catch (Exception e) {}
		}

		return row;
	}

	public Map<String, Object> selectOne(String sql) {
		Map<String, Object> row = null;
		
		try {
			
			conn = DriverManager.getConnection(url, user, password);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int columnCount = rsmd.getColumnCount();
			
			while (rs.next()) {
				if (row == null)
					row = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					String key = rsmd.getColumnLabel(i);
					Object value = rs.getObject(i);
					row.put(key, value);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try {rs.close();} catch (Exception e) {}
			if (pstmt != null) try {pstmt.close();} catch (Exception e) {}
			if (conn != null) try {conn.close();} catch (Exception e) {}
		}
		return row;
	}

	public List<Map<String, Object>> selectAll (String sql) {

		List<Map<String, Object>> list = new ArrayList<>();

		try {
			
			conn = DriverManager.getConnection(url, user, password);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int columnCount = rsmd.getColumnCount();
			
			while (rs.next()) {
				
				Map<String, Object> row = null;
				
				if (row == null)
					row = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					String key = rsmd.getColumnLabel(i);
					Object value = rs.getObject(i);
					row.put(key, value);
				}
				
				list.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try {rs.close();} catch (Exception e) {}
			if (pstmt != null) try {pstmt.close();} catch (Exception e) {}
			if (conn != null) try {conn.close();} catch (Exception e) {}
		}
		return list;
	}
	
	public List<Map<String, Object>> selectAll(String sql, List<Object> param) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object>	row = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < param.size(); i++) {
				pstmt.setObject(i + 1, param.get(i));
			}
			rs = pstmt.executeQuery(); 
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				row = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					String key = rsmd.getColumnLabel(i);
					Object value = rs.getObject(i);
					row.put(key, value);
				}
				
				list.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try {rs.close();} catch (Exception e) {}
			if (pstmt != null) try {pstmt.close();} catch (Exception e) {}
			if (conn != null) try {conn.close();} catch (Exception e) {}
		}

		return list;
	}

	public int update(String sql) {
		int result = 0;
		try {
			conn = DriverManager.getConnection(url, user, password);
			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try {rs.close();} catch (Exception e) {}
			if (pstmt != null) try {pstmt.close();} catch (Exception e) {}
			if (conn != null) try {conn.close();} catch (Exception e) {}
		}
		return result;
	}
	
	public int update(String sql, List<Object> param) {
		int result = 0;
		try {
			conn = DriverManager.getConnection(url, user, password);
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < param.size(); i++) {
				pstmt.setObject(i + 1, param.get(i));
			}
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) try {rs.close();} catch (Exception e) {}
			if (pstmt != null) try {pstmt.close();} catch (Exception e) {}
			if (conn != null) try {conn.close();} catch (Exception e) {}
		}
		return result;
	}
}