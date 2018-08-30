package com.nts.pjt02.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Sql 실행 결과로 생성된 ResultSet에서 데이터 리스트를 가져오는 인터페이스
 * 
 * @author 유재찬
 * @param <T>
 *
 */

public interface ResultListExtractor<T> {
	List<T> getListFromResultSet(ResultSet resultSet) throws SQLException;
}
