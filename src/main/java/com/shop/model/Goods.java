package com.shop.model;

import java.math.BigDecimal;
import java.util.Date;

public class Goods {
	
	private int ID;
	private int userId;
	private int typeID;
	private String goodsName;
	private String introduce;
	private BigDecimal price = new BigDecimal("0.00");
	private BigDecimal nowPrice = new BigDecimal("0.00");
	private int num;
	private String picture;
	private Date INTime;
	private int sale = 0;
	private int hit = 0;
	private int buy = 0;
	private int status = 0;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getTypeID() {
		return typeID;
	}
	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getNowPrice() {
		return nowPrice;
	}
	public void setNowPrice(BigDecimal nowPrice) {
		this.nowPrice = nowPrice;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public Date getINTime() {
		return INTime;
	}
	public void setINTime(Date iNTime) {
		INTime = iNTime;
	}
	public int getSale() {
		return sale;
	}
	public void setSale(int sale) {
		this.sale = sale;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getBuy() {
		return buy;
	}
	public void setBuy(int buy) {
		this.buy = buy;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
