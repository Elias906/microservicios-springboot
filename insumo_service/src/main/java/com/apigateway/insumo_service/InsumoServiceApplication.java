package com.apigateway.insumo_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InsumoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsumoServiceApplication.class, args);
	}

}
