package com.xy.wmall.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xy.wmall.common.Assert;
import com.xy.wmall.common.utils.CommonUtils;
import com.xy.wmall.common.utils.DateUtils;
import com.xy.wmall.common.utils.JacksonUtils;
import com.xy.wmall.enums.DeliverTypeEnum;
import com.xy.wmall.enums.TrueFalseStatusEnum;
import com.xy.wmall.model.Deliver;
import com.xy.wmall.model.DeliverDetail;
import com.xy.wmall.model.Logistics;
import com.xy.wmall.model.LogisticsCompany;
import com.xy.wmall.model.Product;
import com.xy.wmall.model.Proxy;
import com.xy.wmall.service.DeliverDetailService;
import com.xy.wmall.service.DeliverService;
import com.xy.wmall.service.LogisticsCompanyService;
import com.xy.wmall.service.LogisticsService;
import com.xy.wmall.service.ProductService;
import com.xy.wmall.service.ProxyService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller
 * 
 * @author admin
 * @date 2017年10月28日 上午08:53:59
 */
@Controller
@RequestMapping(value = "/admin/deliver", produces = { "application/json; charset=UTF-8" })
@Slf4j
public class DeliverController extends BaseController {

    @Autowired
	private DeliverService deliverService;
    
    @Autowired
    private ProxyService proxyService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private DeliverDetailService deliverDetailService;
    
    @Autowired
    private LogisticsService logisticsService;
    
