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
public class SendCheckCodeHandler implements EventHandler {
	
	@Autowired
	private MyMailSender mailSender;

	@Override
	public void doHandle(EventModel model) {
		Map<String, Object> map = new HashMap();
        map.put("username", model.getExt("username"));
        map.put("checkcode", model.getExt("checkcode"));
		mailSender.sendWithHTMLTemplate(model.getExt("to").toString(), "修改账户信息", "mail/SendCheckCode.html", map);
	}

	@Override
	public List<EventType> getSupportEventTypes() {
		return Arrays.asList(EventType.CHECK);
	}

}
