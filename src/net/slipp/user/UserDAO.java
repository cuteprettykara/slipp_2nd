package net.slipp.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.slipp.support.JdbcTemplate;

public class UserDAO {

	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/slipp_dev","scott", "tiger");
			
		} catch (Exception e) {
			return null;
		}
	}

	public void addUser(User user) throws SQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate() {
			
			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getUserId());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getName());
				pstmt.setString(4, user.getEmail());
			}
		};
		
		String sql = "insert into users values(?,?,?,?)";
		jdbcTemplate.executeUpdate(sql);
	}

	public User findByUserId(String userId) throws SQLException {
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from users where userId=?";
		User user = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if (!rs.next()) return null;
				
			String id = rs.getString("userId");
			String password = rs.getString("password");
			String name = rs.getString("name");
			String email = rs.getString("email");
			
			user = new User(id, password, name, email);
			
		} catch (Exception e) {
			
		} finally {
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();
		}
		
		return user;
	}

	public void removeUser(String userId) throws SQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate() {

			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, userId);
			}
			
		};
		
		String sql = "delete from users where userId=?";
		jdbcTemplate.executeUpdate(sql);
	}

	public void updateUser(User user) throws SQLException {
		String sql = null;
		sql =       "update users";
		sql = sql + "   set password = ?, name = ?, email = ? ";
		sql = sql + " where userId = ?  ";

		JdbcTemplate jdbcTemplate = new JdbcTemplate() {

			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getPassword());
				pstmt.setString(2, user.getName());
				pstmt.setString(3, user.getEmail());
				pstmt.setString(4, user.getUserId());
			}
			
		};
		
		jdbcTemplate.executeUpdate(sql);
	}
}
