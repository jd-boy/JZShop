package com.shop.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shop.model.Cart;
import com.shop.model.Comment;
import com.shop.model.HostHolder;
import com.shop.model.Order;
import com.shop.model.OrderDetail;
import com.shop.serviceImpl.CartServiceImpl;
import com.shop.serviceImpl.GoodsServiceImpl;
import com.shop.serviceImpl.OrderServiceImpl;
import com.shop.util.JSONStringUtil;
import com.shop.util.JZShopUtil;

@Controller
public class OrderController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private OrderServiceImpl orderServiceImpl;
	
	@Autowired
	private GoodsServiceImpl goodsServiceImpl;
	
	@Autowired
	private CartServiceImpl cartServiceImpl;
	
	@Autowired
	private HostHolder hostHolder;
	
	/*
	 * 创建订单
	 */
	@RequestMapping(value = "/createOrder")
	@ResponseBody
	public String cartOrder(@RequestBody JSONObject json) {
		
		if(hostHolder.getMember() == null) {
			return "redirect:/login";
		}
		
		ArrayList<Cart> list = cartServiceImpl.getCartsByUserId(hostHolder.getMember().getID());
		
		String msg = "您选择的商品:\n";
		boolean flag = false;
		
		for(Cart cart : list) {
			int num = goodsServiceImpl.getGoodsNum(cart.getGoodsId());
			if(cart.getNum() > num) {
				msg += cart.getGoodsName()+" 库存为 " + num + "\n";
				flag = true;
			}
		}
		
		if(flag) {
			msg += "库存不足，请重新修改商品数量！！";
			return JSONStringUtil.getJSONString(1, msg);
		}
		
		int goodsTypeNum = 0;
		
		Order order = new Order();
		
		order.setRecevieName(json.getString("recevieName"));
		order.setAddress(json.getString("address"));
		order.setTel(json.getString("tel"));
		order.setBz(json.getString("bz"));
		order.setBnumber(goodsTypeNum);
		
		String orderId = orderServiceImpl.createOrder(order);
		
		if(orderId == null) {
			return JSONStringUtil.getJSONString(1, "创建订单失败");
		} else {
			return JSONStringUtil.getJSONString(0, orderId);
		}
	}
	
	/*
	 * 我的全部订单
	 */
	@RequestMapping(value = "/myOrder")
	public String cartLess(@RequestParam("page") int page, Model model) {
		if(hostHolder.getMember() == null) {
			return "redirect:/login";
		}
		
		String username = hostHolder.getMember().getUserName();
		int num = 5;
		int start = (page-1)*num;
		int count = JZShopUtil.getPageCount(page, num, 
				orderServiceImpl.getOrderDetailCount(username));
		
		ArrayList<OrderDetail> list = orderServiceImpl.getAllOrderDetailByUsername(username, start, num);
		
		Map<String, Map<Integer, String>> map = new HashMap<String, Map<Integer, String>>();
		ArrayList<Comment> commentList = goodsServiceImpl.getCommentsByUserid(hostHolder.getMember().getID());
		for(Comment c : commentList) {
			
			if(map.containsKey(c.getOrderId())) {
				map.get(c.getOrderId()).put(c.getGoodsId(), c.getText());
			} else {
				map.put(c.getOrderId(), new HashMap<Integer, String>());
				map.get(c.getOrderId()).put(c.getGoodsId(), c.getText());
			}
		}
		
		model.addAttribute("orderDetailList", list);
		model.addAttribute("commentMap", map);
		model.addAttribute("page", page);
		model.addAttribute("count", count);
		
		return "/myOrder";
	}
	
	/*
	 * 测试订单是否有效
	 */
	@RequestMapping(value = "/testOrder")
	@ResponseBody
	public String testOrder(@RequestBody JSONObject json) {
		
		if(hostHolder.getMember() == null) {
			return "redirect:/login";
		}
		
		if(orderServiceImpl.getOrder(json.getString("orderId")) == null) {
			return JSONStringUtil.getJSONString(1, "无效订单");
		} else {
			return JSONStringUtil.getJSONString(0, "有效订单");
		}
		
	}
	
	/*
	 * 取消订单
	 */
	@RequestMapping(value = "/cancelOrder")
	@ResponseBody
	public String cancelOrder(@RequestBody JSONObject json) {
		
		if(hostHolder.getMember() == null) {
			return "redirect:/login";
		}
		
		String orderId = json.getString("orderId");
		
		if(orderServiceImpl.getOrder(orderId) == null) {
			return JSONStringUtil.getJSONString(1, "无效订单");
		}
		
		if(orderServiceImpl.confirmOrder(orderId) == 0) {
			return JSONStringUtil.getJSONString(1, "取消订单失败");
		} else {
			return JSONStringUtil.getJSONString(0, "取消订单成功");
		}
		
	}
	
	/*
	 * 确认订单
	 */
	@RequestMapping(value = "/confirmOrder")
	public String confirmOrder(HttpServletRequest request, Model model) {
		
		if(hostHolder.getMember() == null) {
			return "redirect:/login";
		}
		String orderId = request.getParameter("orderId").toString();
		
		Order order = orderServiceImpl.getOrder(orderId);
		if(order == null) {
			return "/myOrder?page=1";
		}
		request.setAttribute("order", order);
		request.setAttribute("orderDetailList", orderServiceImpl.getAllOrderDetail(orderId));
		
		return "/confirmOrder";
	}
	
	/*
	 * 确认发货
	 */
	@RequestMapping(value = "/ship")
	@ResponseBody
	public String ship(@RequestBody JSONObject json) {
		
		String orderId = json.getString("orderId");
		int goodsId = json.getInteger("goodsId");
		String number = json.getString("number").trim();
		if(orderServiceImpl.getOrder(orderId) == null) {
			return JSONStringUtil.getJSONString(1, "无效订单");
		}
		
		if(orderServiceImpl.setTrackingNumber(orderId, goodsId, number) == 0) {
			return JSONStringUtil.getJSONString(1, "添加快递单号失败");
		}
		
		if(orderServiceImpl.setOrderDetailStatus(orderId, goodsId, OrderServiceImpl.UN_RECEIPT) != 0) {
			return JSONStringUtil.getJSONString(0, "修改订单状态成功");
		} else {
			return JSONStringUtil.getJSONString(1, "修改订单状态失败");
		}
	}
	
	/*
	 * 确认收货
	 */
	@RequestMapping(value = "/confirmReceipt")
	@ResponseBody
	public String confirmReceipt(@RequestBody JSONObject json) {
		String orderId = json.getString("orderId");
		int goodsId = json.getInteger("goodsId");
		
		if(orderServiceImpl.setOrderDetailStatus(orderId, goodsId, OrderServiceImpl.FINISH) != 0) {
			return JSONStringUtil.getJSONString(0, "确认收货成功");
		} else {
			return JSONStringUtil.getJSONString(1, "确认收货失败");
		}
	}
	
	/*
	 * 待发货订单
	 */
	@RequestMapping(value = "/handleOrder")
	public String handleOrder(@RequestParam("page") int page, Model model) {
		if(hostHolder.getMember() == null) {
			return "redirect:/login";
		}
		
		int num = 10;
		int start = (page-1)*num;
		int count = JZShopUtil.getPageCount(page, num, 
				orderServiceImpl.getCountOrderDetailBySeller(hostHolder.getMember().getUserName(), OrderServiceImpl.UN_SHIP));
		
		ArrayList<OrderDetail> list = orderServiceImpl.getOrderDetailBySeller(hostHolder.getMember().getUserName(),
													   OrderServiceImpl.UN_SHIP, start, num);
		
		Map<String, String> map = new HashMap<String, String>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		
		String orderId = "";
		for(int i = 0; i < list.size(); i++) {
			orderId = list.get(i).getOrderID();
			if(!map.containsKey(orderId)) {
				map.put(orderId, sdf.format(orderServiceImpl.getOrderDate(orderId)));
			}
		}
		
		model.addAttribute("orderDetailList", list);
		model.addAttribute("map", map);
		model.addAttribute("count", count);
		model.addAttribute("page", page);
		
		return "/handleOrder";
	}
	
	/*
	 * 待确认收货的订单
	 */
	@RequestMapping(value = "/unfinishOrder")
	public String unfinishOrder(@RequestParam("page") int page, Model model) {
		if(hostHolder.getMember() == null) {
			return "redirect:/login";
		}
		
		int num = 10;
		int start = (page-1)*num;
		int count = JZShopUtil.getPageCount(page, num, 
				orderServiceImpl.getCountOrderDetailBySeller(hostHolder.getMember().getUserName(), OrderServiceImpl.UN_RECEIPT));
		
		ArrayList<OrderDetail> list = orderServiceImpl.getOrderDetailBySeller(hostHolder.getMember().getUserName(),
													   OrderServiceImpl.UN_RECEIPT, start, num);
		
		Map<String, String> map = new HashMap<String, String>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		
		String orderId = "";
		for(int i = 0; i < list.size(); i++) {
			orderId = list.get(i).getOrderID();
			if(!map.containsKey(orderId)) {
				map.put(orderId, sdf.format(orderServiceImpl.getOrderDate(orderId)));
			}
		}
		
		model.addAttribute("orderDetailList", list);
		model.addAttribute("map", map);
		model.addAttribute("count", count);
		model.addAttribute("page", page);
		
		return "/unfinishOrder";
	}
	
	
	/*
	 * 已完成订单
	 */
	@RequestMapping(value = "/fulfilOrder")
	public String finishOrder(@RequestParam("page") int page, Model model) {
		if(hostHolder.getMember() == null) {
			return "redirect:/login";
		}
		
		int num = 10;
		int start = (page-1)*num;
		int count = JZShopUtil.getPageCount(page, num, 
				orderServiceImpl.getCountOrderDetailBySeller(hostHolder.getMember().getUserName(), OrderServiceImpl.UN_SHIP));
		
		ArrayList<OrderDetail> list = orderServiceImpl.getOrderDetailBySeller(hostHolder.getMember().getUserName(),
													   OrderServiceImpl.FINISH, start, num);
		
		Map<String, String> map = new HashMap<String, String>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		
		String orderId = "";
		for(int i = 0; i < list.size(); i++) {
			orderId = list.get(i).getOrderID();
			if(!map.containsKey(orderId)) {
				map.put(orderId, sdf.format(orderServiceImpl.getOrderDate(orderId)));
			}
		}
		
		model.addAttribute("orderDetailList", list);
		model.addAttribute("map", map);
		model.addAttribute("count", count);
		model.addAttribute("page", page);
		
		return "/fulfilOrder";
	}
}
