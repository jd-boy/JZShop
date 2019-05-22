package com.shop.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shop.model.Goods;

@Mapper
public interface GoodsDAO {
	
	String TABLE_NAME = " tb_goods ";
	String INSERT_FIELDS = " userId,typeID,goodsName,introduce,price,nowPrice,num,picture,INTime,sale,hit,buy,status";
	String SELECT_FIELDS = "ID," + INSERT_FIELDS;
	
	//根据商品ID查询商品全部信息
	@Select({"SELECT ", SELECT_FIELDS, "FROM", TABLE_NAME, 
		"WHERE ID = #{id} AND status=0"})
	public Goods getGoodsByID(int id);
	
	//查询某商家的全部商品
	@Select({"SELECT ", SELECT_FIELDS, "FROM", TABLE_NAME, 
		"WHERE userId = #{userId} AND status=0 ORDER BY INTime DESC"})
	public ArrayList<Goods> getAllGoodsByStore(int userId);
	
	//插入一条新的商品信息
	@Insert({"INSERT INTO ", TABLE_NAME, "(", INSERT_FIELDS, ") VALUES (#{userId},#{typeID},#{goodsName},#{introduce},#{price},",
		"#{nowPrice},#{num},#{picture},#{INTime},#{sale},#{hit},#{buy},#{status})"})
	public int addGoods(Goods goods);
	
	//根据商品ID删除一条商品信息
	@Delete({"DELETE FROM ", TABLE_NAME, " WHERE ID = #{id}"})
	public int delGoodsById(int id);
	
	//查询全部商品中前30个最新上架商品信息
	@Select({"SELECT t1.ID, t1.goodsName,t1.price,t1.picture,t2.typeName FROM ", TABLE_NAME, " t1, tb_subtype t2 ",
		"WHERE t1.typeID=t2.ID AND DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(t1.INTime) AND t1.status=0",
		" order by t1.INTime desc limit 30"})
	public ArrayList<Goods> getNewGoods();
	
	//查询特定类型商品中前30个最新上架商品信息
	@Select({"SELECT t1.ID, t1.goodsName,t1.price,t1.picture,t2.typeName FROM ", TABLE_NAME,
		" t1, tb_subtype t2 ",
		"WHERE t1.typeID=t2.ID AND DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(t1.INTime) AND t2.superType=#{typeID} AND t1.status=0",
		"order by t1.INTime desc limit 30"})
	public ArrayList<Goods> getNewGoodsByType(int typeID);
	
	//查询某卖家店铺中的新品商品
	@Select({"SELECT ID,goodsName,price,nowPrice,picture FROM", TABLE_NAME, 
		"WHERE userId=#{userId} AND DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(INTime)",
		" order by hit desc"})
	public ArrayList<Goods> getNewGoodsByStore(int userId);
	
	//查询全部商品中前30个打折商品信息
	@Select({"SELECT t1.ID, t1.GoodsName,t1.price,t1.nowPrice,t1.picture,t2.TypeName FROM", TABLE_NAME,
		" t1,tb_subtype t2 where t1.typeID=t2.ID AND t1.sale=1 AND t1.status=0",
		" order by t1.INTime DESC limit 30"})
	public ArrayList<Goods> getDiscountGoods();
	
	//查询特定类型商品中的前30个打折商品信息
	@Select({"SELECT t1.ID, t1.GoodsName,t1.price,t1.nowPrice,t1.picture,t2.TypeName FROM", TABLE_NAME,
		" t1,tb_subtype t2 where t1.typeID=t2.ID and t1.sale=1 AND t2.superType=#{typeID} AND t1.status=0",
		"order by t1.INTime desc limit 30"})
	public ArrayList<Goods> getDiscountGoodsByType(int typeID);
	
	//查询某卖家店铺中的打折商品
	@Select({"SELECT ID,goodsName,price,nowPrice,picture FROM", TABLE_NAME, 
		"WHERE userId=#{userId}", " order by hit desc"})
	public ArrayList<Goods> getDiscountGoodsByStore(int userId);
	
	//查询全部商品中点击量最高的两个商品信息
	@Select({"SELECT ID,goodsName,nowPrice,picture FROM", TABLE_NAME, "WHERE status=0",
		" order by hit desc limit 2"})
	public ArrayList<Goods> getHotGoods();
	
	//查询特定类别的商品中点击量最高的两个商品信息
	@Select({"SELECT t1.ID,t1.goodsName,t1.nowPrice,t1.picture FROM", TABLE_NAME, " t1,tb_subtype t2 ",
		"WHERE t1.typeID=t2.ID AND t2.superType=#{typeID}",
		" order by hit desc limit 2"})
	public ArrayList<Goods> getHotGoodsByType(int typeID);
	
	//查询某卖家商品中点击量最高的两个商品
	@Select({"SELECT ID,goodsName,nowPrice,picture FROM", TABLE_NAME, 
		"WHERE userId=#{userId}", " order by hit desc limit 2"})
	public ArrayList<Goods> getHotGoodsByStore(int userId);
	
