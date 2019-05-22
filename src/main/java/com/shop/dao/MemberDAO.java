package com.shop.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shop.model.Member;

@Mapper
public interface MemberDAO {
	
	String TABLE_NAME = " tb_member ";
	String INSERT_FIELDS = " userName, trueName, password, salt, head, city, address, postcode, "
			+ "cardNO, cardType, grade, Amount, tel, email, type, freeze ";
	String SELECT_FIELDS = " ID, " + INSERT_FIELDS;
	
	//插入一个新的会员账号
	@Insert({"INSERT INTO ", TABLE_NAME, "(", INSERT_FIELDS, 
		") VALUES (#{userName},#{trueName},#{password},#{salt},#{head},#{city},#{address},#{postcode},",
		"#{cardNO},#{cardType},#{grade},#{amount},#{tel},#{email},#{type},#{freeze})"})
	public int addMember(Member member);
	
	//根据会员id返回该会员的全部信息
	@Select({"SELECT", SELECT_FIELDS, " FROM ", TABLE_NAME, "WHERE ID = #{id}"})
	public Member getMemberByID(int id);
	
	//根据账号名返回该会员的全部信息
	@Select({"SELECT", SELECT_FIELDS, " FROM ", TABLE_NAME, "WHERE userName = #{userName}"})
	public Member getMemberByUserName(String userName);
	
	//根据账号名返回账号ID
	@Select({"SELECT ID FROM ", TABLE_NAME, " WHERE userName=#{userName}"})
	public int getIdByUserName(String userName);
	
	//根据账号id返回账号名
	@Select({"SELECT userName FROM ", TABLE_NAME, " WHERE ID=#{ID}"})
	public String getUserNameById(int ID);
	
	//根据用户ID获取账号类型
	@Select({"SELECT type FROM ", TABLE_NAME, " WHERE ID=#{id}"})
	public int getType(int id);
	
	//根据用户ID修改账号类型
	@Update({"UPDATE ", TABLE_NAME, " SET type=#{type} WHERE ID=#{id}"})
	public int setType(@Param("id") int id, @Param("type") int type);
	
	//修改账号基本信息
	@Update({"UPDATE ", TABLE_NAME, " SET tel=#{tel},address=#{address},",
			"postcode=#{postcode},email=#{email} WHERE ID=#{ID}"})
	public int setInfo(Member member);
	
	//修改账号密码
	@Update({"UPDATE ", TABLE_NAME, " SET password=#{password},salt=#{salt} WHERE ID=#{id}"})
	public int setPassword(@Param("id") int id,
						   @Param("password") String password,
						   @Param("salt") String salt);
	
	//修改账号是否冻结
	@Update({"UPDATE ", TABLE_NAME, " SET freeze=#{freeze} WHERE userName=#{userName}"})
	public int setFreeze(@Param("userName") String userName, @Param("freeze") int freeze);
	
	//获取账号冻结状态
	@Select({"SELECT freeze FROM ", TABLE_NAME, " WHERE userName=#{userName}"})
	public int getFreeze(String userName);
	
	//根据账号名修改账号账号类别
	@Update({"UPDATE ", TABLE_NAME, " SET type=#{type} WHERE userName=#{userName}"})
	public int alertType(@Param("userName") String userName, @Param("type") int type);
	
	//根据商品id获取商家信息
	@Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, "WHERE ID = ",
			"(SELECT userId FROM tb_goods WHERE ID=#{ID})"})
	public Member getMemberByGoodsId(int ID);
	
	//根据用户id获取头像
	@Select({"SELECT head FROM ", TABLE_NAME, " WHERE ID = #{id}"})
	public String getHeadById(int id);
	
	//根据商品id获取用户名，邮箱
	@Select({"SELECT userName,email FROM ", TABLE_NAME, " WHERE ID = ",
			"(SELECT userId FROM tb_goods WHERE ID = #{goodsId})"})
	public Member getNEByGoodsId(int goodsId);
}
