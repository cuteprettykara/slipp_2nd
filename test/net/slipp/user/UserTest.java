package net.slipp.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

	public static User TEST_USER = new User("prettykara", "1111", "�����", "cuteprettykara@gmail.com");
	private UserDAO userDao;

	@Before
	public void setup() throws SQLException {
		userDao = new UserDAO();
		userDao.removeUser(TEST_USER.getUserId());
	}
	
	@Test
	public void matchPassword() {
		String password = "1111";
		
		assertTrue(TEST_USER.matchPassword(password));
	}

	
	@Test
	public void notMatchPassword() {
		String password = "2222";
		
		assertFalse(TEST_USER.matchPassword(password));
	}
	
	@Test
	public void loginSuccess() throws Exception {
		User user = TEST_USER;
		
		userDao.addUser(TEST_USER);
	
		assertTrue(User.login(user.getUserId(), user.getPassword()));
	}
	
	@Test(expected=UserNotFoundException.class)
	public void loginWhenUserNotFound() throws Exception {
		User.login("userId", TEST_USER.getPassword());
	}

	@Test(expected=PasswordMismatchException.class)
	public void loginWhenPasswordMismatch() throws Exception {
		User user = TEST_USER;
		
		userDao.addUser(user);
			
		User.login(user.getUserId(), "2222");
	}
}
