package net.slipp.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;

public class UserDAO {

	public void addUser(User user) {
		String sql = "insert into users values(?,?,?,?)";
		JdbcTemplate.executeUpdate(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
	}

	public User findByUserId(String userId) {
		
		RowMapper<User> rm = rs->
			new User(
				rs.getString("userId"), 
				rs.getString("password"), 
				rs.getString("name"), 
				rs.getString("email")
			);
		
		
		String sql = "select * from users where userId=?";
		return JdbcTemplate.executeQuery(sql, rm, userId);
	}

	public void removeUser(String userId) {
		String sql = "delete from users where userId=?";
		JdbcTemplate.executeUpdate(sql, userId);
	}

	public void updateUser(User user) {
		String sql = null;
		sql =       "update users";
		sql = sql + "   set password = ?, name = ?, email = ? ";
		sql = sql + " where userId = ?  ";

		JdbcTemplate.executeUpdate(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
	}

	public List<User> findUsers() throws SQLException {
		String sql = "select * from users";
		
		RowMapper<User> rm = rs->
			new User(
				rs.getString("userId"), 
				rs.getString("password"), 
				rs.getString("name"), 
				rs.getString("email")
			);
		
		
		return JdbcTemplate.list(sql, rm);
	}
}
