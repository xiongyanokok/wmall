package com.xy.wmall.common;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 线程池
 * 
 * @author xiongyan
 * @date 2017年8月29日 上午11:39:06
 */
public class ThreadPoolContext {
	
	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ThreadPoolContext.class);
	
	/**
	 * 核心线程数
	 */
	private static Integer corePoolSize = 5;
	
	/**
	 * 最大线程数
	 */
	private static Integer maxPoolSize = 10;
	
	/**
	 * 队列容量
	 */
	private static Integer capacity = 100;
	
	/**
	 * 线程池
	 */
	private static ListeningExecutorService executorService;
	
	/**
	 * 私有构造方法
	 */
	private ThreadPoolContext() {
		
	}

	
	/**
	 * 初始化线程池
	 */
	public static void init() {
		ExecutorService threadPool = new ThreadPoolExecutor(
				corePoolSize, 
				maxPoolSize, 
				0L, 
				TimeUnit.MILLISECONDS, 
				new ArrayBlockingQueue<Runnable>(capacity),
				new ThreadFactoryBuilder().setNameFormat("thread-pool-%d").build(), 
				new ThreadPoolExecutor.AbortPolicy());
		executorService = MoreExecutors.listeningDecorator(threadPool);
	}
	
	/**
	 * 销毁线程池
	 */
	public static void close() {
		if (null != executorService) {
			executorService.shutdown();
			executorService = null;
		}
	}
	
	/**
	 * 异步执行
	 * 
	 * @param callable
	 * @return
	 */
	public static <T> ListenableFuture<T> submit(Callable<T> callable) {
		return executorService.submit(callable);
	}

	/**
	 * 异步执行
	 * 
	 * @param runnable
	 */
	public static void execute(Runnable runnable) {
		executorService.execute(runnable);
	}
	
	/**
	 * 获取异步执行结果
	 * 
	 * @param future
	 * @return
	 */
	public static <T> T getResult(Future<T> future) {
		try {
			return future.get();
		} catch (Exception e) {
			logger.error("异步查询失败", e);
			return null;
		}
	}
	
	/**
	 * 获取异步执行结果
	 * 
	 * @param future
	 * @param timeout
	 * @return
	 */
	public static <T> T getResult(Future<T> future, int timeout) {
		try {
			return future.get(timeout, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			logger.error("异步查询失败", e);
			return null;
		}
	}
	
}
