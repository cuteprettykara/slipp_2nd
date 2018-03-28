package net.slipp.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class JdbcTemplate {
	
	public abstract void setParameters(PreparedStatement pstmt) throws SQLException;
	
	public void executeUpdate(String sql) throws SQLException {
		Connection con = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			setParameters(pstmt);
			
			pstmt.executeUpdate();
			
		} finally {
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();
		}
	}

}
