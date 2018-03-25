package net.slipp.user;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UserDAOTest {

	private UserDAO userDao;

	@Before
	public void setup() {
		userDao = new UserDAO();
	}

	@Test
	public void connection() {
		assertNotNull(userDao.getConnection());
	}

	
	@Test
	public void crud() throws Exception {
		User user = UserTest.TEST_USER;
		userDao.removeUser(user.getUserId());
		userDao.addUser(user);		
		
		User dbuser = userDao.findByUserId(user.getUserId());
		assertEquals(user, dbuser);
		
		
		User updateUser = new User(user.getUserId(), "uPassword", "uname", "email@ggg");
		userDao.updateUser(updateUser);
		
		dbuser = userDao.findByUserId(updateUser.getUserId());
		assertEquals(updateUser, dbuser);
	}
	
	@Test
	public void 존재하지_않는_사용자_조회() throws Exception {
		User user = UserTest.TEST_USER;
		userDao.removeUser(user.getUserId());
		
		User dbuser = userDao.findByUserId(user.getUserId());
		assertNull(dbuser);
	}
}
