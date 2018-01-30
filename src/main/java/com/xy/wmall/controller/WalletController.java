package com.xy.wmall.controller;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xy.wmall.common.Assert;
import com.xy.wmall.enums.ArithmeticTypeEnum;
import com.xy.wmall.enums.TrueFalseStatusEnum;
import com.xy.wmall.model.Proxy;
import com.xy.wmall.model.Wallet;
import com.xy.wmall.service.ProxyService;
import com.xy.wmall.service.WalletService;

/**
 * Controller
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:30
 */
@Controller
@RequestMapping(value = "/admin/wallet", produces = { "application/json; charset=UTF-8" })
public class WalletController extends BaseController {

	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

    @Autowired
	private WalletService walletService;
	
    @Autowired
	private ProxyService proxyService;
    
	/**
	 * 进入列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model, Integer proxyId) {
		if (null == proxyId) {
			proxyId = getProxyId();
		}
		Assert.notNull(proxyId, "proxyId为空");
		Proxy proxy = proxyService.getProxyById(proxyId);
		Assert.notNull(proxy, "代理不存在");
		model.addAttribute("proxy", proxy);
		return "wallet/list";
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
			String proxyId = request.getParameter("proxyId");
			Assert.notNull(proxyId, "proxyId为空");
			// 代理ID
			map.put("proxyId", proxyId); 
			// 类型
			map.put("type", request.getParameter("type")); 
			return walletService.listWallet(map);
		});
	}
	
	/**
	 * 进入添加存款页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET })
	public String add(Model model, Integer proxyId) {
		Assert.notNull(proxyId, "proxyId为空");
		Proxy proxy = proxyService.getProxyById(proxyId);
		Assert.notNull(proxy, "代理不存在");
		model.addAttribute("proxy", proxy);
		model.addAttribute("type", ArithmeticTypeEnum.ADD.getValue());
		return "wallet/add";
	}
	
	/**
	 * 进入消费支出页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/expenses", method = { RequestMethod.GET })
	public String expenses(Model model, Integer proxyId) {
		Assert.notNull(proxyId, "proxyId为空");
		Proxy proxy = proxyService.getProxyById(proxyId);
		Assert.notNull(proxy, "代理不存在");
		model.addAttribute("proxy", proxy);
		model.addAttribute("type", ArithmeticTypeEnum.SUB.getValue());
		// 查询代理钱包余额
		model.addAttribute("balance", walletService.getWalletBalance(proxyId));
		return "wallet/add";
	}
	
	/**
	 * 保存数据
	 * 
	 * @param wallet
	 * @return
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> save(Wallet wallet) {
		Assert.notNull(wallet, "保存数据为空");
		wallet.setCreateUserId(getUserId());
		wallet.setCreateTime(new Date());
		wallet.setUpdateUserId(getUserId());
		wallet.setUpdateTime(new Date());
		wallet.setIsDelete(TrueFalseStatusEnum.FALSE.getValue());
		walletService.save(wallet);
		logger.info("【{}】保存成功", wallet);
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
		Wallet wallet = walletService.getWalletById(id);
		Assert.notNull(wallet, "数据不存在");
		model.addAttribute("wallet", wallet);
		Proxy proxy = proxyService.getProxyById(wallet.getProxyId());
		Assert.notNull(proxy, "代理不存在");
		model.addAttribute("proxy", proxy);
		return "wallet/edit";
	}
	
	/**
	 * 修改数据
	 * 
	 * @param wallet
	 * @return
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> update(Wallet wallet) {
		Assert.notNull(wallet, "修改数据为空");
		Wallet walletInfo = walletService.getWalletById(wallet.getId());
		Assert.notNull(walletInfo, "数据不存在");
		wallet.setUpdateUserId(getUserId());
		wallet.setUpdateTime(new Date());
		walletService.update(wallet);
		logger.info("【{}】修改成功", wallet);
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
		Wallet wallet = walletService.getWalletById(id);
		Assert.notNull(wallet, "数据不存在");
		walletService.remove(wallet);
		logger.info("【{}】删除成功", wallet);
		return buildSuccess("删除成功");
	}
	
}
