package net.slipp.support.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PrepareStatementSetter {
	void setParameters(PreparedStatement pstmt) throws SQLException;
}
