package com.xy.wmall.common.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.springframework.util.StreamUtils;

import com.xy.wmall.common.Constant;

/**
 * HttpClientUtils
 * 
 * @author xiongyan
 * @date 2018年1月18日 下午4:01:57
 */
public class HttpClientUtils {
	
	private CloseableHttpAsyncClient httpClient = null;

	/**
	 * 初始化HttpAsyncClients
	 */
	private HttpClientUtils() {
		httpClient = HttpAsyncClients.createDefault();
		httpClient.start();
	}
	
	/**
	 * 内部类
	 */
	private static class SingletonHolder {
		
		private SingletonHolder() {
		}
		
		/**
		 * 初始化对象
		 */
        private static final HttpClientUtils INSTANCE = new HttpClientUtils();
    }

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static final HttpClientUtils getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	/**
	 * 关闭 HttpAsyncClients
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		if (null != httpClient) {
			httpClient.close();
			httpClient = null;
		}
	}
	
	/**
	 * get请求
	 * 
	 * @param url
	 * @return
	 */
	public String get(String url) {
		try {
			HttpGet request = new HttpGet(url);
			HttpResponse response = httpClient.execute(request, null).get(10, TimeUnit.SECONDS);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				return StreamUtils.copyToString(response.getEntity().getContent(), Charset.forName(Constant.CHARSETNAME));
			}
		} catch (Exception e) {
			// 
		}
		return null;
	}
}
