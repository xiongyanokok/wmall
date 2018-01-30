package com.xy.wmall.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xy.wmall.common.Assert;
import com.xy.wmall.common.utils.HttpClientUtils;
import com.xy.wmall.common.utils.JacksonUtils;
import com.xy.wmall.enums.TrueFalseStatusEnum;
import com.xy.wmall.model.Deliver;
import com.xy.wmall.model.Logistics;
import com.xy.wmall.model.LogisticsCompany;
import com.xy.wmall.pojo.LogisticsInfo;
import com.xy.wmall.service.DeliverService;
import com.xy.wmall.service.LogisticsCompanyService;
import com.xy.wmall.service.LogisticsService;

/**
 * Controller
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:06
 */
@Controller
@RequestMapping(value = "/admin/logistics", produces = { "application/json; charset=UTF-8" })
public class LogisticsController extends BaseController {

	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(LogisticsController.class);

    @Autowired
	private LogisticsService logisticsService;
    
    @Autowired
	private DeliverService deliverService;
    
    @Autowired
    private LogisticsCompanyService logisticsCompanyService;
	
	/**
	 * 进入列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model) {
		return "logistics/list";
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
			return logisticsService.listLogistics(map);
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
		return "logistics/add";
	}
	
	/**
	 * 保存数据
	 * 
	 * @param logistics
	 * @return
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> save(Logistics logistics) {
		Assert.notNull(logistics, "保存数据为空");
		logistics.setCreateUserId(getUserId());
		logistics.setCreateTime(new Date());
		logistics.setUpdateUserId(getUserId());
		logistics.setUpdateTime(new Date());
		logistics.setIsDelete(TrueFalseStatusEnum.FALSE.getValue());
		logisticsService.save(logistics);
		logger.info("【{}】保存成功", logistics);
		// 立即发货
		deliver(logistics.getDeliverId());
		return buildSuccess("保存成功");
	}
	
	/**
	 * 立即发货
	 * 
	 * @param deliverId
	 */
	private void deliver(Integer deliverId) {
		Deliver deliver = new Deliver();
		deliver.setId(deliverId);
		deliver.setDeliverStatus(TrueFalseStatusEnum.TRUE.getValue());
		deliver.setInventoryStatus(TrueFalseStatusEnum.TRUE.getValue());
		deliver.setUpdateUserId(getUserId());
		deliver.setUpdateTime(new Date());
		deliverService.status(deliver);
		logger.info("【{}】发货成功", deliver);
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
		Logistics logistics = logisticsService.getLogisticsById(id);
		Assert.notNull(logistics, "数据不存在");
		model.addAttribute("logistics", logistics);
		return "logistics/edit";
	}
	
	/**
	 * 进入物流页面
	 * 
	 * @param model
	 * @param deliverId
	 * @return
	 */
	@RequestMapping(value = "/add_edit", method = { RequestMethod.GET })
	public String addOrEdit(Model model, Integer deliverId) {
		Assert.notNull(deliverId, "deliverId为空");
		List<LogisticsCompany> logisticsCompanies = logisticsCompanyService.listLogisticsCompany();
		model.addAttribute("logisticsCompanies", logisticsCompanies);
		Map<String, Object> map = new HashMap<>();
		map.put("deliverId", deliverId);
		map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
		Logistics logistics = logisticsService.getLogistics(map);
		if (null != logistics) {
			model.addAttribute("logistics", logistics);
			return "logistics/edit";
		} else {
			model.addAttribute("deliverId", deliverId);
			return "logistics/add";
		}
	}
	
	/**
	 * 修改数据
	 * 
	 * @param logistics
	 * @return
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> update(Logistics logistics) {
		Assert.notNull(logistics, "修改数据为空");
		Logistics logisticsInfo = logisticsService.getLogisticsById(logistics.getId());
		Assert.notNull(logisticsInfo, "数据不存在");
		logistics.setUpdateUserId(getUserId());
		logistics.setUpdateTime(new Date());
		logisticsService.update(logistics);
		logger.info("【{}】修改成功", logistics);
		// 立即发货
		deliver(logisticsInfo.getDeliverId());
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
		Logistics logistics = logisticsService.getLogisticsById(id);
		Assert.notNull(logistics, "数据不存在");
		logisticsService.remove(logistics);
		logger.info("【{}】删除成功", logistics);
		return buildSuccess("删除成功");
	}
	
	/**
	 * 物流查询地址
	 */
	@Value("${logistics.url}")
	private String logisticsUrl;
	
	/**
	 * 进入详情页面
	 * 
	 * @param model
	 * @param deliverId
	 * @return
	 */
	@RequestMapping(value = "/detail", method = { RequestMethod.GET })
	public String detail(Model model, Integer deliverId) {
		Assert.notNull(deliverId, "deliverId为空");
		// 发货物流信息
		Map<String, Object> map = new HashMap<>();
		map.put("deliverId", deliverId);
		map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
		Logistics logistics = logisticsService.getLogistics(map);
		Assert.notNull(logistics, "数据不存在");
		model.addAttribute("logistics", logistics);
		
		// 物流公司信息
		LogisticsCompany logisticsCompany = logisticsCompanyService.getLogisticsCompanyById(logistics.getCompanyId());
		Assert.notNull(logisticsCompany, "数据不存在");
		model.addAttribute("logisticsCompany", logisticsCompany);
		
		// 获取物流跟踪信息
		String value = HttpClientUtils.get(String.format(logisticsUrl, logisticsCompany.getPinyin(), logistics.getNumber()));
		if (StringUtils.isNotEmpty(value)) {
			value = value.substring(value.indexOf('['), value.lastIndexOf(']') + 1);
			List<LogisticsInfo> logisticsInfos = JacksonUtils.deserialize(value, new TypeReference<List<LogisticsInfo>>() { });
			model.addAttribute("logisticsInfos", logisticsInfos);
		}
		return "logistics/detail";
	}
}
