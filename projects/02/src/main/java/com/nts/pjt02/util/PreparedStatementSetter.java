package com.nts.pjt02.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Sql 실행을 위해 PreparedStatement를 설정하는 인터페이스
 * 
 * @author 유재찬
 *
 */
public interface PreparedStatementSetter {
	void setPreparedStatement(PreparedStatement preparedStatement) throws SQLException;
}
