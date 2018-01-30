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
import com.xy.wmall.enums.TrueFalseStatusEnum;
import com.xy.wmall.model.Memo;
import com.xy.wmall.service.MemoService;

/**
 * Controller
 * 
 * @author admin
 * @date 2017年10月28日 上午08:54:08
 */
@Controller
@RequestMapping(value = "/admin/memo", produces = { "application/json; charset=UTF-8" })
public class MemoController extends BaseController {

	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(MemoController.class);

    @Autowired
	private MemoService memoService;
	
	/**
	 * 进入列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model) {
		return "memo/list";
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
			// 标题
			map.put("title", request.getParameter("title")); 
			// 内容
			map.put("content", request.getParameter("content"));
			// 状态
			map.put("status", request.getParameter("status")); 
			return memoService.listMemo(map);
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
		return "memo/add";
	}
	
	/**
	 * 保存数据
	 * 
	 * @param memo
	 * @return
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> save(Memo memo) {
		Assert.notNull(memo, "保存数据为空");
		memo.setCreateUserId(getUserId());
		memo.setCreateTime(new Date());
		memo.setUpdateUserId(getUserId());
		memo.setUpdateTime(new Date());
		memo.setIsDelete(TrueFalseStatusEnum.FALSE.getValue());
		memoService.save(memo);
		logger.info("【{}】保存成功", memo);
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
		Memo memo = memoService.getMemoById(id);
		Assert.notNull(memo, "数据不存在");
		model.addAttribute("memo", memo);
		return "memo/edit";
	}
	
	/**
	 * 修改数据
	 * 
	 * @param memo
	 * @return
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> update(Memo memo) {
		Assert.notNull(memo, "修改数据为空");
		Memo memoInfo = memoService.getMemoById(memo.getId());
		Assert.notNull(memoInfo, "数据不存在");
		memo.setUpdateUserId(getUserId());
		memo.setUpdateTime(new Date());
		memoService.update(memo);
		logger.info("【{}】修改成功", memo);
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
		Memo memo = memoService.getMemoById(id);
		Assert.notNull(memo, "数据不存在");
		memoService.remove(memo);
		logger.info("【{}】删除成功", memo);
		return buildSuccess("删除成功");
	}
	
}
