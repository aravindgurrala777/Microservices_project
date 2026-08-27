package com.project.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceMicroprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceMicroprojectApplication.class, args);
	}

}
