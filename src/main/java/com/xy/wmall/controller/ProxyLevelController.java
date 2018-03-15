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
import com.xy.wmall.enums.TrueFalseStatusEnum;
import com.xy.wmall.model.ProxyLevel;
import com.xy.wmall.service.ProxyLevelService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:24
 */
@Controller
@RequestMapping(value = "/admin/proxylevel", produces = { "application/json; charset=UTF-8" })
@Slf4j
public class ProxyLevelController extends BaseController {

    @Autowired
	private ProxyLevelService proxyLevelService;
	
	/**
	 * 进入列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model) {
		return "proxylevel/list";
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
			return proxyLevelService.listByMap(map);
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
		return "proxylevel/add";
	}
	
	/**
	 * 保存数据
	 * 
	 * @param proxyLevel
	 * @return
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> save(ProxyLevel proxyLevel) {
		Assert.notNull(proxyLevel, "保存数据为空");
		proxyLevel.setCreateUserId(getUserId());
		proxyLevel.setCreateTime(new Date());
		proxyLevel.setUpdateUserId(getUserId());
		proxyLevel.setUpdateTime(new Date());
		proxyLevel.setIsDelete(TrueFalseStatusEnum.FALSE.getValue());
		proxyLevelService.save(proxyLevel);
		log.info("【{}】保存成功", proxyLevel);
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
		ProxyLevel proxyLevel = proxyLevelService.getById(id);
		Assert.notNull(proxyLevel, "数据不存在");
		model.addAttribute("proxyLevel", proxyLevel);
		return "proxylevel/edit";
	}
	
	/**
	 * 修改数据
	 * 
	 * @param proxyLevel
	 * @return
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> update(ProxyLevel proxyLevel) {
		Assert.notNull(proxyLevel, "修改数据为空");
		ProxyLevel proxyLevelInfo = proxyLevelService.getById(proxyLevel.getId());
		Assert.notNull(proxyLevelInfo, "数据不存在");
		proxyLevel.setUpdateUserId(getUserId());
		proxyLevel.setUpdateTime(new Date());
		proxyLevelService.update(proxyLevel);
		log.info("【{}】修改成功", proxyLevel);
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
		ProxyLevel proxyLevel = proxyLevelService.getById(id);
		Assert.notNull(proxyLevel, "数据不存在");
		proxyLevelService.remove(proxyLevel);
		log.info("【{}】删除成功", proxyLevel);
		return buildSuccess("删除成功");
	}
	
}
