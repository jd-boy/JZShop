package com.shop.serviceImpl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.dao.CartDAO;
import com.shop.dao.GoodsDAO;
import com.shop.dao.MemberDAO;
import com.shop.dao.OrderDAO;
import com.shop.dao.OrderDetailDAO;
import com.shop.model.Cart;
import com.shop.model.HostHolder;
import com.shop.model.Order;
import com.shop.model.OrderDetail;
import com.shop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	public static int UN_PAY = 0;            //未付款
	public static int UN_SHIP = 1;           //未发货
	public static int UN_RECEIPT = 2;        //未确认收货
	public static int FINISH = 3;            //订单完成
	
	@Autowired
	private CartDAO cartDAO;
	
	@Autowired
	private GoodsDAO goodsDAO;
	
	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private OrderDetailDAO orderDetailDAO;
	
	@Autowired 
	private HostHolder hostHolder;

	@Override
	public int insterOrder(Order order) {
		return orderDAO.insterOrder(order);
	}

	@Override
	public ArrayList<Order> getAllOrder(String username) {
		return orderDAO.getAllOrder(username);
	}
	
	@Override
	public Order getOrder(String orderID) {
		return orderDAO.getOrder(orderID);
	}

	@Override
	public int setRecevieName(String orderID, String recevieName) {
		return orderDAO.setRecevieName(orderID, recevieName);
	}

	@Override
	public int setAddress(String orderID, String address) {
		return orderDAO.setAddress(orderID, address);
	}

	@Override
	public int setTel(String orderID, String tel) {
		return orderDAO.setTel(orderID, tel);
	}
	
	@Override
	public int setStatus(String orderID, int status) {
		return orderDAO.setStatus(orderID, status);
	}

	@Override
	public int insterOrderDetail(OrderDetail orderDetail) {
		return orderDetailDAO.insterOrderDetail(orderDetail);
	}

	@Override
	public Order getOrderDetailById(int ID) {
		return orderDetailDAO.getOrderDetailById(ID);
	}

	@Override
	public ArrayList<OrderDetail> getAllOrderDetail(String orderID) {
		return orderDetailDAO.getAllOrderDetail(orderID);
	}
	
	@Override
	public ArrayList<OrderDetail> getAllOrderDetailByUsername(String username, int start, int num) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		
		ArrayList<OrderDetail> list = new ArrayList<OrderDetail>();
		String time = null;
		for(String orderId : getAllOrderId(username)) {
			time = sdf.format(getOrderDate(orderId));
			for(OrderDetail orderDetail : getAllOrderDetail(orderId)) {
				orderDetail.setTime(time);
				list.add(orderDetail);
			}
		}
		
		ArrayList<OrderDetail> res = new ArrayList<OrderDetail>();
		int end = start + num;
		for(int i = start; i < end && i < list.size(); i++) {
			res.add(list.get(i));
		}
		
		return res;
	}

	@Override
	public String createOrderId() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		Random random = new Random((int)(Math.random()*10001));
		
		return sdf.format(new Date())+String.format("%04d", random.nextInt(10000));
	}

	@Override
	public String createOrder(Order order) {
		
		ArrayList<Cart> cartList = cartDAO.getCarts(hostHolder.getMember().getID());
		
		order.setOrderID(createOrderId());
		order.setUsername(hostHolder.getMember().getUserName());
		order.setBnumber(cartList.size());
		order.setOrderDate(new Date());
		order.setStatus(0);
		
		BigDecimal sumPrice = new BigDecimal("0.00");
		int flag = 0;
		int memberId = hostHolder.getMember().getID();
		
		for(Cart cart : cartList) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrderID(order.getOrderID());
			orderDetail.setGoodsID(cart.getGoodsId());
			orderDetail.setGoodsName(cart.getGoodsName());
			orderDetail.setSellerName(memberDAO.getUserNameById(goodsDAO.getUserId(cart.getGoodsId())));
			orderDetail.setPrice(cart.getPrice());
			orderDetail.setGoodsPicture(cart.getPicture());
			orderDetail.setNumber(cart.getNum());
			if(orderDetailDAO.insterOrderDetail(orderDetail) == 0) {
				flag = 1;
				break;
			}
			cartDAO.setStatus(1, memberId, cart.getGoodsId());
			sumPrice = sumPrice.add(orderDetail.getPrice().multiply(new BigDecimal(String.valueOf(orderDetail.getNumber()))));
		}
		
		order.setSumPrice(sumPrice);
		
		if(orderDAO.insterOrder(order) == 0) {
			return null;
		}
		
		if(flag == 1) {
			for(Cart cart : cartList) {
				cartDAO.setStatus(0, memberId, cart.getGoodsId());
			}
			return null;
		}
		
		return order.getOrderID();
	}

	@Override
	public ArrayList<String> getAllOrderId(String username) {
		return orderDAO.getAllOrderId(username);
	}

	@Override
	public int getOrderDetailCount(String username) {
		
		if(orderDAO.getAllOrderId(username).size() == 0) {
			return 0;
		} else {
			return orderDetailDAO.getOrderDetailCount(username);
		}
	}

	@Override
	public int getOrderStatus(String orderID) {
		return orderDAO.getStatus(orderID);
	}

	@Override
	public int setFreezeOrder(String orderID, int freeze) {
		return orderDAO.setFreezeOrder(orderID, freeze);
	}

	@Override
	public int setOrderDetailFreeze(String orderID, int status) {
		return orderDetailDAO.setOrderDetailFreeze(orderID, status);
	}

	@Override
	public int confirmOrder(String orderID) {

		if(setFreezeOrder(orderID, 1) == 0) {
			return 0;
		}
		
		return setOrderDetailFreeze(orderID, 1);
	}

	@Override
	public ArrayList<String> getAllGoodsName(String orderID) {
		return orderDetailDAO.getAllGoodsName(orderID);
	}

	@Override
	public ArrayList<Integer> getAllGoodsIdByOrderId(String orderID) {
		return orderDetailDAO.getAllGoodsIdByOrderId(orderID);
	}

	@Override
	public ArrayList<OrderDetail> getOrderDetailBySeller(String sellerName, int status, int start, int num) {
		return orderDetailDAO.getOrderDetailBySeller(sellerName, status, start, num);
	}

	@Override
	public int getCountOrderDetailBySeller(String sellerName, int status) {
		return orderDetailDAO.getCountOrderDetailBySeller(sellerName, status);
	}

	@Override
	public int setOrderDetailStatus(String orderID, int goodsID, int status) {
		return orderDetailDAO.setOrderDetailStatus(orderID, goodsID, status);
	}

	@Override
	public int setTrackingNumber(String orderID, int goodsID, String trackingNumber) {
		return orderDetailDAO.setTrackingNumber(orderID, goodsID, trackingNumber);
	}

	@Override
	public Date getOrderDate(String orderID) {
		return orderDAO.getOrderDate(orderID);
	}
	
}
