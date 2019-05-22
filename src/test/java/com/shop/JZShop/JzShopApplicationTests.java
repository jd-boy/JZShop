package com.shop.JZShop;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.shop.async.EventProducer;
import com.shop.model.Order;
import com.shop.serviceImpl.GoodsServiceImpl;
import com.shop.serviceImpl.MemberServiceImpl;
import com.shop.serviceImpl.OrderServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JzShopApplicationTests {
	
	@Autowired
	private GoodsServiceImpl goodsServiceImpl;
	
	@Autowired
	private OrderServiceImpl orderServiceImpl;
	
	@Autowired
	private MemberServiceImpl memberServiceImpl;
	
	@Autowired
	private EventProducer eventProducer;

	@Test
	public void contextLoads() throws MessagingException {
		
		/*ArrayList<OrderDetail> list = orderServiceImpl.getAllOrderDetail("201905062130093019");
		for(OrderDetail orderDetail : list) {
			//修改商品库存
			int num = goodsServiceImpl.getGoodsNum(orderDetail.getGoodsID()) - orderDetail.getNumber();
			goodsServiceImpl.setGoodsNum(orderDetail.getGoodsID(), num);
			
			//增加商品销量
			int buy = goodsServiceImpl.getBuy(orderDetail.getGoodsID()) + orderDetail.getNumber();
			goodsServiceImpl.setBuy(orderDetail.getGoodsID(), buy);
			
			Member member = memberServiceImpl.getNEByGoodsId(orderDetail.getGoodsID());
			
			eventProducer.fireEvent(new EventModel(EventType.BUY_GOODS)
					.setEntityType(EntityType.ENTITY_GOODS)
					.setExt("seller", member.getUserName())
					.setExt("email", member.getEmail())
					.setExt("orderId", orderDetail.getOrderID())
					.setExt("goodsNum", String.valueOf(orderDetail.getNumber()))
					.setExt("goodsName", orderDetail.getGoodsName()));
		}*/
		Order order = orderServiceImpl.getOrder("201905071109223283");
		System.out.println(order.getUsername());
	}

}
