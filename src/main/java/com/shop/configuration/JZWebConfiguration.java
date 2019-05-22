package com.shop.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.shop.interceptor.CartInterceptor;
import com.shop.interceptor.ManageGoodsInterceptor;
import com.shop.interceptor.ModifyMemberInterceptor;
import com.shop.interceptor.PassportInterceptor;

@Component
public class JZWebConfiguration implements WebMvcConfigurer {
	
	@Autowired
	PassportInterceptor passportInterceptor;
	
	@Autowired
	PassportInterceptor loginRequiredInterceptor;
	
	@Autowired
	CartInterceptor cartInterceptor;
	
	@Autowired
	ManageGoodsInterceptor manageGoodsInterceptor;
	
	@Autowired
	ModifyMemberInterceptor modifyMemberInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(passportInterceptor);
		//registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("");
		registry.addInterceptor(cartInterceptor).addPathPatterns("/cart_see");
		registry.addInterceptor(manageGoodsInterceptor).addPathPatterns("/manageGoods");
		registry.addInterceptor(modifyMemberInterceptor).addPathPatterns("/modifyMember");
		WebMvcConfigurer.super.addInterceptors(registry);
	}
	
}
