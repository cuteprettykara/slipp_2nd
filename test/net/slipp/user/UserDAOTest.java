package net.slipp.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class UserDAOTest {

	private UserDAO userDao;

	@Before
	public void setup() {
		userDao = new UserDAO();
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
	
	@Test
	public void findUsers() throws Exception {
		List<User> users = userDao.findUsers();
		assertTrue(users.size() > 1);
	}
}
