package com.shop.dao;

import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shop.model.Cart;

@Mapper
public interface CartDAO {
	
	String TABLE_NAME = " tb_cart ";
	String INSERT_FIELDS = " userId, goodsId, goodsName, picture, price, num, addDate, status ";
	String SELECT_FIELDS = "id," + INSERT_FIELDS;
	
	//插入一条新的商品
	@Insert({"INSERT INTO ", TABLE_NAME, "(", INSERT_FIELDS, ") VALUES (#{userId},#{goodsId},#{goodsName},",
			"#{picture},#{price},#{num},#{addDate},#{status})"})
	public int addCart(Cart cart);
	
	//返回用户购物车中全部商品信息
	@Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE userId=#{userId} AND status=0"})
	public ArrayList<Cart> getCarts(int userId);
	
	//返回某个用户购物车中的某个商品全部信息
	@Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE userId=#{userId} AND goodsId=#{goodsId}", 
		"AND status=0"})
	public Cart getGoodsByUserId(@Param("userId") int userId, @Param("goodsId") int goodsId);
	
	//返回某个商品的数量
	@Select({"SELECT num FROM ", TABLE_NAME, "WHERE userId=#{userId} AND goodsId=#{goodsId}"})
	public int getNum(@Param("userId") int userId, @Param("goodsId") int goodsId);
	
	//设置某个商品的数量
	@Update({"UPDATE ", TABLE_NAME, " SET num=#{num} WHERE userId=#{userId} AND goodsId=#{goodsId}"})
	public int  setNum(@Param("num") int num, @Param("userId") int userId, @Param("goodsId") int goodsId);
	
	//返回某个商品的添加时间
	@Select({"SELECT addDate FROM ", TABLE_NAME, "WHERE userId=#{userId} AND goodsId=#{goodsId}"})
	public Date getAddDate(@Param("userId") int userId, @Param("goodsId") int goodsId);
	
	//设置商品的添加时间
	@Update({"UPDATE ", TABLE_NAME, " SET addDate=#{addDate} WHERE userId=#{userId} AND goodsId=#{goodsId}"})
	public int setAddDate(@Param("addDate") Date addDate, @Param("userId") int userId, @Param("goodsId") int goodsId);
	
	//设置商品的状态
	@Update({"UPDATE ", TABLE_NAME, " SET status=#{status} WHERE userId=#{userId} AND goodsId=#{goodsId}"})
	public int setStatus(@Param("status") int status, @Param("userId") int userId, @Param("goodsId") int goodsId);
	
	//删除购物车中全部商品
	@Update({"UPDATE ", TABLE_NAME, " SET status=1 WHERE userId=#{userId} AND status=0"})
	public int clearCart(int userId);
	
}
