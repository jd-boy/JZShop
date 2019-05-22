package com.shop.serviceImpl;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.dao.CartDAO;
import com.shop.dao.GoodsDAO;
import com.shop.model.Cart;
import com.shop.model.Goods;
import com.shop.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartDAO cartDAO;
	
	@Autowired
	private GoodsDAO goodsDAO;
	
	@Override
	public int insertCart(Cart cart) {
		return cartDAO.addCart(cart);
	}
	
	@Override
	public Cart getGoodsByUserId(int userId, int goodsId) {
		return cartDAO.getGoodsByUserId(userId, goodsId);
	}

	@Override
	public ArrayList<Cart> getCartsByUserId(int userId) {
		return cartDAO.getCarts(userId);
	}

	@Override
	public int getNum(int userId, int goodsId) {
		return cartDAO.getNum(userId, goodsId);
	}

	@Override
	public int setNum(int num, int userId, int goodsId) {
		return cartDAO.setNum(num, userId, goodsId);
	}

	@Override
	public Date getAddDate(int userId, int goodsId) {
		return cartDAO.getAddDate(userId, goodsId);
	}

	@Override
	public int setAddDate(Date addDate, int userId, int goodsId) {
		return cartDAO.setAddDate(addDate, userId, goodsId);
	}

	@Override
	public void addCart(int userId, int goodsId, int num) {
		
		Cart cart = getGoodsByUserId(userId, goodsId);
		
		if(cart == null) {
			
			Goods goods = goodsDAO.getGoodsByID(goodsId);
			
			cart = new Cart();
			
			cart.setUserId(userId);
			cart.setGoodsId(goodsId);
			cart.setGoodsName(goods.getGoodsName());
			cart.setPicture(goods.getPicture());
			if(goods.getSale() == 1) {
				cart.setPrice(goods.getNowPrice());
			} else {
				cart.setPrice(goods.getPrice());
			}
			cart.setNum(num);
			cart.setAddDate(new Date());
			cart.setStatus(0);
			
			insertCart(cart);
		} else {
			setNum(cart.getNum()+num, userId, goodsId);
			setAddDate(new Date(), userId, goodsId);
		}
		return ;
	}

	@Override
	public int delGoods(int userId, int goodsId) {
		return cartDAO.setStatus(1, userId, goodsId);
	}

	@Override
	public int clearCart(int userId) {
		return cartDAO.clearCart(userId);
	}

}
