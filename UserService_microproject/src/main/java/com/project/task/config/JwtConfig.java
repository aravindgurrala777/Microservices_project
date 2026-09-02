package com.project.task.config;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class JwtConfig {

	
	@Value("${spring.security.oauth2.resourceserver.jwt.secret-key}")
	private String secretKey;
	
	
	
	@Bean
	public JwtDecoder jwtDecoder() {
		
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
		
		return NimbusJwtDecoder.withSecretKey(secretKeySpec).build();
	}
}
