package core;

import static org.junit.Assert.*;

import org.junit.Test;

import core.jdbc.ConnectionManager;

public class ConnectionManagerTest {

	@Test
	public void connection() {
		assertNotNull(ConnectionManager.getConnection());
	}

}