    @Autowired
    private LogisticsCompanyService logisticsCompanyService;
    
	
	/**
	 * 进入列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model) {
		List<Product> products = productService.listProduct();
		model.addAttribute("products", products);
		model.addAttribute("productsJson", JacksonUtils.serialize(products));
		model.addAttribute("parentProxyId", getParentProxyId());
		return "deliver/list";
	}
	
	/**
	 * 进入自己发货列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/self_list", method = { RequestMethod.GET })
	public String selfList(Model model) {
		List<Product> products = productService.listProduct();
		model.addAttribute("products", products);
		model.addAttribute("productsJson", JacksonUtils.serialize(products));
		return "deliver/self_list";
	}
	
	/**
	 * 进入老大发货列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/super_list", method = { RequestMethod.GET })
	public String superList(Model model) {
		List<Product> products = productService.listProduct();
		model.addAttribute("products", products);
		model.addAttribute("productsJson", JacksonUtils.serialize(products));
		return "deliver/super_list";
	}
	
	/**
	 * 进入工厂发货列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/factory_list", method = { RequestMethod.GET })
	public String factoryList(Model model) {
		List<Product> products = productService.listProduct();
		model.addAttribute("products", products);
		model.addAttribute("productsJson", JacksonUtils.serialize(products));
		return "deliver/factory_list";
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
			// 代理ID
			map.put("proxyId", request.getParameter("proxyId")); 
			// 代理昵称
			map.put("wechatName", request.getParameter("wechatName")); 
			// 产品id
			map.put("productId", request.getParameter("productId")); 
			// 收件人姓名
			map.put("receiveName", request.getParameter("receiveName"));
			// 发货类型 
			String deliverType = request.getParameter("deliverType"); 
			if (StringUtils.isNotEmpty(deliverType)) {
				if (DeliverTypeEnum.SUPER_DELIVER.getValue().equals(Integer.valueOf(deliverType))) {
					map.put("deliverTypes", Arrays.asList(DeliverTypeEnum.SUPER_DELIVER.getValue(), DeliverTypeEnum.FACTORY_DELIVER.getValue()));
				} else {
					map.put("deliverType", deliverType); 
				}
			}
			// 发货状态
			map.put("deliverStatus", request.getParameter("deliverStatus")); 
			// 对货状态
			map.put("inventoryStatus", request.getParameter("inventoryStatus"));
			// 发货开始时间
			map.put("startDate", request.getParameter("startDate"));
			// 发货结束时间
			map.put("endDate", request.getParameter("endDate")); 
			// 发货单id
			map.put("groupBy", "id"); 
			// 查询发货单
			List<Deliver> delivers = deliverService.listByMap(map);
			if (CollectionUtils.isEmpty(delivers)) {
				return Collections.emptyList();
			}
			
			// 获取发货单id
			List<Integer> deliverIds = new ArrayList<>(delivers.size());
			for (Deliver deliver : delivers) {
				deliverIds.add(deliver.getId());
			}
			// 查询发货单详情
			Map<String, Object> detailMap = new HashMap<>();
			detailMap.put("deliverIds", deliverIds);
			List<DeliverDetail> deliverDetails = deliverDetailService.listByMap(detailMap);
			for (Deliver deliver : delivers) {
				List<DeliverDetail> details = new ArrayList<>();
				for (DeliverDetail deliverDetail : deliverDetails) {
					if (deliver.getId().equals(deliverDetail.getDeliverId())) {
						details.add(deliverDetail);
					}
				}
				deliver.setDeliverDetails(details);
			}
			return delivers;
		});
	}
	
	/**
	 * 进入新增页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = { RequestMethod.GET })
	public String add(Model model, Integer proxyId) {
		if (null == proxyId) {
			proxyId = getProxyId();
		} else {
			model.addAttribute("proxyId", proxyId);
		}
		Assert.notNull(proxyId, "proxyId为空");
		Proxy proxy = proxyService.getById(proxyId);
		Assert.notNull(proxy, "代理不存在");
		model.addAttribute("proxy", proxy);
		List<Product> products = productService.listProduct();
		model.addAttribute("products", products);
		return "deliver/add";
	}
	
	/**
	 * 保存数据
	 * 
	 * @param deliver
	 * @return
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> save(Deliver deliver) {
		Assert.notNull(deliver, "保存数据为空");
		if (deliver.getProxyId().equals(getProxyId())) {
			// 发给自己
			deliver.setParentProxyId(getParentProxyId());
		} else {
			// 发给代理
			deliver.setParentProxyId(getProxyId());
		}
		deliver.setCreateUserId(getUserId());
		deliver.setCreateTime(new Date());
		deliver.setUpdateUserId(getUserId());
		deliver.setUpdateTime(new Date());
		deliver.setIsDelete(TrueFalseStatusEnum.FALSE.getValue());
		deliverService.save(deliver);
		log.info("【{}】保存成功", deliver);
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
		Deliver deliver = deliverService.getById(id);
		Assert.notNull(deliver, id + "不存在");
		model.addAttribute("deliver", deliver);
		// 查询发货详情
		Map<String, Object> map = new HashMap<>();
		map.put("deliverId", id);
		List<DeliverDetail> deliverDetails = deliverDetailService.listByMap(map);
		model.addAttribute("deliverDetails", deliverDetails);
		List<Product> products = productService.listProduct();
		model.addAttribute("products", products);
		return "deliver/edit";
	}
	
	/**
	 * 修改数据
	 * 
	 * @param deliver
	 * @return
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> update(Deliver deliver) {
		Assert.notNull(deliver, "修改数据为空");
		Deliver deliverInfo = deliverService.getById(deliver.getId());
		Assert.notNull(deliverInfo, "数据不存在");
		deliver.setUpdateUserId(getUserId());
		deliver.setUpdateTime(new Date());
		deliverService.update(deliver);
		log.info("【{}】修改成功", deliver);
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
		Deliver deliver = deliverService.getById(id);
		Assert.notNull(deliver, "数据不存在");
		deliverService.remove(deliver);
		log.info("【{}】删除成功", deliver);
		return buildSuccess("删除成功");
	}
	
	/**
	 * 查看发货
	 * 
	 * @param model
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/report", method = { RequestMethod.GET })
	public String report(Model model, String ids) {
		Assert.hasLength(ids, "请选择发货单");
		// 获取发货单id
		String[] array = ids.split(",");
		List<Integer> deliverIds = new ArrayList<>(array.length);
		for (String deliverId : array) {
			deliverIds.add(Integer.valueOf(deliverId));
		}
		Map<String, Object> map = new HashMap<>();
		map.put("deliverIds", deliverIds);
		// 待发货单
		List<Deliver> delivers = deliverService.listWaitDeliver(map);
		Assert.notEmpty(delivers, "查看发货单不存在");
		// 发货单详情
		List<DeliverDetail> deliverDetails = deliverDetailService.listByMap(map);
		for (Deliver deliver : delivers) {
			List<DeliverDetail> details = new ArrayList<>();
			for (DeliverDetail deliverDetail : deliverDetails) {
				if (deliver.getId().equals(deliverDetail.getDeliverId())) {
					details.add(deliverDetail);
				}
			}
			deliver.setDeliverDetails(details);
		}
		model.addAttribute("delivers", delivers);
		List<Product> products = productService.listProduct();
		model.addAttribute("products", products);
		model.addAttribute("myWechatName", getUserInfo().getWechatName());
		return "deliver/report";
	}
	
	/**
	 * 发货
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/status", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> deliverStatus(Integer id) {
		Assert.notNull(id, "id为空");
		Deliver deliverInfo = deliverService.getById(id);
		Assert.notNull(deliverInfo, "数据不存在");
		// 修改已发货
		Deliver deliver = new Deliver();
		deliver.setId(id);
		deliver.setDeliverStatus(TrueFalseStatusEnum.TRUE.getValue());
		if (DeliverTypeEnum.SELF_DELIVER.getValue().equals(deliverInfo.getDeliverType())) {
			deliver.setInventoryStatus(TrueFalseStatusEnum.TRUE.getValue());
		}
		deliver.setUpdateUserId(getUserId());
		deliver.setUpdateTime(new Date());
		deliverService.updateDeliverStatus(deliver);
		log.info("【{}】发货成功", deliver);
		return buildSuccess("发货成功");
	}
	
	/**
	 * 撤销发货
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/reset", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> deliverReset(Integer id) {
		Assert.notNull(id, "id为空");
		Deliver deliverInfo = deliverService.getById(id);
		Assert.notNull(deliverInfo, "数据不存在");
		// 撤销发货
		Deliver deliver = new Deliver();
		deliver.setId(id);
		deliver.setDeliverStatus(TrueFalseStatusEnum.FALSE.getValue());
		deliver.setInventoryStatus(TrueFalseStatusEnum.FALSE.getValue());
		deliver.setUpdateUserId(getUserId());
		deliver.setUpdateTime(new Date());
		deliverService.updateDeliverStatus(deliver);
		log.info("【{}】撤销发货成功", deliver);
		return buildSuccess("撤销发货成功");
	}
	
	/**
	 * 对货
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/inventory", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> inventory(String ids) {
		Assert.hasLength(ids, "请选择发货单");
		Map<String, Object> map = new HashMap<>();
		map.put("ids", Arrays.asList(ids.split(",")));
		deliverService.batchInventory(map);
		log.info("【{}】批量对货成功", map);
		return buildSuccess("对货成功");
	}
	
	/**
	 * 进入详情页面
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/detail", method = { RequestMethod.GET })
	public String detail(Model model, Integer id) {
		Assert.notNull(id, "id为空");
		Deliver deliver = deliverService.getById(id);
		Assert.notNull(deliver, "数据不存在");
		model.addAttribute("deliver", deliver);
		
		// 查询发货详情
		Map<String, Object> map = CommonUtils.defaultQueryMap();
		map.put("deliverId", id);
		List<DeliverDetail> deliverDetails = deliverDetailService.listByMap(map);
		model.addAttribute("deliverDetails", deliverDetails);
		List<Product> products = productService.listProduct();
		model.addAttribute("products", products);
		
		// 发货物流信息
		Logistics logistics = logisticsService.getByMap(map);
		if (null != logistics) {
			model.addAttribute("logistics", logistics);
			LogisticsCompany logisticsCompany = logisticsCompanyService.getById(logistics.getCompanyId());
			model.addAttribute("name", logisticsCompany.getName());
		}
		return "deliver/detail";
	}
	
	/**
	 * 导出excel
	 */
	@RequestMapping(value = "/export", method = { RequestMethod.GET })
	public void export() {
		Map<String, Object> map = CommonUtils.defaultQueryMap();
		// 代理id
		String proxyId = request.getParameter("proxyId");
		map.put("proxyId", proxyId); 
		// 代理昵称
		map.put("wechatName", request.getParameter("wechatName"));
		// 产品id
		map.put("productId", request.getParameter("productId")); 
		// 收件人姓名
		map.put("receiveName", request.getParameter("receiveName"));
		// 发货类型
		String deliverType = request.getParameter("deliverType");  
		if (StringUtils.isNotEmpty(deliverType)) {
			if (DeliverTypeEnum.SUPER_DELIVER.getValue().equals(Integer.valueOf(deliverType))) {
				map.put("deliverTypes", Arrays.asList(DeliverTypeEnum.SUPER_DELIVER.getValue(), DeliverTypeEnum.FACTORY_DELIVER.getValue()));
			} else {
				map.put("deliverType", deliverType); 
			}
		}
		// 发货状态
		map.put("deliverStatus", request.getParameter("deliverStatus")); 
		// 对货状态
		map.put("inventoryStatus", request.getParameter("inventoryStatus"));
		// 发货开始时间
		map.put("startDate", request.getParameter("startDate"));
		// 发货结束时间
		map.put("endDate", request.getParameter("endDate"));
		// 发货单id
		map.put("groupBy", "id");
		// 发货时间排序
		map.put("orderBy", "create_time desc");
		List<Deliver> delivers = deliverService.listByMap(map);
		if (CollectionUtils.isEmpty(delivers)) {
			return;
		}

		// 获取发货单id
		List<Integer> deliverIds = new ArrayList<>(delivers.size());
		for (Deliver deliver : delivers) {
			deliverIds.add(deliver.getId());
		}
		// 查询发货单详情
		Map<String, Object> detailMap = new HashMap<>();
		detailMap.put("deliverIds", deliverIds);
		List<DeliverDetail> deliverDetails = deliverDetailService.listByMap(detailMap);
		// 查询快递信息
		List<Logistics> logisticss = logisticsService.listByMap(detailMap);
		for (Deliver deliver : delivers) {
			List<DeliverDetail> details = new ArrayList<>();
			for (DeliverDetail deliverDetail : deliverDetails) {
				if (deliver.getId().equals(deliverDetail.getDeliverId())) {
					details.add(deliverDetail);
				}
			}
			deliver.setDeliverDetails(details);
			for (Logistics logistics : logisticss) {
				if (deliver.getId().equals(logistics.getDeliverId())) {
					deliver.setLogistics(logistics);
					break;
				}
			}
		}

		String wechatName = "";
		if (StringUtils.isNotEmpty(proxyId)) {
			Proxy proxy = proxyService.getById(Integer.valueOf(proxyId));
			Assert.notNull(proxy, "代理不存在");
			wechatName = proxy.getWechatName() + "_";
		}

		// 导出excel
		exportExcel(wechatName, delivers, deliverType);
	}
	
