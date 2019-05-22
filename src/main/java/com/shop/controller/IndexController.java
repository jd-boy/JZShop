package com.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.model.HostHolder;
import com.shop.serviceImpl.GoodsServiceImpl;

@Controller
public class IndexController {
	
	@Autowired
	private HostHolder hostHolder;
	
	@Autowired
	private GoodsServiceImpl goodsServiceImpl;
	
	@RequestMapping(path = {"/index", "/"})
	public String index(Model model) {
		
		if(hostHolder.getMember() != null) {
			model.addAttribute("username", hostHolder.getMember().getUserName());
			model.addAttribute("usertype", hostHolder.getMember().getType());
		}
		model.addAttribute("hotGoods", goodsServiceImpl.getHotGoods());
		model.addAttribute("newGoods", goodsServiceImpl.getNewGoods());
		model.addAttribute("discountGoods", goodsServiceImpl.getDiscountGoods());
		return "/index";
	}
	
	@RequestMapping(path = {"/goodsList"})
	public String goodsList(Model model, @RequestParam("type") int typeID) {
		
		if(hostHolder.getMember() != null) {
			model.addAttribute("username", hostHolder.getMember().getUserName());
			model.addAttribute("usertype", hostHolder.getMember().getType());
		}
		model.addAttribute("hotGoods", goodsServiceImpl.getHotGoodsByType(typeID));
		model.addAttribute("newGoods", goodsServiceImpl.getNewGoodsByType(typeID));
		model.addAttribute("discountGoods", goodsServiceImpl.getDiscountGoodsByType(typeID));
		return "/index";
	}

	@RequestMapping(path = "pay_fulfil")
	public String payFulfil() {
		return "pay_fulfil";
	}
}
