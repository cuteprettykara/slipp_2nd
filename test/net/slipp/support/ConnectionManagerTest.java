package net.slipp.support;

import static org.junit.Assert.*;

import org.junit.Test;

import net.slipp.support.jdbc.ConnectionManager;

public class ConnectionManagerTest {

	@Test
	public void connection() {
		assertNotNull(ConnectionManager.getConnection());
	}

}
