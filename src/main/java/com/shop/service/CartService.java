package com.shop.service;

import java.util.ArrayList;
import java.util.Date;

import com.shop.model.Cart;

public interface CartService {
	
	//插入一条新是商品
	public int insertCart(Cart cart);
	
	//返回用户购物车中全部商品信息
	public ArrayList<Cart> getCartsByUserId(int userId);
	
	//饭后某个用户购物车中的某个商品全部信息
	public Cart getGoodsByUserId(int userId, int goodsId);
	
	//返回某个商品的数量
	public int getNum(int userId, int goodsId);
	
	//设置某个商品的数量
	public int  setNum(int num, int userId, int goodsId);
	
	//返回某个商品的添加时间
	public Date getAddDate(int userId, int goodsId);
	
	//设置某个商品的添加时间
	public int setAddDate(Date addDate, int userId, int goodsId);
	
	//添加商品到购物车
	public void addCart(int userId, int goodsId, int num);
	
	//从购物车中删除一个商品
	public int delGoods(int userId, int goodsId);
	
	//删除购物车中全部商品
	public int clearCart(int userId);
}
