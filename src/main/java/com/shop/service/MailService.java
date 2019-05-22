package com.shop.service;

import com.shop.model.Member;
import com.shop.model.OrderDetail;

public interface MailService {
	
	public void alterInfo();//用户修改账号信息时发送验证码
	
	public void addComment(int goodsId, String comment);//商品被评论时给商家发送评论邮件
	
	public void sendBuyNotice(Member member, OrderDetail orderDetail);//商品被购买后给商家发送发货通知
}
