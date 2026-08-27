package com.project.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerMicroservicesApplication.class, args);
	}

}
