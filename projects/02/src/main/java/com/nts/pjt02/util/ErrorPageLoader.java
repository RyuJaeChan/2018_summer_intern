package com.nts.pjt02.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorPageLoader {
	public void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response, String errorMessage)
		throws ServletException, IOException {

		request.setAttribute("errorMessage", errorMessage);

		request.getRequestDispatcher("view/error.jsp").forward(request, response);
	}
}
