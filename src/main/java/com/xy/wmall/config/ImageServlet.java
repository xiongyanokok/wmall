package com.xy.wmall.config;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xy.wmall.common.Constant;
import com.xy.wmall.common.VerifyCodeUtils;

/**
 * 图片验证码
 * 
 * @author xiongyan
 * @date 2017年10月31日 下午12:15:28
 */
@WebServlet(urlPatterns = "/image")
public class ImageServlet extends HttpServlet implements Servlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * service
	 */
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/png");

		// 生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		// 存入会话session
		HttpSession session = request.getSession(true);
		String imageCode = verifyCode.toLowerCase();
		session.setAttribute(Constant.IMAGE_CODE, imageCode);
		// 生成图片
		int w = 100;
		int h = 39;
		VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
	}

}
