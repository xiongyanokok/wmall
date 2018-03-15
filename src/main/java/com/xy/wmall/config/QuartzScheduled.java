package com.xy.wmall.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.xy.wmall.service.BackupService;

import lombok.extern.slf4j.Slf4j;

/**
 * 定时任务
 * 
 * @author xiongyan
 * @date 2017年11月28日 下午5:23:21
 */
@Configuration
@EnableScheduling
@Slf4j
public class QuartzScheduled {
	
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
			log.error("自动备份数据库失败：", e);
		}
    }
}
