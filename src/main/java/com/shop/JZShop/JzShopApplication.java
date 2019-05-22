package com.shop.JZShop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.shop.util.SpringUtil;

@MapperScan("com.shop.dao")
@ServletComponentScan("com.shop.servlet")
@ComponentScan({"com.shop.serviceImpl","com.shop.controller", "com.shop.model",
	"com.shop.interceptor", "com.shop.configuration", "com.shop.interceptor",
	"com.shop.aspect","com.shop.async","com.shop.handler","com.shop.util"})
@Import(SpringUtil.class)
@SpringBootApplication
public class JzShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JzShopApplication.class, args);
	}
}
