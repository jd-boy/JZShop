package com.shop.model;

import java.util.Date;

public class Manager {
	
	private int id;
	private String manager;
	private String password;
	private String salt;
	private int type = 0;
	private String head = "head.png";
	private Date createdate;
	private int freeze = 0;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public int getFreeze() {
		return freeze;
	}
	public void setFreeze(int freeze) {
		this.freeze = freeze;
	}
	
}
