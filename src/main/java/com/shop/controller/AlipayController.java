package com.shop.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.config.AlipayConfig;
import com.shop.async.EventModel;
import com.shop.async.EventType;
import com.shop.model.EntityType;
import com.shop.model.HostHolder;
import com.shop.model.Member;
import com.shop.model.Order;
import com.shop.model.OrderDetail;
import com.shop.model.PaySerial;
import com.shop.serviceImpl.GoodsServiceImpl;
import com.shop.serviceImpl.MailServiceImpl;
import com.shop.serviceImpl.MemberServiceImpl;
import com.shop.serviceImpl.OrderServiceImpl;
import com.shop.serviceImpl.PaySerialServiceImpl;

@Controller
@RequestMapping("/alipay")
public class AlipayController {
	
	private static final Logger logger = LoggerFactory.getLogger(AlipayController.class);
	
	@Autowired
	private GoodsServiceImpl goodsServiceImpl;
	
	@Autowired
	private OrderServiceImpl orderServiceImpl;
	
	@Autowired
	private PaySerialServiceImpl paySerialServiceImpl;
	
	@Autowired
	private MemberServiceImpl memberServiceImpl;
	
	@Autowired
	private MailServiceImpl mailServiceImpl;
	
	@Autowired
	private HostHolder hostHolder;
	
	/**
	 * @Description: 前往支付宝第三方网关进行支付
	 * @version V1.0
	 */
	@RequestMapping(value = "/goAlipay")
	@ResponseBody
	public String goAlipay(HttpServletRequest request) {
		
		Order order = orderServiceImpl.getOrder(request.getParameter("orderId"));
		
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
 
		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
 
		//商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = order.getOrderID();
		//付款金额，必填
		String total_amount = order.getSumPrice().toString();
		//订单名称，必填
		String subject = hostHolder.getMember().getUserName();
		//商品描述，可空
		String body = "用户订购商品个数：" + order.getBnumber();
 
		// 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
    	String timeout_express = "1c";
    	
    	String qr_code_timeout_express = "2m";
 
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
				+ "\"total_amount\":\""+ total_amount +"\","
				+ "\"subject\":\""+ subject +"\","
				+ "\"body\":\""+ body +"\","
				+ "\"timeout_express\":\""+ timeout_express +"\","
				+ "\"qr_code_timeout_express\":\""+ qr_code_timeout_express +"\","
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
 
		try {
			return alipayClient.pageExecute(alipayRequest).getBody();
		} catch (AlipayApiException e) {
			e.printStackTrace();
			logger.error("前往支付页面异常");
			return "错误";
		}
 
	}
	
	/**
	 * @Description: 支付宝同步通知页面"
	 */
	@RequestMapping(value = "/alipayReturnNotice")
	public String alipayReturnNotice(HttpServletRequest request) throws Exception {
 
		logger.info("支付成功, 进入同步通知接口...");
 
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
 
		boolean signVerified = false;
		try {
			signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
		} catch (Exception e) {
			logger.error("SDK验证签名出现异常");
		}
 
		//——请在这里编写您的程序（以下代码仅作参考）——
		if(signVerified) {
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//付款金额
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
 
			//修改订单状态为已支付
			orderServiceImpl.setStatus(out_trade_no, 1);
			
			//修改商品库存和销量
			ArrayList<OrderDetail> list = orderServiceImpl.getAllOrderDetail(out_trade_no);
			for(OrderDetail orderDetail : list) {
				//修改商品库存
				int num = goodsServiceImpl.getGoodsNum(orderDetail.getGoodsID()) - orderDetail.getNumber();
				goodsServiceImpl.setGoodsNum(orderDetail.getGoodsID(), num);
				
				//增加商品销量
				int buy = goodsServiceImpl.getBuy(orderDetail.getGoodsID()) + orderDetail.getNumber();
				goodsServiceImpl.setBuy(orderDetail.getGoodsID(), buy);
				
				//修改订单详细中订单状态为待发货
				orderServiceImpl.setOrderDetailStatus(out_trade_no, orderDetail.getGoodsID(), OrderServiceImpl.UN_SHIP);
				
				Member member = memberServiceImpl.getNEByGoodsId(orderDetail.getGoodsID());
				//给卖家发送商品购买邮件，提醒发货
				mailServiceImpl.sendBuyNotice(member, orderDetail);
			}
			//新增支付流水
			PaySerial paySerial = new PaySerial();
			paySerial.setOrderId(out_trade_no);
			paySerial.setZhifubaoId(trade_no);
			paySerial.setPrice(new BigDecimal(total_amount));
			paySerial.setCreateDate(new Date());
			paySerialServiceImpl.addPaySerial(paySerial);
			
			String goods_names = "";
			
			for(String name : orderServiceImpl.getAllGoodsName(out_trade_no)) {
				goods_names += name+" ";
			}
			
			logger.info("********************** 支付成功(支付宝同步通知) **********************");
			logger.info("* 订单号: {}", out_trade_no);
			logger.info("* 支付宝交易号: {}", trade_no);
			logger.info("* 实付金额: {}", total_amount);
			logger.info("* 购买产品: {}", goods_names);
			logger.info("***************************************************************");
 
			return "pay_fulfil";
		}else {
			logger.error("支付, 验签失败...");
		}
		return "/pay_fulfil";
	}
 
	/**
	 * @Description: 支付宝异步 通知页面
	 */
	@RequestMapping(value = "/alipayNotifyNotice")
	public String alipayNotifyNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {
		logger.info("支付成功, 进入异步通知接口...");
 
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
 
		boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
 
		//——请在这里编写您的程序（以下代码仅作参考）——
		
		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
		if(signVerified) {//验证成功
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
 
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
 
			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
 
			//付款金额
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
 
			if(trade_status.equals("TRADE_FINISHED")) {
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
 
				//注意： 尚自习的订单没有退款功能, 这个条件判断是进不来的, 所以此处不必写代码
				//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
			} else if (trade_status.equals("TRADE_SUCCESS")) {
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
 
				//注意：
				//付款完成后，支付宝系统发送该交易状态通知
 
				// 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水
				/*orderService.updateOrderStatus(out_trade_no, trade_no, total_amount);
 
				Orders order = orderService.getOrderById(out_trade_no);
				Product product = productService.getProductById(order.getProductId());*/
 
				logger.info("********************** 支付成功(支付宝异步通知) **********************");
				logger.info("* 订单号: {}", out_trade_no);
				logger.info("* 支付宝交易号: {}", trade_no);
				logger.info("* 实付金额: {}", total_amount);
				logger.info("* 购买产品: {}", "佳士科技啊");
				logger.info("***************************************************************");
			}
			logger.info("支付成功...");
 
		} else {//验证失败
			logger.error("支付, 验签失败...");
		}
 
		return "/pay_fulfil";
	}
}
