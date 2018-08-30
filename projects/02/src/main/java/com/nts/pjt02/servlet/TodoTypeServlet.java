package com.nts.pjt02.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nts.pjt02.dao.TodoDao;
import com.nts.pjt02.enumeration.ProgressType;

/**
 * 일정의 상태값을 변경하여 데이터 베이스에 업데이트한다.
 * 업데이트를 성공적으로 마치면 'success'를 보내고 실패할 경우 'fail'을 보낸다. 
 * @author 유재찬
 *
 */
@WebServlet("/type/*")
public class TodoTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(TodoTypeServlet.class);

	private static final String FAIL_MASSAGE = "fail";
	private static final String SUCCESS_MASSAGE = "success";

	private TodoDao dao = new TodoDao();

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		if (pathInfo == null) {
			response.getWriter().print(FAIL_MASSAGE);
			return;
		}

		String[] pathParts = pathInfo.split("/");
		if (pathParts.length < 2) {
			response.getWriter().print(FAIL_MASSAGE);
			return;
		}

		String id = pathParts[1];
		int result = 0;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonData = objectMapper.readValue(request.getInputStream(), JsonNode.class);

			String type = jsonData.get("type").asText();

			result = dao.updateTodo(id, ProgressType.valueOf(type));
		} catch (SQLException | IOException | ClassNotFoundException e) {
			logger.error("Server Error : ", e);
			response.getWriter().print(FAIL_MASSAGE);
			return;
		} catch (IllegalArgumentException | NullPointerException e) {
			response.getWriter().print(FAIL_MASSAGE);
			return;
		}

		if (result == 0) {
			logger.error("Update fail - check ID : " + id);
			response.getWriter().print(FAIL_MASSAGE);
			return;
		}

		response.getWriter().print(SUCCESS_MASSAGE);
	}

}
