package net.slipp.support;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConnectionManagerTest {

	@Test
	public void connection() {
		assertNotNull(ConnectionManager.getConnection());
	}

}
