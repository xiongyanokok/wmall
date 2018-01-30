package com.xy.wmall.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xy.wmall.common.Constant;
import com.xy.wmall.common.utils.CommonUtils;
import com.xy.wmall.exception.WmallException;

/**
 * 全局异常处理
 * 
 * @author xiongyan
 * @date 2017年3月28日 下午4:15:46
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	/**
	 * WmallException 全局异常
	 * 
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler(WmallException.class)
    @ResponseBody
    public Object exceptionHandler(WmallException e, HttpServletRequest request) {
		logger.error("WmallException：", e);
		if (CommonUtils.isAjax(request)) {
			String message = e.getMessage();
			int index = message.indexOf('】');
			if (index > 0) {
				message = message.substring(index+1);
			}
			return errorJson(message);
		} else {
			return errorView(e);
		}
    } 
	
	/**
	 * RuntimeException 全局异常
	 * 
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Object handleAllException(RuntimeException e, HttpServletRequest request) {
		logger.error("RuntimeException：", e);
		if (CommonUtils.isAjax(request)) {
			return errorJson("系统错误");
		} else {
			return errorView(e);
		}
    } 
	
	/**
	 * 返回错误页面
	 * 
	 * @param e
	 * @return
	 */
	private ModelAndView errorView(Exception e) {
		ModelAndView mv = new ModelAndView(Constant.DEFAULT_ERROR_VIEW);
		mv.addObject(Constant.DEFAULT_MESSAGE, e.getMessage());
		return mv;
	}
	
	/**
	 * 返回错误json
	 * 
	 * @param message
	 * @return
	 */
	private Map<String, Object> errorJson(String message) {
		Map<String, Object> map = new HashMap<>(2);
		map.put(Constant.DEFAULT_CODE, "N");
		map.put(Constant.DEFAULT_MESSAGE, message);
        return map; 
	}
	
}
