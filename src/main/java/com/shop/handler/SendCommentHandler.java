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
import com.shop.util.MyMailSender;

@Component
public class SendCommentHandler implements EventHandler {
	
	@Autowired
	private MyMailSender mailSender;

	@Override
	public void doHandle(EventModel model) {
		Map<String, Object> map = new HashMap();
        map.put("seller", model.getExt("seller").toString());
        map.put("goods", model.getExt("goods").toString());
        map.put("comment", model.getExt("comment").toString());
		mailSender.sendWithHTMLTemplate(model.getExt("to").toString(), "商品评论通知", "mail/comment.html", map);
		
	}

	@Override
	public List<EventType> getSupportEventTypes() {
		return Arrays.asList(EventType.COMMENT);
	}

}
