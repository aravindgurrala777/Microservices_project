package com.project.task.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

	@Value("${app.rest.connect-timeout:2000}")
	private int connectTimeout;
	
	@Value("${app.rest.read-timeout:3000}")
	private int readTimeout;
	
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		
		
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(connectTimeout);
		factory.setReadTimeout(readTimeout);
		
		
		System.out.println("RestTemplate created with connectTimeout: " + connectTimeout + " ms, readTimeout: " + readTimeout + " ms");
		return new RestTemplate(factory);
	}
	
}