	//按商品类型查询商品信息，并按点击量的降序，id的升序排序
	@Select({"SELECT ID,goodsName,nowPrice,picture FROM ", TABLE_NAME, "WHERE typeID=#{typeID} ",
		"ORDER BY hit DESC,ID ASC"})
	public ArrayList<Goods> getHotGoodsByTypeID(int typeID);
	
	//按商品类型的父类查询热门商品
	@Select({"SELECT t1.ID,t1.GoodsName,t1.nowprice,t1.picture FROM tb_goods t1,tb_subtype t2 ",
		"WHERE t1.typeID=t2.ID and t2.superType=#{superID} ", "ORDER BY t1.hit DESC,t1.ID ASC"})
	public ArrayList<Goods> getHotGoodsBySuperID(int superID);
	
	//根据商品类型号查询
	@Select({"SELECT ID,goodsName,nowPrice,picture FROM", TABLE_NAME, " WHERE typeID = #{typeID}"})
	public ArrayList<Goods> getGoodsByTypeID(int typeID);
	
	//根据商品id获取卖家Id
	@Select({"SELECT userId FROM ", TABLE_NAME, " WHERE ID=#{id}"})
	public int getUserId(int id);
	
	//根据商品id，获取其图片名
	@Select({"SELECT picture FROM ", TABLE_NAME, "WHERE ID=#{id}"})
	public String getPicture(int id);
	
	//根据商品id，修改其图片名
	@Update({"UPDATE ", TABLE_NAME, " SET picture=#{picture} WHERE ID=#{id}"})
	public int setPicture(@Param("id") int id, @Param("picture") String picture);
	
	//根据商品id，修改点击量
	@Update({"UPDATE ", TABLE_NAME, " SET hit=#{hit} WHERE ID=#{id}"})
	public int updateHit(@Param("id") int id, @Param("hit") int hit);
	
	//根据商品id获取商品销量
	@Select({"SELECT buy FROM ", TABLE_NAME, " WHERE ID=#{id}"})
	public int getBuy(int id);
	
	//根据商品id修改商品销量
	@Update({"UPDATE ", TABLE_NAME, " SET buy=#{buy} WHERE ID=#{id}"})
	public int setBuy(@Param("id") int id, @Param("buy") int buy);
	
	//根据商品id获取商品状态
	@Select({"SELECT status FROM ", TABLE_NAME, " WHERE ID=#{id}"})
	public int getStatus(int id);
	
	//根据商品id修改商品状态
	@Update({"UPDATE ", TABLE_NAME, " SET status=#{status} WHERE ID=#{id}"})
	public int setStatus(@Param("id") int id, @Param("status") int status);
	
	
	//根据关键字匹配商品名
	@Select({"SELECT ID,goodsName,price,nowPrice,picture FROM ", TABLE_NAME,
		"WHERE MATCH (goodsName,introduce) AGAINST (#{keyword} WITH QUERY EXPANSION) ",
		"AND DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(INTime) AND status=0",
		" limit #{start},#{number}"})
	public ArrayList<Goods> getGoodsByKeyword(@Param("keyword") String keyword,
											  @Param("start") int start,
											  @Param("number") int number);
	
	//根据关键字返回匹配的商品个数
	@Select({"SELECT COUNT(ID) FROM ", TABLE_NAME,
		"WHERE MATCH (goodsName,introduce) AGAINST (#{keyword} WITH QUERY EXPANSION) ",
		"AND DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(INTime) AND status=0"})
	public int getCountByKeyword(String keyword);
	
	//根据商品id修改名称、价格、折后价、库存、简介
	@Update({"UPDATE ", TABLE_NAME, " SET goodsName=#{goodsName},typeID=#{typeID},price=#{price},",
		"nowPrice=#{nowPrice},introduce=#{introduce},num=#{num},picture=#{picture} WHERE ID=#{ID}"})
	public int setInfo(Goods goods);
	
	//返回ID最大值
	@Select({"SELECT MAX(ID) FROM ", TABLE_NAME, " WHERE status=0"})
	public int getMaxId();
	
	//根据商品id获取商品名
	@Select({"SELECT goodsName FROM ", TABLE_NAME, " WHERE ID=#{id} AND status=0"})
	public String getGoodsName(int id);
	
	//查询店铺中全部商品
	@Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME,
			" WHERE userId=#{userId} AND status=0",
			"ORDER BY INTime DESC LIMIT #{start},#{num}"})
	public ArrayList<Goods> getAllByStore(@Param("userId") int userId,@Param("start") int start,
										  @Param("num") int num);
	
	//返回某商铺中全部商品的数量
	@Select({"SELECT COUNT(ID) FROM ", TABLE_NAME, " WHERE userId=#{userId} AND status=0"})
	public int getGoodsNumByStore(int userId);
	
	//查询某商品的库存num
	@Select({"SELECT num FROM ", TABLE_NAME, " WHERE ID=#{id} AND status=0"})
	public int getGoodsNum(int id);
	
	//根据商品id修改商品库存
	@Update({"UPDATE ", TABLE_NAME, " SET num=#{num} WHERE ID=#{id}"})
	public int setGoodsNum(@Param("id") int id, @Param("num") int num);
}
