package com.xy.wmall.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.xy.wmall.service.BackupService;

/**
 * 定时任务
 * 
 * @author xiongyan
 * @date 2017年11月28日 下午5:23:21
 */
@Configuration
@EnableScheduling
public class QuartzScheduled {
	
	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(QuartzScheduled.class);

	@Autowired
	private BackupService backupService;
	
	/**
	 * 每天22点执行一次
	 */
	@Scheduled(cron = "0 0 22 * * ?")
    public void backup() {
		try {
			backupService.backup();
		} catch (Exception e) {
			logger.error("自动备份数据库失败：", e);
		}
    }
}
