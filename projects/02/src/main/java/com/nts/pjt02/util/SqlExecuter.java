package com.nts.pjt02.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 데이터 베이스에 접속하여 sql을 실행
 * 
 * @author 유재찬
 */

public class SqlExecuter {
	static public int executeUpdate(String sql, PreparedStatementSetter pss)
		throws SQLException, ClassNotFoundException, IOException {
		try (PreparedStatement preparedStatement = getPreparedStatement(sql, pss);) {

			return preparedStatement.executeUpdate();
		}
	}

	static public <T> List<T> executeQuery(String sql, PreparedStatementSetter pss, ResultListExtractor<T> rle)
		throws SQLException, ClassNotFoundException, IOException {
		try (PreparedStatement preparedStatement = getPreparedStatement(sql, pss);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			return rle.getListFromResultSet(resultSet);
		}
	}

	static private PreparedStatement getPreparedStatement(String sql, PreparedStatementSetter pss)
		throws SQLException, ClassNotFoundException, IOException {
		Connection connection = DatabaseConnector.getInstance().getConnection();

		PreparedStatement preparedStatement = connection.prepareStatement(sql);

		pss.setPreparedStatement(preparedStatement);

		return preparedStatement;
	}
}
