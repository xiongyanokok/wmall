package com.xy.wmall.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xy.wmall.common.Constant;

/**
 * 登录过滤器
 * 
 * @author xiongyan
 * @date 2017年10月27日 下午1:59:57
 */
@WebFilter(urlPatterns = {"/admin/*", "/main"})
public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		HttpSession session = servletRequest.getSession();
		Object obj = session.getAttribute(Constant.SESSION_KEY);
		if (null == obj) {
			// session timeout
			servletResponse.setHeader("sessionstatus", "timeout");
			response.getWriter().print("<script>top.location.replace('../../../');</script>");
			return;
		}
        chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// 
	}

}
