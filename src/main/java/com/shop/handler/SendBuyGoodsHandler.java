package com.shop.handler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shop.async.EventHandler;
import com.shop.async.EventModel;
import com.shop.async.EventType;
import com.shop.model.Order;
import com.shop.serviceImpl.OrderServiceImpl;
import com.shop.util.MyMailSender;

@Component
public class SendBuyGoodsHandler implements EventHandler {
	
	@Autowired
	private MyMailSender mailSender;
	
	@Override
	public void doHandle(EventModel model) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seller", model.getExt("seller"));
		map.put("tel", model.getExt("tel"));
		map.put("recevieName", model.getExt("recevieName"));
		map.put("address", model.getExt("address"));
		map.put("bz", model.getExt("bz"));
		map.put("orderId", model.getExt("orderId"));
		map.put("goodsName", model.getExt("goodsName"));
		map.put("goodsNum", model.getExt("goodsNum"));
		
		mailSender.sendWithHTMLTemplate(model.getExt("email"), "商品购买通知", "mail/buyGoods.html", map);
	}

	@Override
	public List<EventType> getSupportEventTypes() {
		return Arrays.asList(EventType.BUY_GOODS);
	}

}
