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
import com.xy.wmall.model.LogisticsCompany;
import com.xy.wmall.service.LogisticsCompanyService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller
 * 
 * @author admin
 * @date 2018年01月18日 下午09:30:03
 */
@Controller
@RequestMapping(value = "/admin/logisticscompany", produces = { "application/json; charset=UTF-8" })
@Slf4j
public class LogisticsCompanyController extends BaseController {

    @Autowired
	private LogisticsCompanyService logisticsCompanyService;
	
	/**
	 * 进入列表页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model) {
		return "logisticscompany/list";
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
			map.put("name", request.getParameter("name")); 
			return logisticsCompanyService.listByMap(map);
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
		return "logisticscompany/add";
	}
	
	/**
	 * 保存数据
	 * 
	 * @param logisticsCompany
	 * @return
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> save(LogisticsCompany logisticsCompany) {
		Assert.notNull(logisticsCompany, "保存数据为空");
		logisticsCompany.setCreateUserId(getUserId());
		logisticsCompany.setCreateTime(new Date());
		logisticsCompany.setUpdateUserId(getUserId());
		logisticsCompany.setUpdateTime(new Date());
		logisticsCompany.setIsDelete(TrueFalseStatusEnum.FALSE.getValue());
		logisticsCompanyService.save(logisticsCompany);
		log.info("【{}】保存成功", logisticsCompany);
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
		LogisticsCompany logisticsCompany = logisticsCompanyService.getById(id);
		Assert.notNull(logisticsCompany, "数据不存在");
		model.addAttribute("logisticsCompany", logisticsCompany);
		return "logisticscompany/edit";
	}
	
	/**
	 * 修改数据
	 * 
	 * @param logisticsCompany
	 * @return
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> update(LogisticsCompany logisticsCompany) {
		Assert.notNull(logisticsCompany, "修改数据为空");
		LogisticsCompany logisticsCompanyInfo = logisticsCompanyService.getById(logisticsCompany.getId());
		Assert.notNull(logisticsCompanyInfo, "数据不存在");
		logisticsCompany.setUpdateUserId(getUserId());
		logisticsCompany.setUpdateTime(new Date());
		logisticsCompanyService.update(logisticsCompany);
		log.info("【{}】修改成功", logisticsCompany);
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
		LogisticsCompany logisticsCompany = logisticsCompanyService.getById(id);
		Assert.notNull(logisticsCompany, "数据不存在");
		logisticsCompanyService.remove(logisticsCompany);
		log.info("【{}】删除成功", logisticsCompany);
		return buildSuccess("删除成功");
	}
	
}
