package com.xy.wmall.service.impl;

import java.io.File;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.druid.pool.DruidDataSource;
import com.xy.wmall.common.utils.DateUtils;
import com.xy.wmall.exception.WmallException;
import com.xy.wmall.service.BackupService;

/**
 * 数据库备份
 * 
 * @author xiongyan
 * @date 2017年11月28日 下午4:05:48
 */
@Service
public class BackupServiceImpl implements BackupService {

	/**
	 * 数据源
	 */
	@Autowired
	private DruidDataSource dataSource;

	/**
	 * 本地存放地址
	 */
	@Value("${local.path}")
	private String localPath;

	/**
	 * 备份
	 */
	@Override
	public Boolean backup() {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			Map<String, Object> map = jdbcTemplate.queryForMap("show global variables like 'basedir'");
			// 数据库安装路径
			String path = map.get("Value").toString();
			// url
			String url = dataSource.getUrl().substring(0, dataSource.getUrl().indexOf('?'));
			// host
			String host = url.substring(url.indexOf("//") + 2, url.lastIndexOf(':'));
			// username
			String username = dataSource.getUsername();
			// password
			String password = dataSource.getPassword();
			// db
			String db = url.substring(url.lastIndexOf('/') + 1);
			// 文件夹是否存在，不存在创建文件夹
			File file = new File(localPath);
			if (!file.exists()) {
				file.mkdir();
			}
			// 备份文件保存路径
			String sqlFile = localPath + db + "_" + DateUtils.format(new Date(), "yyyyMMdd") + ".sql";
			StringBuilder mysqldump = new StringBuilder(path).append("bin/mysqldump --opt").append(" -h").append(host).append(" --user=").append(username).append(" --password=").append(password)
					.append(" --lock-all-tables=true").append(" --result-file=").append(sqlFile).append(" --default-character-set=utf8 ").append(db);

			// 执行命令
			Process process = Runtime.getRuntime().exec(mysqldump.toString());
			return (null != process && process.waitFor() == 0);
		} catch (Exception e) {
			throw new WmallException("数据库备份失败", e);
		}
	}

}
