package com.xy.wmall.config;

import java.util.Properties;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.PageHelper;

/**
 * mybatis配置
 * 
 * @author xiongyan
 * @date 2017年10月27日 上午11:28:40
 */
@Configuration
@MapperScan("com.xy.wmall.mapper")
public class MyBatisConfiguration {
	
	/**
	 * 分页
	 * 
	 * @return
	 */
	@Bean
	public PageHelper pageHelper() {
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("dialect", "mysql");
		properties.setProperty("reasonable", "false");
		pageHelper.setProperties(properties);
		return pageHelper;
	}
	
}
