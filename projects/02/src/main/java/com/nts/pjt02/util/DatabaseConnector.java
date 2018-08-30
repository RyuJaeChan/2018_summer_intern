package com.nts.pjt02.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 데이터 베이스에 접속해 sql 실행
 * 
 * @author 유재찬
 *
 */
public class DatabaseConnector {
	private String jdbcUrl;
	private String userId;
	private String userPassword;
	private String driver;

	static private DatabaseConnector instance;

	private DatabaseConnector() throws ClassNotFoundException, IOException {
		this.readPropertiesFile("config/db.properties");
		Class.forName(driver);
	}

	public static DatabaseConnector getInstance() throws ClassNotFoundException, IOException {
		if (instance == null) {
			instance = new DatabaseConnector();
		}
		return instance;
	}

	private void readPropertiesFile(String properyPath) throws IOException {
		PropertiesLoader propertiesLoader = new PropertiesLoader();
		Properties properties = propertiesLoader.getProperties(properyPath);

		jdbcUrl = properties.getProperty("db.url");
		userId = properties.getProperty("db.id");
		userPassword = properties.getProperty("db.password");
		driver = properties.getProperty("db.driver");
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(jdbcUrl, userId, userPassword);
	}

}