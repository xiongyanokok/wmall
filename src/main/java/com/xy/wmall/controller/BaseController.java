package com.xy.wmall.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xy.wmall.common.Constant;
import com.xy.wmall.common.CustomDateEditor;
import com.xy.wmall.common.utils.CommonUtils;
import com.xy.wmall.pojo.UserInfo;

/**
 * controller的基类
 * 
 * @author xiongyan
 * @date 2016年10月27日 下午2:06:29
 */
public abstract class BaseController {

	/**
	 * HttpServletRequest
	 */
	@Autowired
	protected HttpServletRequest request;

	/**
	 * HttpServletResponse
	 */
	@Autowired
	protected HttpServletResponse response;

	/**
	 * HttpSession
	 */
	@Autowired
	protected HttpSession session;
	
	/**
	 * 构建成功数据
	 * 
	 * @param obj
	 * @return
	 */
	public Map<String, Object> buildData(Object obj) {
		Map<String, Object> map = new HashMap<>(2);
		map.put(Constant.DEFAULT_CODE, "Y");
		map.put(Constant.DEFAULT_DATA, obj);
		return map;
	}
	
	/**
	 * 构建成功消息
	 * 
	 * @param obj
	 * @return
	 */
	public Map<String, Object> buildSuccess(Object obj) {
		Map<String, Object> map = new HashMap<>(2);
		map.put(Constant.DEFAULT_CODE, "Y");
		map.put(Constant.DEFAULT_MESSAGE, obj);
		return map;
	}

	/**
	 * 构建失败消息
	 * 
	 * @param obj
	 * @return
	 */
	public Map<String, Object> buildFail(Object obj) {
		Map<String, Object> map = new HashMap<>(2);
		map.put(Constant.DEFAULT_CODE, "N");
		map.put(Constant.DEFAULT_MESSAGE, obj);
		return map;
	}

	/**
	 * 分页查询回调
	 * 
	 * @author xiongyan
	 * @date 2016年11月21日 下午2:51:22
	 */
	public interface QueryCallback<T> {

		/**
		 * 查询数据库
		 * 
		 * @param map
		 * @return
		 */
		List<T> query(Map<String, Object> map);
	}

	/**
	 * 分页方法
	 * 
	 * @param request
	 * @param callback
	 * @return
	 */
	public <T> Map<String, Object> pageInfoResult(final QueryCallback<T> callback) {
		// 索引开始数
		String offset = request.getParameter("offset");
		if (StringUtils.isEmpty(offset)) {
			offset = "0";
		}
		// 每页显示条数
		String limit = request.getParameter("limit");
		if (StringUtils.isEmpty(limit)) {
			limit = "10";
		}

		// 设置分页参数
		PageHelper.offsetPage(Integer.valueOf(offset), Integer.valueOf(limit));

		Map<String, Object> map = CommonUtils.defaultQueryMap();
		// 排序
		map.put("orderBy", CommonUtils.humpToLine(request.getParameter("orderBy")));
		// 查询数据库
		List<T> list = callback.query(map);

		// 分页
		PageInfo<T> pageInfo = new PageInfo<>(list);

		// jquery dataTable 分页参数
		Map<String, Object> result = new HashMap<>(4);
		result.put(Constant.DEFAULT_CODE, "Y");
		result.put(Constant.DEFAULT_DATA, pageInfo.getList());
		result.put("recordsTotal", pageInfo.getTotal());
		result.put("recordsFiltered", pageInfo.getTotal());
		return result;
	}
	
	/**
	 * 字符串转日期 格式化
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor());
	}
	
	/**
	 * 获取session中的用户信息
	 * @return
	 */
	public UserInfo getUserInfo() {
		return (UserInfo) session.getAttribute(Constant.SESSION_KEY);
	}
	
	/**
	 * 用户ID
	 * 
	 * @return
	 */
	public Integer getUserId() {
		return getUserInfo().getUserId();
	}
	
	/**
	 * 代理ID
	 * 
	 * @return
	 */
	public Integer getProxyId() {
		return getUserInfo().getProxyId();
	}
	
	/**
	 * 上级代理id
	 * 
	 * @return
	 */
	public Integer getParentProxyId() {
		return getUserInfo().getParentProxyId();
	}
	
}
