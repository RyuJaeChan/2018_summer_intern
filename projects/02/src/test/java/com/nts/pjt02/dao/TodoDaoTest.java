package com.nts.pjt02.dao;

import static org.junit.Assert.assertSame;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.nts.pjt02.dto.TodoDto;
import com.nts.pjt02.enumeration.ProgressType;

public class TodoDaoTest {
	TodoDao dao;
	Connection conn;

	@Before
	public void set() throws IOException, ClassNotFoundException, SQLException {
		this.dao = new TodoDao();

		conn = DriverManager
			.getConnection("jdbc:mysql://10.67.8.165:3306/db?useUnicode=true&characterEncoding=utf8", "root", "qwe123");

		conn.setAutoCommit(false);

		int max = 100000;
		for (int i = 0; i < max; i++) {
			insertData("TODO");
		}
		for (int i = 0; i < max; i++) {
			insertData("DOING");
		}
		for (int i = 0; i < max; i++) {
			insertData("DONE");
		}

	}

	@Test
	public void loopTest() throws SQLException {
		List<TodoDto> list = selectQuery("");

		List<TodoDto> todoList = new ArrayList<>();
		List<TodoDto> doingList = new ArrayList<>();
		List<TodoDto> doneList = new ArrayList<>();

		for (TodoDto element : list) {
			switch (element.getType()) {
				case "TODO":
					todoList.add(element);
					break;
				case "DONE":
					doneList.add(element);
					break;
				case "DOING":
					doingList.add(element);
					break;
			}
		}

	}

	@Test
	public void sql3TimeTest() throws SQLException {
		List<TodoDto> todoList = selectQuery("TODO");
		List<TodoDto> doingList = selectQuery("DOING");
		List<TodoDto> doneList = selectQuery("DONE");

	}

	public List<TodoDto> selectQuery(String type) throws SQLException {
		String sql = "SELECT * FROM todo WHERE type=? ORDER BY regdate";
		if ("".equals(type)) {
			sql = "SELECT id, title, name, sequence, type, regdate FROM todo ORDER BY regdate";
		}
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		if ("".equals(type) == false) {
			preparedStatement.setString(1, type);
		}

		ResultSet resultSet = preparedStatement.executeQuery();
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
	}

	@Test
	public void testAddTodo() throws SQLException, ClassNotFoundException, IOException {
		TodoDto dto = new TodoDto();
		dto.setName("junit");
		dto.setSequence(1);
		dto.setTitle("junit_test");
		dto.setType("TODO");
		assertSame(1, dao.addTodo(dto));
	}

	@Test
	public void testUpdateTodo() throws SQLException, ClassNotFoundException, IOException {
		assertSame(1, dao.updateTodo("31", ProgressType.DONE));
		dao.updateTodo("31", ProgressType.TODO);
	}

	@Test
	public void testGetTodoList() throws SQLException, ClassNotFoundException, IOException {
		List<TodoDto> todoList = dao.getTodoList(ProgressType.TODO);
		assertSame(3, todoList.size());
	}

	@After
	public void set1() throws SQLException {
		conn.rollback();
	}

	public void insertData(String type) throws SQLException {

		String sql = "INSERT INTO todo(title, name, sequence, type) VALUES(?,?,?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, "junit test");
		preparedStatement.setString(2, "title");
		preparedStatement.setInt(3, 1);
		preparedStatement.setString(4, type);

		preparedStatement.executeUpdate();

	}

}
