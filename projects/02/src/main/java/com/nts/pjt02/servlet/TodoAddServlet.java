package com.nts.pjt02.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.nts.pjt02.dao.TodoDao;
import com.nts.pjt02.dto.TodoDto;
import com.nts.pjt02.enumeration.ProgressType;
import com.nts.pjt02.util.ErrorPageLoader;

/**
 * todoForm.jsp에서 받은 정보를 데이터 베이스에 입력
 * 
 * @author 유재찬
 *
 */
@WebServlet("/add")
public class TodoAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(TodoAddServlet.class);

	private TodoDao dao = new TodoDao();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		try {
			String name = request.getParameter("name");
			String title = request.getParameter("title");
			int sequence = Integer.parseInt(request.getParameter("sequence"));

			if (this.checkParameter(name, title, sequence) == false) {
				ErrorPageLoader errorPageLoader = new ErrorPageLoader();
				errorPageLoader.forwardToErrorPage(request, response, "Client Error : 입력 양식을 확인해주세요.");
				return;
			}

			int result = dao.addTodo(new TodoDto(title, name, sequence, ProgressType.TODO.toString()));

			if (result != 1) {
				logger.error("Server Error : Data insertion fail.");
				ErrorPageLoader errorPageLoader = new ErrorPageLoader();
				errorPageLoader.forwardToErrorPage(request, response, "Server Error : 잠시 후 다시 시도해 주세요.");
				return;
			}
		} catch (NumberFormatException e) {
			ErrorPageLoader errorPageLoader = new ErrorPageLoader();
			errorPageLoader.forwardToErrorPage(request, response, "Client Error : 입력 양식을 확인해주세요.");
			return;
		} catch (SQLException | ClassNotFoundException e) {
			logger.error("Server Error : ", e);
			ErrorPageLoader errorPageLoader = new ErrorPageLoader();
			errorPageLoader.forwardToErrorPage(request, response, "Server Error : 잠시 후 다시 시도해 주세요.");
			return;
		}

		response.sendRedirect("main");
	}

	private boolean checkParameter(String name, String title, int sequence) {
		if ("".equals(name) || "".equals(title) || name.length() > 32 || title.length() > 24) {
			return false;
		}
		if (sequence < 1 || 3 < sequence) {
			return false;
		}
		return true;
	}

}
