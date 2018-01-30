package com.xy.wmall.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xy.wmall.common.Constant;
import com.xy.wmall.common.utils.DateUtils;
import com.xy.wmall.common.utils.Md5Utils;
import com.xy.wmall.enums.TrueFalseStatusEnum;
import com.xy.wmall.exception.WmallException;
import com.xy.wmall.model.Proxy;
import com.xy.wmall.model.User;
import com.xy.wmall.pojo.UserInfo;
import com.xy.wmall.service.ProxyService;
import com.xy.wmall.service.UserService;

/**
 * Controller
 * 
 * @author admin
 * @date 2016年11月17日 下午2:06:29
 */
@Controller
@RequestMapping(value = "/", produces = {"application/json; charset=UTF-8"})
public class MainController extends BaseController {
	
	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private ProxyService proxyService;
	
    /**
	 * 登录页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = {RequestMethod.GET})
	public String index(Model model) {
		return "system/login";
	}
    
    /**
	 * 登录
	 * 
	 * @param username
	 * @param password
	 * @param checkCode
	 * @return
	 */
	@RequestMapping(value = "/login", method = {RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> login(String username, String password, String checkCode) {
		if (StringUtils.isAnyEmpty(username, password, checkCode)) {
			logger.error("代理失败：用户名或密码或验证码为空");
			throw new WmallException("登录失败");
		}
		
		// 验证验证码
		String imageCode = (String) session.getAttribute(Constant.IMAGE_CODE);
		if (StringUtils.isEmpty(imageCode) || !imageCode.equalsIgnoreCase(checkCode)) {
			logger.error("验证码错误：session验证码【{}】，登录验证码【{}】", imageCode, checkCode);
			return buildFail("验证码错误");
		}
		// 清除验证码session
		session.removeAttribute(Constant.IMAGE_CODE);
		
		Map<String, Object> map = new HashMap<>(2);
		map.put("username", username);
		map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
		User user = userService.getUser(map);
		if (null == user || !user.getPassword().equals(Md5Utils.md5(password))) {
			logger.error("用户名或密码错误：用户名【{}】，密码【{}】", username, password);
			return buildFail("用户名或密码错误");
		}
		
		// 添加session
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(user.getId());
		userInfo.setProxyId(user.getProxyId());
		session.setAttribute(Constant.SESSION_KEY, userInfo);
		return buildSuccess("登录成功");
	}
	
	/**
	 * 主页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/main", method = {RequestMethod.GET})
	public String main(Model model) {
		UserInfo userInfo = (UserInfo) session.getAttribute(Constant.SESSION_KEY);
		if (userInfo.getProxyId() == 0) {
			model.addAttribute("name", "管理员");
		} else {
			Proxy proxy = proxyService.getProxyById(getProxyId());
			userInfo.setParentProxyId(0);
			userInfo.setWechatName(proxy.getWechatName());
			model.addAttribute("name", proxy.getWechatName());
		}
		model.addAttribute("time", DateUtils.format(new Date(), DateUtils.NORM_DATE_PATTERN));
		model.addAttribute("week", DateUtils.getWeek());
		return "system/main";
	}
	
	/**
	 * 退出页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/logout", method = {RequestMethod.GET})
	public String logout(Model model) {
		// 清除用户session
		session.removeAttribute(Constant.SESSION_KEY);
		return "redirect:/";
	}
	
}
