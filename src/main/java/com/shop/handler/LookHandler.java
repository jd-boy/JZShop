package com.shop.handler;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shop.async.EventHandler;
import com.shop.async.EventModel;
import com.shop.async.EventType;
import com.shop.serviceImpl.GoodsServiceImpl;

@Component
public class LookHandler implements EventHandler {
	
	@Autowired
	private GoodsServiceImpl goodsServiceImpl;

	@Override
	public void doHandle(EventModel model) {
		synchronized (this) {
			goodsServiceImpl.updateHit(Integer.valueOf(model.getExt("goodsId")), Integer.valueOf(model.getExt("goodsHit")));
		}
	}

	@Override
	public List<EventType> getSupportEventTypes() {
		return Arrays.asList(EventType.LOOK);
	}
	
}
