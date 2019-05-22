package com.shop.model;

import java.math.BigDecimal;
import java.util.Date;

public class PaySerial {
	
	private int id;
	private String orderId;
	private BigDecimal price;
	private String zhifubaoId;
	private Date createDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getZhifubaoId() {
		return zhifubaoId;
	}
	public void setZhifubaoId(String zhifubaoId) {
		this.zhifubaoId = zhifubaoId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
