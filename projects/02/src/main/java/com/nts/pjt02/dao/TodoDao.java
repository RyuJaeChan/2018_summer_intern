package com.nts.pjt02.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nts.pjt02.dto.TodoDto;
import com.nts.pjt02.enumeration.ProgressType;
import com.nts.pjt02.util.SqlExecuter;

/**
 * 데이터 베이스를 통해 Todo 리스트 관리 
 * 
 * @author 유재찬
 * @version 1.0		2018. 07. 16.
 */

public class TodoDao {

	/**
	 * @param todo	: 삽입 할 데이터
	 * @return 1 : 성공, else : 실패
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public int addTodo(TodoDto todo) throws SQLException, ClassNotFoundException, IOException {
		String sql = "INSERT INTO todo(title, name, sequence, type) VALUES(?,?,?,?)";

		return SqlExecuter.executeUpdate(sql,
			(preparedStatement) -> {
				preparedStatement.setString(1, todo.getTitle());
				preparedStatement.setString(2, todo.getName());
				preparedStatement.setInt(3, todo.getSequence());
				preparedStatement.setString(4, todo.getType());
			});
	}

	/**
	 * @param id	: 수정할 id
	 * @param type	: 변경할 상태값
	 * @return	1 : 성공, else : 실패
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public int updateTodo(String id, ProgressType type)
		throws SQLException, ClassNotFoundException, IOException {
		String sql = "UPDATE todo SET type=? WHERE id=?";

		return SqlExecuter.executeUpdate(sql,
			(preparedStatement) -> {
				preparedStatement.setString(1, type.toString());
				preparedStatement.setString(2, id);
			});
	}

	/**
	 * @param type	: 검색할 type
	 * @return	List<TodoDto> : 검색 결과로 얻은 데이터 리스트
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public List<TodoDto> getTodoList(ProgressType type)
		throws SQLException, ClassNotFoundException, IOException {
		String sql = "SELECT id, title, name, sequence, type, regdate FROM todo WHERE type=? ORDER BY regdate";

		return SqlExecuter.executeQuery(sql,
			(preparedStatement) -> {
				preparedStatement.setString(1, type.toString());
			},
			(resultSet) -> {
				List<TodoDto> list = new ArrayList<>();
				while (resultSet.next()) {
					TodoDto dto = new TodoDto();
					dto.setId(resultSet.getLong(1));
					dto.setTitle(resultSet.getString(2));
					dto.setName(resultSet.getString(3));
					dto.setSequence(resultSet.getInt(4));
					dto.setType(resultSet.getString(5));
					dto.setRegdate(resultSet.getString(6));
					list.add(dto);
				}
				return list;
			});
	}
}
