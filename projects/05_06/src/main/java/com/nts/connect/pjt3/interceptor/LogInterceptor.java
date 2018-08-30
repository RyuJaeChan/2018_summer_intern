package com.nts.connect.pjt3.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author	유재찬
 * @date	2018. 8. 28.
 */
public class LogInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		String clientIp = request.getRemoteAddr();
		String url = request.getRequestURL().toString();

		logger.info("ip : {} url : {}", clientIp, url);
		return true;
	}
}
