package com.shop.model;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class HostHolder {
		
	private ThreadLocal<Member> members = new ThreadLocal<Member>();
	
	private ThreadLocal<Manager> managers = new ThreadLocal<Manager>();
	
	private ThreadLocal<ArrayList<Cart>> carts = new ThreadLocal<ArrayList<Cart>>();
	
	
	/*
	 * 添加一个普通账号 
	 */
	public Member getMember() {
		return (Member)members.get();
	}
	
	public void setMember(Member member) {
		members.set(member);
	}
	
	
	/*
	 * 添加一个管理员账号 
	 */
	public Manager getManager() {
		return (Manager)managers.get();
	}
	
	public void setManager(Manager manager) {
		managers.set(manager);
	}
	
	
	/*
	 *  获取购物车信息 
	 */
	public ArrayList<Cart> getCartList() {
		return (ArrayList<Cart>)carts.get();
	}
	
	public void setCartList(ArrayList<Cart> cartList) {
		carts.set(cartList);
	}
	
	public void clear() {
		members.remove();
		carts.remove();
		managers.remove();
	}
	
	
}
