package com.shop.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shop.model.LoginTicket;

@Mapper
public interface LoginTicketDAO {
	
	String TABLE_NAME = "tb_login_ticket";
	String INSERT_FIELDS = " userid, ticket, expired, type, status ";
	String SELECT_FIELDS = " id, " + INSERT_FIELDS;
	
	//根据ticker查询
	@Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE ticket = #{ticket}"})
	public LoginTicket getLoginTicketByTicket(String ticket);
	
	//根据用户名插入一条LoginTicket
	@Insert({"INSERT INTO ", TABLE_NAME, "( ", INSERT_FIELDS,
		" )  VALUES (#{userid},#{ticket},#{expired},#{type},#{status})"})
	public int addLoginTicker(LoginTicket loginTicket);
	
	//根据ticket修改status
	@Update({"UPDATE ", TABLE_NAME, " SET status=#{status} WHERE ticket=#{ticket} AND type=#{type}"})
	public int updateStatus(@Param("ticket") String ticket,@Param("status") int status,@Param("type") int type);
}
