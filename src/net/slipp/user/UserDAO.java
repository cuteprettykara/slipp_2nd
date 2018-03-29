package net.slipp.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.slipp.support.JdbcTemplate;
import net.slipp.support.RowMapper;

public class UserDAO {

	public void addUser(User user) throws SQLException {
		String sql = "insert into users values(?,?,?,?)";
		JdbcTemplate.executeUpdate(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
	}

	public User findByUserId(String userId) throws SQLException {
		
		RowMapper<User> rm = new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs) throws SQLException {
				String id = rs.getString("userId");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String email = rs.getString("email");
				
				return new User(id, password, name, email);
			}
		};
		
		String sql = "select * from users where userId=?";
		return JdbcTemplate.executeQuery(sql, rm, userId);
	}

	public void removeUser(String userId) throws SQLException {
		String sql = "delete from users where userId=?";
		JdbcTemplate.executeUpdate(sql, userId);
	}

	public void updateUser(User user) throws SQLException {
		String sql = null;
		sql =       "update users";
		sql = sql + "   set password = ?, name = ?, email = ? ";
		sql = sql + " where userId = ?  ";

		JdbcTemplate.executeUpdate(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
	}

	public List<User> findUsers() throws SQLException {
		String sql = "select * from users";
		
		RowMapper<User> rm = new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs) throws SQLException {
				String id = rs.getString("userId");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String email = rs.getString("email");
				
				return new User(id, password, name, email);
			}
		};
		
		return JdbcTemplate.list(sql, rm);
	}
}