	/**
	 * 表头
	 * 
	 * @param workbook
	 * @param sheet
	 * @param deliverType
	 */
	private void createTitle(HSSFWorkbook workbook, HSSFSheet sheet, String deliverType) {
		// 表头
		HSSFRow row = sheet.createRow(0);
		// 设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
		sheet.setColumnWidth(0, 12 * 256);
		sheet.setColumnWidth(1, 15 * 256);
		sheet.setColumnWidth(2, 15 * 256);
		sheet.setColumnWidth(3, 60 * 256);
		sheet.setColumnWidth(4, 12 * 256);
		sheet.setColumnWidth(5, 50 * 256);
		sheet.setColumnWidth(6, 10 * 256);
		sheet.setColumnWidth(7, 12 * 256);
		sheet.setColumnWidth(8, 16 * 256);
		sheet.setColumnWidth(9, 10 * 256);

		// 设置为居中加粗
		HSSFCellStyle style = workbook.createCellStyle();
		// 居中
		style.setAlignment(HorizontalAlignment.CENTER);
		// 下边框
		style.setBorderBottom(BorderStyle.THIN);
		// 左边框
		style.setBorderLeft(BorderStyle.THIN);
		// 上边框
		style.setBorderTop(BorderStyle.THIN);
		// 右边框
		style.setBorderRight(BorderStyle.THIN);
		// 字体设置
		HSSFFont font = workbook.createFont();
		style.setFont(font);
		// 字体
		font.setFontName("宋体");
		// 字体大小
		font.setFontHeightInPoints((short) 12);
		// 加粗
		font.setBold(true);

		HSSFCell cell;
		cell = row.createCell(0);
		cell.setCellValue("代理昵称");
		cell.setCellStyle(style);

		cell = row.createCell(1);
		cell.setCellValue("收件人姓名");
		cell.setCellStyle(style);
	
		cell = row.createCell(2);
		cell.setCellValue("收件人电话");
		cell.setCellStyle(style);
	
		cell = row.createCell(3);
		cell.setCellValue("收件人地址");
		cell.setCellStyle(style);
		
		cell = row.createCell(4);
		cell.setCellValue("发货时间");
		cell.setCellStyle(style);
		
		cell = row.createCell(5);
		cell.setCellValue("产品明细");
		cell.setCellStyle(style);
		
		cell = row.createCell(6);
		cell.setCellValue("快递费");
		cell.setCellStyle(style);
		
		cell = row.createCell(7);
		cell.setCellValue("物流公司");
		cell.setCellStyle(style);
		
		cell = row.createCell(8);
		cell.setCellValue("物流单号");
		cell.setCellStyle(style);
		
		cell = row.createCell(9);
		cell.setCellValue("物流价格");
		cell.setCellStyle(style);
	}

