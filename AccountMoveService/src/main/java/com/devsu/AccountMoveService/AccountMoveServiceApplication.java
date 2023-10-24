package com.devsu.AccountMoveService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AccountMoveServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountMoveServiceApplication.class, args);
	}
}
