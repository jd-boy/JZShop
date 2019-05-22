package com.shop.serviceImpl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.async.EventModel;
import com.shop.async.EventProducer;
import com.shop.async.EventType;
import com.shop.dao.GoodsDAO;
import com.shop.dao.MemberDAO;
import com.shop.model.EntityType;
import com.shop.model.HostHolder;
import com.shop.model.Member;
import com.shop.model.Order;
import com.shop.model.OrderDetail;
import com.shop.service.MailService;
import com.shop.util.JedisAdapter;
import com.shop.serviceImpl.OrderServiceImpl;

@Service
public class MailServiceImpl implements MailService {
	
	@Autowired
	private HostHolder hostHolder;
	
	@Autowired
	private OrderServiceImpl orderServicImpl;
	
	@Autowired
	private JedisAdapter jedisAdapter;
	
	@Autowired
	private EventProducer eventProducer;

	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private GoodsDAO goodsDAO;
	
	@Override
	public void alterInfo() {
		String checkcode = "";
		Random random = new Random();
		for(int i = 0; i < 6; i++) {
			checkcode = checkcode + random.nextInt(10);
		}
		
		eventProducer.fireEvent(new EventModel(EventType.CHECK)
				.setEntityType(EntityType.ENTITY_MEMBER)
				.setExt("username", hostHolder.getMember().getUserName())
				.setExt("checkcode", checkcode)
				.setExt("to", hostHolder.getMember().getEmail()));
		
		jedisAdapter.setex("checkcode"+hostHolder.getMember().getID(), 60, checkcode);
		
	}

	@Override
	public void addComment(int goodsId, String comment) {
		
		Member seller = memberDAO.getMemberByGoodsId(goodsId);
		
		eventProducer.fireEvent(new EventModel(EventType.COMMENT)
				.setEntityType(EntityType.ENTITY_COMMENT)
				.setExt("seller", seller.getUserName())
				.setExt("goods", goodsDAO.getGoodsName(goodsId))
				.setExt("comment", comment)
				.setExt("to", seller.getEmail()));
	}

	@Override
	public void sendBuyNotice(Member member, OrderDetail orderDetail) {
		System.out.println(orderDetail.getOrderID()+"\n\n\n");
		Order order = orderServicImpl.getOrder(orderDetail.getOrderID());
		
		eventProducer.fireEvent(new EventModel(EventType.BUY_GOODS)
				.setEntityType(EntityType.ENTITY_GOODS)
				.setExt("seller", member.getUserName())
				.setExt("email", member.getEmail())
				.setExt("tel", order.getTel())
				.setExt("recevieName", order.getRecevieName())
				.setExt("address", order.getAddress())
				.setExt("bz", order.getBz())
				.setExt("orderId", orderDetail.getOrderID())
				.setExt("goodsNum", String.valueOf(orderDetail.getNumber()))
				.setExt("goodsName", orderDetail.getGoodsName()));
	}
	
}
