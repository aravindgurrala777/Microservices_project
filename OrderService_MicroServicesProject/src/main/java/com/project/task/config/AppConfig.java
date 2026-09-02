package com.project.task.config;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
		
		RestTemplate restTemplate = new RestTemplate(factory);
		
		restTemplate.getInterceptors().add((request, body, execution) -> {

			var auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth instanceof JwtAuthenticationToken jwtAuth) {
                String token = jwtAuth.getToken().getTokenValue();
                request.getHeaders().add("Authorization", "Bearer " + token);
            }


            String correlationId = MDC.get("correlationId");
            if (correlationId == null) {
                correlationId = MDC.get("X-Correlation-ID");
            }
            if (correlationId != null) {
                request.getHeaders().add("X-Correlation-ID", correlationId);
            }

            return execution.execute(request, body);
        });
		
		
		System.out.println("RestTemplate created with connectTimeout: " + connectTimeout + " ms, readTimeout: " + readTimeout + " ms");
		
		return restTemplate;
	}
}