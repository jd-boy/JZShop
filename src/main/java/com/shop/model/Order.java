package com.shop.model;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
	private String orderID;
	private int bnumber;
	private String username;
	private String recevieName;
	private String address;
	private String tel;
	private Date orderDate;
	private String bz;
	private BigDecimal sumPrice = new BigDecimal("0.0");
	private int status = 0;
	private int freeze = 0;
	
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public int getBnumber() {
		return bnumber;
	}
	public void setBnumber(int bnumber) {
		this.bnumber = bnumber;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRecevieName() {
		return recevieName;
	}
	public void setRecevieName(String recevieName) {
		this.recevieName = recevieName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public BigDecimal getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(BigDecimal sumPrice) {
		this.sumPrice = sumPrice;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getFreeze() {
		return freeze;
	}
	public void setFreeze(int freeze) {
		this.freeze = freeze;
	}
}
