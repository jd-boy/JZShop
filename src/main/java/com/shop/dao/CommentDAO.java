package com.shop.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.shop.model.Comment;

@Mapper
public interface CommentDAO {
	
	String TABLE_NAME = " tb_comment ";
	String INSERT_FIELDS = " userId, goodsId, orderId, text, date, status ";
	String SELECT_FIELDS = "id, " + INSERT_FIELDS;
	
	//插入一条新评论
	@Insert({"INSERT INTO ", TABLE_NAME, "(", INSERT_FIELDS, ") VALUES (#{userId},#{goodsId},#{orderId},",
			"#{text},#{date},#{status})"})
	public int addComment(Comment comment);
	
	//查询某一商品的评论
	@Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME,
			" WHERE goodsId=#{goodsId} AND status=0",
			" ORDER BY date DESC LIMIT #{start},#{number}"})
	public ArrayList<Comment> getCommentsByGoodsId(@Param("goodsId") int goodsId,
												   @Param("start") int start,
												   @Param("number") int number);
	
	//查询用户的全部评论
	@Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE userId=#{userId} AND status=0"})
	public ArrayList<Comment> getCommentsByUserid(int userId);
	
	//根据商品id获取商品总评论数
	@Select({"SELECT COUNT(id) FROM ", TABLE_NAME, " WHERE goodsId=#{goodsId} AND status=0"})
	public int getCommentCountByGoodsId(int goodsId);
}
