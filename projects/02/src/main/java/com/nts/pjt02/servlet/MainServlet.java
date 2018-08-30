package com.nts.pjt02.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
 * 데이터 베이스에서 todo 테이블 조회 후 Main.jsp로 포워드
 * 
 * @author 유재찬 
 *
 */
@WebServlet("/main")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(MainServlet.class);

	private TodoDao dao = new TodoDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		try {
			List<TodoDto> todoList = dao.getTodoList(ProgressType.TODO);
			List<TodoDto> doingList = dao.getTodoList(ProgressType.DOING);
			List<TodoDto> doneList = dao.getTodoList(ProgressType.DONE);

			request.setAttribute("todoList", todoList);
			request.setAttribute("doingList", doingList);
			request.setAttribute("doneList", doneList);
		} catch (SQLException | ClassNotFoundException e) {
			logger.error("Server Error : ", e);
			ErrorPageLoader errorPageLoader = new ErrorPageLoader();
			errorPageLoader.forwardToErrorPage(request, response, "Server Error : 잠시 후 다시 시도해 주세요.");
			return;
		}

		request.getRequestDispatcher("/view/main.jsp").forward(request, response);
	}
}
