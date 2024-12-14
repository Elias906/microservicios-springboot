package com.apigateway.api_gateway2;

import com.apigateway.api_gateway2.prueba.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;


@SpringBootApplication
//#@EnableConfigServer
public class ApiGateway2Application {

	public static void main(String[] args) {

		SpringApplication.run(ApiGateway2Application.class, args);
	}

}
