package com.shop.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.shop.model.PaySerial;

public interface PaySerialDAO {
	String TABLE_NAME = " tb_pay_serial ";
	String INSERT_FIELDS = " orderId,price,zhifubaoId,createDate ";
	String SELECT_FIELDS = "id," + INSERT_FIELDS;
	
	//插入一条订单流水
	@Insert({"INSERT INTO ", TABLE_NAME, "(", INSERT_FIELDS, ") VALUES (",
		"#{orderId},#{price},#{zhifubaoId},#{createDate})"})
	public int addPaySerial(PaySerial paySerial);
	
	//根据订单号查询订单流水
	@Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME,
			 " WHERE orderId=#{orderId} ORDER BY createDate DESC"})
	public PaySerial getPaySerialById(int orderId);
	
	//查询全部订单流水
	@Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " ORDER BY createDate DESC"})
	public ArrayList<PaySerial> getAllPaySerial();
}
