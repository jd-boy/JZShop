package com.shop.service;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.shop.model.Comment;
import com.shop.model.Goods;

public interface GoodsService {
	
	
	public Goods getGoodsByID(int id);//根据商品ID查询商品全部信息
	
	public ArrayList<Goods> getAllGoodsByStore(int userId);//查询某商家的全部商品
	
	public int addGoods(Goods goods);//插入一条新的商品信息
	
	public int delGoodsById(int id);//根据商品ID删除一条商品信息
	
	public ArrayList<Goods> getNewGoods();//查询全部商品中最新上架商品信息
	
	public ArrayList<Goods> getNewGoodsByType(int typeID);//查询特定类型商品中最新上架商品信息
	
	public ArrayList<Goods> getNewGoodsByStore(int userId);//查询某卖家店铺中的新品商品
	
	public ArrayList<Goods> getDiscountGoods();//查询全部商品中打折商品信息
	
	public ArrayList<Goods> getDiscountGoodsByType(int typeID);//查询特定类型的打折商品信息
	
	public ArrayList<Goods> getDiscountGoodsByStore(int userId);//查询某卖家店铺中的打折商品
	
	public ArrayList<Goods> getHotGoods();//查询热门商品信息（点击量最高的两个）
	
	//查询特定类别的商品中点击量最高的两个商品信息
	public ArrayList<Goods> getHotGoodsByType(int typeID);
	
	public ArrayList<Goods> getHotGoodsByStore(int userId);//查询某卖家商品中点击量最高的两个商品
	
	public ArrayList<Goods> getHotGoodsByTypeID(int typeID);//按商品类型查询商品信息，并按点击量的降序，id的升序排序
	
	public ArrayList<Goods> getHotGoodsBySuperID(int superID);//按商品类型的父类查询热门商品
	
	public ArrayList<Goods> getGoodsByTypeID(int typeID);//根据商品类型ID查询部分信息
	
	public String saveImage(MultipartFile image) throws IOException;//上传图片
	
	public String getPicture(int id);//根据商品id，获取其图片名
	
	public int setPicture(int id, String picture);//根据商品id，修改其图片名
	
	public int updateHit(int id, int hit);//根据商品id，修改点击量
	
	public int getBuy(int id);//根据商品id获取商品销量
	
	public int setBuy(int id, int buy);//根据商品id修改商品销量
	
	public int getStatus(int id);//根据商品id获取商品状态
	
	public int setStatus(int id, int status);//根据商品id修改商品状态
	
	public int getUserId(int id);//根据商品id获取卖家Id
	
	public ArrayList<Goods> getGoodsByKeyword(String keyword, int start, int number);//根据关键字查询商品
	
	public int getCountByKeyword(String keyword);//根据关键字返回匹配的商品个数
	
	public int setInfo(Goods goods);//根据商品id修改名称、价格、折后价、库存、简介
	
	public int getMaxId();//返回ID最大值
	
	public int addComment(Comment comment);//插入一条新评论
	
	public ArrayList<Comment> getCommentsByGoodsId(int goodsId, int start, int number);//查询某一商品的评论
	
	public ArrayList<Comment> getCommentsByUserid(int userId);//查询用户的全部评论
	
	public int getCommentCountByGoodsId(int id);//根据商品id获取商品总评论数
	
	public ArrayList<Goods> getAllByStore(int userId, int start, int num);//查询店铺中全部商品
	
	public int getGoodsNumByStore(int userId);//返回某商铺中全部商品的数量
	
	//查询某商品的库存num
	public int getGoodsNum(int id);
	
	//根据商品id修改商品库存
	public int setGoodsNum(int id, int num);
}
