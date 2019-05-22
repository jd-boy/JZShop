package com.shop.service;

import java.util.ArrayList;

import com.shop.model.PaySerial;

public interface PaySerialService {
	
	//插入一条订单流水
	public int addPaySerial(PaySerial paySerial);
	
	//根据订单号查询订单流水
	public PaySerial getPaySerialById(int orderId);
	
	//查询全部订单流水
	public ArrayList<PaySerial> getAllPaySerial();
}
