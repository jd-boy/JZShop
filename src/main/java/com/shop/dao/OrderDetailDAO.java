package com.shop.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shop.model.Order;
import com.shop.model.OrderDetail;

@Mapper
public interface OrderDetailDAO {
	
	String TABLE_NAME = " tb_order_detail ";
	String INSERT_FIELDS = " orderID,sellerName,goodsID,goodsName,trackingNumber,goodsPicture,price,number,status,freeze ";
	String SELECT_FIELDS = "ID," + INSERT_FIELDS;
	
	//插入一条详细订单记录
	@Insert({"INSERT INTO ", TABLE_NAME, "(", INSERT_FIELDS, ") VALUES (#{orderID},#{sellerName},#{goodsID},",
		"#{goodsName},#{trackingNumber},#{goodsPicture},#{price},#{number},#{status},#{freeze})"})
	public int insterOrderDetail(OrderDetail orderDetail);
	
	//返回单个订单详细记录
	@Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE ID=#{ID} AND freeze=0"})
	public Order getOrderDetailById(int ID);
	
	//返回订单的全部详细信息
	@Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE orderID=#{orderID} AND freeze=0"})
	public ArrayList<OrderDetail> getAllOrderDetail(String orderID);
	
	//查询某一订单号中所有商品名
	@Select({"SELECT goodsName FROM ", TABLE_NAME, " WHERE orderID=#{orderID} AND freeze=0"})
	public ArrayList<String> getAllGoodsName(String orderID);
	
	//查询某一订单号中所有商品Id
	@Select({"SELECT goodsID FROM ", TABLE_NAME, " WHERE orderID=#{orderID} AND freeze=0"})
	public ArrayList<Integer> getAllGoodsIdByOrderId(String orderID);
	
	//返回某用户的详细订单个数
	@Select({"SELECT SUM(bnumber) FROM tb_order WHERE username=#{username} AND freeze=0"})
	public int getOrderDetailCount(String username);
	
	//修改详细订单是否有效
	@Update({"UPDATE ", TABLE_NAME, " SET freeze=#{freeze} WHERE orderID=#{orderID}"})
	public int setOrderDetailFreeze(@Param("orderID") String orderID, @Param("freeze") int freeze);

	//查询某商家某种订单状态的订单详情
	@Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, 
			 "WHERE sellerName=#{sellerName} AND freeze=0 AND status=#{status} ",
			 "ORDER BY orderID DESC ",
			 "limit #{start},#{num}"})
	public ArrayList<OrderDetail> getOrderDetailBySeller(@Param("sellerName") String sellerName,
														 @Param("status") int status,
														 @Param("start") int start,
														 @Param("num") int num);
	
	//查询某商家某种订单状态的数量
	@Select({"SELECT COUNT(ID) FROM ", TABLE_NAME, 
			 "WHERE sellerName=#{sellerName} AND freeze=0 AND status=#{status}"})
	public int getCountOrderDetailBySeller(@Param("sellerName") String sellerName, @Param("status") int status);
	
	//修改某商品的订单状态
	@Update({"UPDATE ", TABLE_NAME, " SET status=#{status} ",
		" WHERE orderID=#{orderID} AND goodsID=#{goodsID}"})
	public int setOrderDetailStatus(@Param("orderID") String orderID, 
									@Param("goodsID") int goodsID, 
									@Param("status") int status);
	
	//修改某商品的快递单号
	@Update({"UPDATE ", TABLE_NAME, " SET trackingNumber=#{trackingNumber} ",
	" WHERE orderID=#{orderID} AND goodsID=#{goodsID}"})
	public int setTrackingNumber(@Param("orderID") String orderID, 
			@Param("goodsID") int goodsID, 
			@Param("trackingNumber") String trackingNumber);
	
}
