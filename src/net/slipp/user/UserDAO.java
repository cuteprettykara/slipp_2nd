package net.slipp.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.slipp.support.JdbcTemplate;
import net.slipp.support.SelectJdbcTemplate;

public class UserDAO {

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
		SelectJdbcTemplate selectJdbcTemplate = new SelectJdbcTemplate() {
			
			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, userId);
			}
			
			@Override
			public Object mapRow(ResultSet rs) throws SQLException {
				if (!rs.next()) return null;
				
				String id = rs.getString("userId");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String email = rs.getString("email");
				
				return new User(id, password, name, email);
			}			
		};
		
		String sql = "select * from users where userId=?";
		return (User) selectJdbcTemplate.executeQuery(sql);
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
