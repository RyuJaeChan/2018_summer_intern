package com.nts.pjt02.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * properties 파일을 읽고 Properties 객체를 반환
 * 
 * @author 유재찬
 *
 */
public class PropertiesLoader {

	public Properties getProperties(String propertyPath) throws IOException {
		Properties properties = new Properties();
		try (InputStream input = getClass().getClassLoader().getResourceAsStream(propertyPath)) {
			properties.load(input);
		}
		return properties;
	}
}
