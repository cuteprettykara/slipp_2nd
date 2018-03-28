package net.slipp.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SelectJdbcTemplate {
	public abstract void setParameters(PreparedStatement pstmt) throws SQLException;
	public abstract Object mapRow(ResultSet rs) throws SQLException;
	
	public Object executeQuery(String sql) throws SQLException {
		Connection con = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			setParameters(pstmt);
			
			rs = pstmt.executeQuery();
			return mapRow(rs);
			
		}  finally {
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();
		}
		
	}
}
