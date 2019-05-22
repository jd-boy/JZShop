package com.shop.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.shop.util.JedisAdapter;
import com.shop.util.RedisKeyUtil;


@Service
public class EventProducer {
	
    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);

    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel) {
        try {
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
            return true;
        } catch (Exception e) {
        	logger.error("EventProducer异常" + e.getMessage());
            return false;
        }
    }
}
