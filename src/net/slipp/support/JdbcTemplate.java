package net.slipp.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcTemplate {
	
	private static final Logger logger = LoggerFactory.getLogger(JdbcTemplate.class);

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
	
	public static void executeUpdate(String sql, Object... parameters) throws SQLException {
		executeUpdate(sql, createPreparedStatementSetter(parameters));
	}
	
	public static <T> T executeQuery(String sql, RowMapper<T> rm, PrepareStatementSetter pss) throws SQLException {
		List<T> list = list(sql, rm, pss);
		if (list.isEmpty()) return null;
		
		return list.get(0);
	}
	
	public static <T> T executeQuery(String sql, RowMapper<T> rm, Object... parameters) throws SQLException {
		return executeQuery(sql, rm, createPreparedStatementSetter(parameters));
	}

	private static PrepareStatementSetter createPreparedStatementSetter(Object... parameters) {
		return  new PrepareStatementSetter() {
			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				for (int i = 0; i < parameters.length; i++) {
					pstmt.setObject(i+1, parameters[i]);
				}
				
			}
		};
	}
	
	public static <T> List<T> list(String sql, RowMapper<T> rm, PrepareStatementSetter pss) throws SQLException {
		Connection con = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pss.setParameters(pstmt);
			
			rs = pstmt.executeQuery();
			
			List<T> list = new ArrayList<T>();
			
			while (rs.next()) {
				list.add(rm.mapRow(rs));
			}
			logger.debug("list.size() : " + list.size());
			return list;
			
		}  finally {
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();
		}
	}
	
	public static <T> List<T> list(String sql, RowMapper<T> rm, Object... parameters) throws SQLException {
		return list(sql, rm, createPreparedStatementSetter(parameters));
	}
}
