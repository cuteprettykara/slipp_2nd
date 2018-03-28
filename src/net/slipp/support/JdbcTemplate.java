package net.slipp.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {
	
	public static void executeUpdate(String sql, PrepareStatementSetter pss) throws SQLException {
		Connection con = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pss.setParameters(pstmt);
			
			pstmt.executeUpdate();
			
		} finally {
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();
		}
	}
	
	public static Object executeQuery(String sql, PrepareStatementSetter pss, RowMapper rm) throws SQLException {
		Connection con = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pss.setParameters(pstmt);
			
			rs = pstmt.executeQuery();
			if (!rs.next()) return null;
			
			return rm.mapRow(rs);
			
		}  finally {
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();
		}
	}
}
