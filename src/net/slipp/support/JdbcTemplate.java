package net.slipp.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class JdbcTemplate {
	
	public abstract void setParameters(PreparedStatement pstmt) throws SQLException;
	
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/slipp_dev","scott", "tiger");
			
		} catch (Exception e) {
			return null;
		}
	}
	
	public void executeUpdate(String sql) throws SQLException {
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			setParameters(pstmt);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			
		} finally {
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();
		}
	}

}
