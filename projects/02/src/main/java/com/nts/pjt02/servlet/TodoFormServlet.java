package com.nts.pjt02.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * todoForm.jsp로 포워딩
 * 
 * @author 유재찬
 *
 */
@WebServlet("/form")
public class TodoFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		request.getRequestDispatcher("/view/todoForm.jsp").forward(request, response);
	}
}