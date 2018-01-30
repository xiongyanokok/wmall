package com.xy.wmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

/**
 * 启动服务
 * 
 * @author xiongyan
 * @date 2017年10月27日 上午9:54:50
 */
@ServletComponentScan
@SpringBootApplication
public class Application {
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return (ConfigurableEmbeddedServletContainer container) -> container.setSessionTimeout(86400);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
