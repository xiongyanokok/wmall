package com.xy.wmall.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xy.wmall.common.Assert;
import com.xy.wmall.common.utils.Md5Utils;
import com.xy.wmall.enums.TrueFalseStatusEnum;
import com.xy.wmall.model.Proxy;
import com.xy.wmall.model.User;
import com.xy.wmall.service.ProxyService;
import com.xy.wmall.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:27
 */
@Controller
@RequestMapping(value = "/admin/user", produces = { "application/json; charset=UTF-8" })
@Slf4j
public class UserController extends BaseController {

    @Autowired
	private UserService userService;
    
    @Autowired
	private ProxyService proxyService;
	
    /**
	 * 进入列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model) {
		return "user/list";
	}
	
	/**
	 * 列表分页查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/query", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> query() {
		return pageInfoResult(map -> {
			// 查询条件
			return userService.listByMap(map);
		});
	}
	
	/**
	 * 进入新增页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET })
	public String add(Model model) {
		return "user/add";
	}
	
	/**
	 * 保存数据
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> save(User user) {
		Assert.notNull(user, "保存数据为空");
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setIsDelete(TrueFalseStatusEnum.FALSE.getValue());
		userService.save(user);
		log.info("【{}】保存成功", user);
		return buildSuccess("保存成功");
	}
	
	/**
	 * 进入修改页面
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/edit", method = { RequestMethod.GET })
	public String edit(Model model, Integer id) {
		Assert.notNull(id, "id为空");
		User user = userService.getById(id);
		Assert.notNull(user, "数据不存在");
		model.addAttribute("user", user);
		return "user/edit";
	}
	
	/**
	 * 修改数据
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> update(User user) {
		Assert.notNull(user, "修改数据为空");
		User userInfo = userService.getById(user.getId());
		Assert.notNull(userInfo, "数据不存在");
		user.setUpdateTime(new Date());
		userService.update(user);
		log.info("【{}】修改成功", user);
		return buildSuccess("修改成功");
	}
	
	/**
	 * 删除数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> delete(Integer id) {
		Assert.notNull(id, "id为空");
		User user = userService.getById(id);
		Assert.notNull(user, "数据不存在");
		userService.remove(user);
		log.info("【{}】删除成功", user);
		return buildSuccess("删除成功");
	}
	
	/**
	 * 进入个人信息
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/info", method = { RequestMethod.GET })
	public String info(Model model) {
		Integer proxyId = getProxyId();
		Proxy proxy = proxyService.getById(proxyId);
		Assert.notNull(proxy, "代理不存在");
		model.addAttribute("proxy", proxy);
		return "system/info";
	}
	
	/**
	 * 进入修改密码页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pwd", method = { RequestMethod.GET })
	public String pwd(Model model) {
		return "user/pwd";
	}
	
	/**
	 * 修改密码
	 * 
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@RequestMapping(value = "/password", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> password(String oldPassword, String newPassword) {
		Assert.hasLength(oldPassword, "oldPassword为空");
		Assert.hasLength(newPassword, "newPassword为空");
		User user = userService.getById(getUserId());
		Assert.notNull(user, "用户不存在");
		if (!user.getPassword().equals(Md5Utils.md5(oldPassword))) {
			log.error("当前密码错误：密码【{}】", oldPassword);
			return buildFail("当前密码错误");
		}
		if (oldPassword.equals(newPassword)) {
			log.error("新密码不能和旧密码一致：新密码【{}】， 旧密码【{}】", newPassword, oldPassword);
			return buildFail("密码不能一致");
		}
		// 修改密码
		user.setPassword(Md5Utils.md5(newPassword));
		userService.update(user);
		log.info("【{}】修改密码成功", user);
		return buildSuccess("密码修改成功");
	}
	
}
