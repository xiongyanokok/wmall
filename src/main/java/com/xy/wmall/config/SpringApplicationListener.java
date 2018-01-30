package com.xy.wmall.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.xy.wmall.common.ThreadPoolContext;
import com.xy.wmall.common.WmallCache;
import com.xy.wmall.common.utils.HttpClientUtils;
import com.xy.wmall.enums.TrueFalseStatusEnum;
import com.xy.wmall.model.Price;
import com.xy.wmall.service.PriceService;

/**
 * 服务器启动初始化数据
 * 
 * @author xiongyan
 * @date 2017年10月27日 下午5:36:05
 */
@Component
public class SpringApplicationListener implements ApplicationListener<ContextRefreshedEvent>, DisposableBean {
	
	@Autowired
	private PriceService priceService;
	
	/**
	 * 当spring容器初始化完成后执行该方法
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// 初始化线程池
		ThreadPoolContext.init();
		// 异步执行
		ThreadPoolContext.execute(this::priceCache);
		// 初始化httpclient
		HttpClientUtils.init();
	}
	
	/**
	 * 产品价格 缓存
	 */
	private void priceCache() {
		Map<String, Object> map = new HashMap<>(1);
		map.put("isDelete", TrueFalseStatusEnum.FALSE.getValue());
		List<Price> prices = priceService.listPrice(map);
		if (CollectionUtils.isNotEmpty(prices)) {
			for (Price price : prices) {
				WmallCache.putPrice(price);
			}
		}
	}

	/**
	 * 销毁
	 */
	@Override
	public void destroy() throws Exception {
		// 销毁线程池
		ThreadPoolContext.close();
		// 关闭httpclient
		HttpClientUtils.close();
	}

}
