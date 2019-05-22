package com.shop.dao;

import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shop.model.Order;

@Mapper
public interface OrderDAO {

	String TABLE_NAME = " tb_order ";
	String INSERT_FIELDS = " orderID,bnumber,username,recevieName,address,tel,orderDate,bz,sumPrice,status,freeze ";
	String SELECT_FIELDS = INSERT_FIELDS;
	
	//插入一条订单主表
	@Insert({"INSERT INTO ", TABLE_NAME, "(", INSERT_FIELDS, ") VALUES (#{orderID},#{bnumber},#{username},",
		"#{recevieName},#{address},#{tel},#{orderDate},#{bz},#{sumPrice},#{status},#{freeze})"})
	public int insterOrder(Order order);
	
	//返回单个订单主表
	@Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE orderID=#{orderID} ", 
		"AND freeze=0"})
	public Order getOrder(String orderID);
	
	//返回用户全部订单
	@Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE username=#{username} ", 
	"AND freeze=0 ORDER BY orderDate DESC"})
	public ArrayList<Order> getAllOrder(String username);
	
	//设置收件人
	@Update({"UPDATE ", TABLE_NAME, " SET recevieName=#{recevieName} WHERE orderID=#{orderID} AND freeze=0"})
	public int setRecevieName(@Param("orderID") String orderID, @Param("recevieName") String recevieName);
	
	//设置收货地址
	@Update({"UPDATE ", TABLE_NAME, " SET address=#{address} WHERE orderID=#{orderID} AND freeze=0"})
	public int setAddress(@Param("orderID") String orderID, @Param("address") String address);
	
	//设置联系电话
	@Update({"UPDATE ", TABLE_NAME, " SET tel=#{tel} WHERE orderID=#{orderID} AND freeze=0"})
	public int setTel(@Param("orderID") String orderID, @Param("tel") String tel);
	
	//查询用户的全部订单号
	@Select({"SELECT orderID FROM ", TABLE_NAME, " WHERE username=#{username} AND freeze=0 ",
			"ORDER BY orderDate DESC"})
	public ArrayList<String> getAllOrderId(String username);
	
	//返回单个订单主表创建时间
	@Select({"SELECT orderDate FROM ", TABLE_NAME, " WHERE orderID=#{orderID} AND freeze=0"})
	public Date getOrderDate(String orderID);
	
	//返回订单支付状态
	@Select({"SELECT status FROM ", TABLE_NAME, " WHERE orderID=#{orderID} AND freeze=0"})
	public int getStatus(String orderID);
	
	//修改订单主表的支付状态
	@Update({"UPDATE ", TABLE_NAME, " SET status=#{status} WHERE orderID=#{orderID} AND freeze=0"})
	public int setStatus(@Param("orderID") String orderID, @Param("status") int status);
	
	//设置收货地址
	@Update({"UPDATE ", TABLE_NAME, " SET freeze=#{freeze} WHERE orderID=#{orderID}"})
	public int setFreezeOrder(@Param("orderID") String orderID, @Param("freeze") int freeze);
	
}
