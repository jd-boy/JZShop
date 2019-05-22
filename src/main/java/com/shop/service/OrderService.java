package com.shop.service;

import java.util.ArrayList;
import java.util.Date;

import com.shop.model.Order;
import com.shop.model.OrderDetail;

public interface OrderService {
	
	//插入一条订单主表
	public int insterOrder(Order order);
	
	//返回用户全部主订单
	public ArrayList<Order> getAllOrder(String username);
	
	//返回单个订单主表
	public Order getOrder(String orderID);
	
	//设置收件人
	public int setRecevieName(String orderID, String recevieName);
	
	//设置收货地址
	public int setAddress(String orderID, String address);
	
	//设置联系电话
	public int setTel(String orderID, String tel);
	
	//修改订单主表的支付状态
	public int setStatus(String orderID, int status);
	
	//插入一条详细订单记录
	public int insterOrderDetail(OrderDetail orderDetail);
	
	//返回单个订单详细记录
	public Order getOrderDetailById(int ID);
	
	//返回订单的全部详细信息
	public ArrayList<OrderDetail> getAllOrderDetail(String orderID);
	
	//返回用户的全部的详细信息
	public ArrayList<OrderDetail> getAllOrderDetailByUsername(String username, int start, int num);
	
	//生成订单号
	public String createOrderId();
	
	//生成订单
	public String createOrder(Order order);
	
	//查询用户的全部订单号
	public ArrayList<String> getAllOrderId(String username);
	
	//返回某用户的详细订单个数
	public int getOrderDetailCount(String username);
	
	//返回订单支付状态
	public int getOrderStatus(String orderID);
	
	//设置收货地址
	public int setFreezeOrder(String orderID, int freeze);
	
	//修改详细订单状态
	public int setOrderDetailFreeze(String orderID, int status);
	
	//取消订单
	public int confirmOrder(String orderID);
	
	//查询某一订单号中所有商品名
	public ArrayList<String> getAllGoodsName(String orderID);
	
	//查询某一订单号中所有商品Id
	public ArrayList<Integer> getAllGoodsIdByOrderId(String orderID);
	
	//查询某商家某种订单状态的订单详情
	public ArrayList<OrderDetail> getOrderDetailBySeller(String sellerName, int status, int start, int num);
	
	//查询某商家某种订单状态的数量
	public int getCountOrderDetailBySeller(String sellerName, int status);
	
	//修改某商品的订单状态
	public int setOrderDetailStatus(String orderID, int goodsID, int status);
	
	//修改某商品的快递单号
	public int setTrackingNumber(String orderID, int goodsID, String trackingNumber);
	
	//返回单个订单主表创建时间
	public Date getOrderDate(String orderID);
}
