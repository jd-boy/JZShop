package com.shop.serviceImpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.dao.PaySerialDAO;
import com.shop.model.PaySerial;
import com.shop.service.PaySerialService;

@Service
public class PaySerialServiceImpl implements PaySerialService {

	@Autowired
	private PaySerialDAO paySerialDAO;
	
	@Override
	public int addPaySerial(PaySerial paySerial) {
		return paySerialDAO.addPaySerial(paySerial);
	}

	@Override
	public PaySerial getPaySerialById(int orderId) {
		return paySerialDAO.getPaySerialById(orderId);
	}

	@Override
	public ArrayList<PaySerial> getAllPaySerial() {
		return paySerialDAO.getAllPaySerial();
	}

}
