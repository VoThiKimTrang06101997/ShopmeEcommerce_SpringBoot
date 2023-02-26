package com.trang.ShopmeBackEnd_Admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration
@EntityScan({ "com.trang.ShopmeCommon.entity", "com.trang.ShopmeBackEnd_Admin.user" })
public class ShopmeBackEndApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShopmeBackEndApplication.class, args);

	}

}
