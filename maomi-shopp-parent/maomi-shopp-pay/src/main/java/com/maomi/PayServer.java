package com.maomi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.maomi.mapper")
public class PayServer {

	public static void main(String[] args) {
		SpringApplication.run(PayServer.class, args);
	}
}
