package com.maomi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.maomi.mapper")
public class OrderServer {

	public static void main(String[] args) {
		SpringApplication.run(OrderServer.class, args);
	}
}
