package com.devsu.CustomerPersonService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CustomerPersonServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerPersonServiceApplication.class, args);
	}
}
