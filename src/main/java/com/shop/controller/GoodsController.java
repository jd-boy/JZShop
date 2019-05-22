package com.shop.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shop.async.EventModel;
import com.shop.async.EventProducer;
import com.shop.async.EventType;
import com.shop.model.Comment;
import com.shop.model.EntityType;
import com.shop.model.Goods;
import com.shop.model.HostHolder;
import com.shop.model.Subtype;
import com.shop.serviceImpl.GoodsServiceImpl;
import com.shop.serviceImpl.GoodsTypeServiceImpl;
import com.shop.serviceImpl.MailServiceImpl;
import com.shop.util.JSONStringUtil;
import com.shop.util.JZShopUtil;

@Controller
public class GoodsController {
	
	@Autowired
	private MailServiceImpl mailServiceImpl ;
	
	@Autowired
	private GoodsServiceImpl goodsServiceImpl;
	
	@Autowired
	private GoodsTypeServiceImpl goodsTypeServiceImpl;
	
	@Autowired
	private HostHolder hostHolder;
	
	@Autowired
	private EventProducer eventProducer;
	
	
	@RequestMapping(value = "/goodsDetail")
	public String goodsDetail(Model model,
							  @RequestParam("ID") int goodsId,
							  @RequestParam("page") int page) {
		
		if(page < 1) {
			return "redirect:/index";
		}
		int num = 10;
		int start = (page-1)*num;
		int count = JZShopUtil.getPageCount(page, num, goodsServiceImpl.getCommentCountByGoodsId(goodsId));
		Goods goods = goodsServiceImpl.getGoodsByID(goodsId);
		ArrayList<Comment> commentList = goodsServiceImpl.getCommentsByGoodsId(goodsId, start, num);
		
		model.addAttribute("goods", goods);
		model.addAttribute("commentList", commentList);
		model.addAttribute("page", page);
		model.addAttribute("count", count);
		
		eventProducer.fireEvent(new EventModel(EventType.LOOK).setEntityId(goods.getID())
				.setEntityType(EntityType.ENTITY_GOODS)
				.setExt("goodsId", String.valueOf(goods.getID()))
				.setExt("goodsHit", String.valueOf(goods.getHit()+1)));
		
		return "goodsDetail";
	}
	
	
	
	
	@RequestMapping(value = "/save_comment")
	@ResponseBody
	public String saveComment(@RequestBody JSONObject jsonParam) {
		
		if(hostHolder.getMember() == null) {
			return JSONStringUtil.getJSONString(1, "请先登录登录");
		}
		
		Comment comment = new Comment();
		comment.setUserId(hostHolder.getMember().getID());
		comment.setGoodsId(jsonParam.getIntValue("goodsId"));
		comment.setOrderId(jsonParam.getString("orderId"));
		comment.setText(jsonParam.get("comment").toString());
		comment.setDate(new Date());
		goodsServiceImpl.addComment(comment);
		mailServiceImpl.addComment(comment.getGoodsId(), comment.getText());
		return JSONStringUtil.getJSONString(0, "评论成功");
	}
	
	@RequestMapping(value = "/searchResult")
	public String searchResult(@RequestParam("name") String name,
							   @RequestParam("page") int page,
							   Model model) {
		
		if(hostHolder.getMember() == null) {
			return "redirect:/login";
		}
		
		if(page < 1) {
			return "redirect:/index";
		}
		
		int num = 36;
		int start = (page-1)*num;
		int count = JZShopUtil.getPageCount(page, num, goodsServiceImpl.getCountByKeyword(name));
		
		model.addAttribute("username", hostHolder.getMember().getUserName());
		model.addAttribute("usertype", hostHolder.getMember().getType());
		model.addAttribute("hotGoods", goodsServiceImpl.getHotGoods());
		model.addAttribute("goodsList", goodsServiceImpl.getGoodsByKeyword(name, start, num));
		model.addAttribute("page", page);
		model.addAttribute("name", name);
		model.addAttribute("count", count);
		
		return "/searchResult";
	}
	
	@RequestMapping(value = "/myStore")
	public String myStore(@RequestParam int page, Model model) {
		if(hostHolder.getMember() == null || hostHolder.getMember().getType() != 1) {
			return "redirect:/login";
		}
		
		if(page < 1) {
			return "redirect:/index";
		}
		int userId = hostHolder.getMember().getID();
		int num = 48;
		int count = JZShopUtil.getPageCount(page, num, goodsServiceImpl.getGoodsNumByStore(userId));
		
		model.addAttribute("hotGoods", goodsServiceImpl.getHotGoodsByStore(userId));
		model.addAttribute("goodsList", goodsServiceImpl.getAllByStore(userId, (page-1)*num, num));
		model.addAttribute("count", count);
		model.addAttribute("page", page);
		return "/myStore";
	}
	
