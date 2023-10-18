package com.vizzibl.velocityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class VelocityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VelocityServiceApplication.class, args);
	}

}
