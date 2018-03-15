package com.xy.wmall.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xy.wmall.common.utils.CommonUtils;
import com.xy.wmall.model.Product;
import com.xy.wmall.service.BackupService;
import com.xy.wmall.service.DeliverService;
import com.xy.wmall.service.ProductService;
import com.xy.wmall.service.WalletService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:27
 */
@Controller
@RequestMapping(value = "/admin/home", produces = { "application/json; charset=UTF-8" })
@Slf4j
public class HomeController extends BaseController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private WalletService walletService;
	
	@Autowired
	private DeliverService deliverService;
	
	@Autowired
	private BackupService backupService;
	
	/**
	 * 进入列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index", method = { RequestMethod.GET })
	public String list(Model model) {
		List<Product> products = productService.listProduct();
		model.addAttribute("products", products);
		
		Map<String, Object> map = CommonUtils.defaultQueryMap();
		map.put("operator", "<>");
		map.put("proxyId", getProxyId());
		// 代理存款
		Integer proxyWallet = walletService.getStatisticsWallet(map);
		if (null != proxyWallet) {
			model.addAttribute("proxyWallet", proxyWallet);
		}
		// 自己存款
		map.put("operator", "=");
		proxyWallet = walletService.getStatisticsWallet(map);
		if (null != proxyWallet) {
			model.addAttribute("myWallet", proxyWallet);
		}
		// 待发货
		map.put("parentProxyId", getProxyId());
		int waitDeliver = deliverService.countWaitDeliver(map);
		model.addAttribute("waitDeliver", waitDeliver);
		
		return "home/index";
	}
	
	/**
	 * 数据备份
	 * 
	 * @return
	 */
	@RequestMapping(value = "/backup", method = { RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> backup() {
		if (backupService.backup()) {
			log.info("数据库备份成功");
			return buildSuccess("备份成功");
		} else {
			log.info("数据库备份失败");
			return buildFail("备份失败");
		}
	}
	
}