	@RequestMapping(value = "/addGoods")
	public String addGoods(HttpServletRequest request) {
		
		if(hostHolder.getMember() == null || hostHolder.getMember().getType() != 1) {
			return "redirect:/login";
		}
		
		Map<Integer, String> map = new HashMap<Integer, String>();
		
		for(Subtype subtype : goodsTypeServiceImpl.getAllSubtype()) {
			map.put(subtype.getID(), subtype.getTypeName());
		}
		
		request.setAttribute("subtypemap", map);
		request.setAttribute("id", goodsServiceImpl.getMaxId()+1);
		
		return "/addGoods";
	}
	
	@RequestMapping(value = "/insertGoods")
	public String insertGoods(HttpServletRequest request) {
		
		Goods goods = new Goods();
		
		goods.setGoodsName(request.getParameter("goodsname"));
		goods.setIntroduce(request.getParameter("introduce"));
		goods.setPrice(new BigDecimal(request.getParameter("price").toString()));
		goods.setNowPrice(new BigDecimal(request.getParameter("nowprice").toString()));
		goods.setNum(Integer.valueOf(request.getParameter("num")));
		goods.setTypeID(Integer.valueOf(request.getParameter("typename")));
		goods.setPicture(request.getParameter("img"));
		
		if(goods.getNowPrice().equals(new BigDecimal("-1"))) {
			goods.setSale(1);
		}
		
		goodsServiceImpl.addGoods(goods);
		
		return "redirect:/manageGoods";
	}
	
	@RequestMapping(value = "/manageGoods")
	public String manageGoods(HttpServletRequest request) {
		
		return "/manageGoods";
	}
	
	@RequestMapping(value = "/delGoods")
	public String delGoods(@RequestParam("id") int id) {
		
		if(hostHolder.getMember() == null || hostHolder.getMember().getType() != 1) {
			return "redirect:/login";
		}
		
		goodsServiceImpl.setStatus(id, 1);
		
		return "redirect:/manageGoods";
	}
	
	@RequestMapping(value = "/alterGoods")
	public String alterGoods(Model model, @RequestParam("id") int id) {
		
		if(hostHolder.getMember() == null || hostHolder.getMember().getType() != 1) {
			return "redirect:/login";
		}
		

		Map<Integer, String> map = new HashMap<Integer, String>();
		
		for(Subtype subtype : goodsTypeServiceImpl.getAllSubtype()) {
			map.put(subtype.getID(), subtype.getTypeName());
		}
		
		model.addAttribute("subtypemap", map);
		model.addAttribute("goods", goodsServiceImpl.getGoodsByID(id));
		
		return "/alterGoods";
	}
	
	@RequestMapping(value = "/saveGoodsInfor")
	@ResponseBody
	public String alterGoodsInfor(@RequestParam("id") int id, Goods goods) {
		
		if(hostHolder.getMember() == null || hostHolder.getMember().getType() != 1) {
			return "redirect:/login";
		}
		goods.setID(id);
		if(goods.getNowPrice().equals(new BigDecimal("-1"))) {
			goods.setSale(0);
		}
		
		if(goods.getPrice().toString().indexOf('-') != -1) {
			return JSONStringUtil.getJSONString(1, "商品单价需大于等于0，请重新修改！！");
		}
		if(goods.getNowPrice().toString().indexOf('-') != -1) {
			return JSONStringUtil.getJSONString(1, "商品单价需大于等于0，请重新修改！！");
		}
		
		if(goods.getPrice().toString().length()-goods.getPrice().toString().indexOf('.')>3) {
			return JSONStringUtil.getJSONString(1, "商品单价只能保留两位小数，请重新修改！！");
		}
		
		if(goods.getNowPrice().toString().length()-goods.getNowPrice().toString().indexOf('.')>3) {
			return JSONStringUtil.getJSONString(1, "商品折后价只能保留两位小数，请重新修改！！");
		}
		
		if(goods.getNum() < 0 || String.valueOf(goods.getNum()).indexOf('.') != -1) {
			return JSONStringUtil.getJSONString(1, "商品库存只能是正整数，请重新修改！！");
		}
		
		if(goodsServiceImpl.setInfo(goods) == 0) {
			return JSONStringUtil.getJSONString(1, "修改商品信息失败，请重新修改！！");
		} else {
			return JSONStringUtil.getJSONString(0, "修改商品信息成功");
		}
	}
	
}
