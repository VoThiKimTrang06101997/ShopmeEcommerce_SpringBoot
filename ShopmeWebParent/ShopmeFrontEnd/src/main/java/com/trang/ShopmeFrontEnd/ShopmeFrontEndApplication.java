package com.trang.ShopmeFrontEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EntityScan({ "com.trang.ShopmeCommon.entity", "com.trang.ShopmeBackEnd_Admin.user" })
public class ShopmeFrontEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopmeFrontEndApplication.class, args);
	}

}