	/**
	 * 导出excel
	 * 
	 * @param wechatName
	 * @param delivers
	 * @param deliverType
	 */
	public void exportExcel(String wechatName, List<Deliver> delivers, String deliverType) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 页签
		HSSFSheet sheet = workbook.createSheet("发货明细");
		// 表头
		createTitle(workbook, sheet, deliverType);
		
		// 产品信息
		Map<Integer, String> productMap = new HashMap<>();
		List<Product> products = productService.listProduct();
		for (Product product : products) {
			productMap.put(product.getId(), product.getProductName());
		}
		
		// 物流公司信息
		Map<Integer, String> logisticsCompanyMap = new HashMap<>();
		List<LogisticsCompany> logisticsCompanies = logisticsCompanyService.listLogisticsCompany();
		for (LogisticsCompany logisticsCompany : logisticsCompanies) {
			logisticsCompanyMap.put(logisticsCompany.getId(), logisticsCompany.getName());
		}

		// 新增数据行，并且设置单元格数据
		int rowNum = 1;
		Map<Integer, AtomicInteger> productCountMap = new HashMap<>();
		for (Deliver deliver : delivers) {
			HSSFRow row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(deliver.getWechatName());
			row.createCell(1).setCellValue(deliver.getReceiveName());
			row.createCell(2).setCellValue(deliver.getReceivePhone());
			row.createCell(3).setCellValue(deliver.getReceiveAddress());
			row.createCell(4).setCellValue(DateUtils.format(deliver.getCreateTime(), DateUtils.NORM_DATE_PATTERN));
			List<DeliverDetail> deliverDetails = deliver.getDeliverDetails();
			if (null != deliverDetails) {
				StringBuilder details = new StringBuilder();
				for (DeliverDetail deliverDetail : deliverDetails) {
					if (details.length() > 0) {
						details.append("，");
					}
					details.append(productMap.get(deliverDetail.getProductId()) + "(" + deliverDetail.getAmount() + ")");
					AtomicInteger productCount = productCountMap.get(deliverDetail.getProductId());
					if (null == productCount) {
						productCount = new AtomicInteger(0);
						productCountMap.put(deliverDetail.getProductId(), productCount);
					}
					productCount.addAndGet(deliverDetail.getAmount());
				}
				row.createCell(5).setCellValue(details.toString());
			}
			if (deliver.getCourierPrice() > 0) {
				row.createCell(6).setCellValue(deliver.getCourierPrice());
			}
			Logistics logistics = deliver.getLogistics(); 
			if (null != logistics) {
				row.createCell(7).setCellValue(logisticsCompanyMap.get(logistics.getCompanyId()));
				row.createCell(8).setCellValue(logistics.getNumber());
				row.createCell(9).setCellValue(logistics.getPrice()+logistics.getCost());
			}
		}
		
		rowNum++;
		for (Map.Entry<Integer, AtomicInteger> entry : productCountMap.entrySet())  {
			HSSFRow row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(productMap.get(entry.getKey()));
			row.createCell(1).setCellValue(entry.getValue().get());
		}
		
		try (OutputStream out = response.getOutputStream();) {
			String fileName = wechatName + "发货明细_" + DateUtils.format(new Date(), "yyyyMMdd") + ".xls";
			response.setContentType("application/octet-stream");
			response.setHeader("Connection", "close");
			response.setHeader("name", fileName);
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setDateHeader("Expires", 0);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
			// 输出
			workbook.write(out);
		} catch (Exception e) {
			log.error("导出excel失败：", e);
		}
	}
}
