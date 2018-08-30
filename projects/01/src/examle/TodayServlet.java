package examle;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TodayServlet.java
 * 오늘 날짜를 출력하는 서블릿 페이지
 *
 * @version		1.0		2018. 07.10.
 * @author 		유재찬
 */

@WebServlet("/today")
public class TodayServlet extends HttpServlet {

	/**
	 * GET 요청을 받아 현재 시간을 브라우저 정중앙에 출력한다.
	 *
	 * @author 유재찬
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8;http-equiv=\"content-language\";content=\"ko\"");

		String outHtml = "";
		outHtml += "<link rel=\"stylesheet\" href=\"./css/style.css\">";
		outHtml += "<body class=\"today_body\">";
		outHtml += "<a href=\"./index.html\">메인화면</a>";
		outHtml += "<div class=\"outter\">";
		outHtml += 		"<div class=\"inner\">";
		outHtml += 			"<h2>";
		outHtml += 				"현재 시간 : " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
		outHtml += 			"</h2>";
		outHtml += 		"</div>";
		outHtml += "</div>";
		outHtml += "</body>";

		response.getWriter().print(outHtml);
		response.getWriter().close();
	}
}
