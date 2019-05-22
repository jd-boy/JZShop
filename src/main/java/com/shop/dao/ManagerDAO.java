package com.shop.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shop.model.Manager;

@Mapper
public interface ManagerDAO {
	
	String TABLE_NAME = " tb_manager ";
	String INSERT_FIELDS = " manager, password, salt, type, head, createdate, freeze ";
	String SELECT_FIELDS = "id, " + INSERT_FIELDS;
	
	//添加一个管理员账号
	@Insert({"INSERT INTO ", TABLE_NAME, "(", INSERT_FIELDS, ") VALUES (#{manager},#{password},#{salt},",
			"#{type},#{head},#{createdate},#{freeze})"})
	public int addManager(Manager manager);
	
	//根据账号名查询某管理员账号的信息
	@Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE manager=#{manager}"})
	public Manager getManager(String manager);
	
	//根据账号id查询某管理员账号的信息
	@Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE id=#{id}"})
	public Manager getManagerById(int id);
	
	//根据账号名修改管理员账号是否冻结
	@Update({"UPDATE ", TABLE_NAME, " SET freeze=#{freeze} WHERE manager=#{manager}"})
	public int setFreezeByUsername(@Param("manager") String manager, @Param("freeze") int freeze);
	
	//根据账号名查询是否被冻结
	@Select({"SELECT freeze FROM ", TABLE_NAME, " WHERE manager=#{manager}"})
	public int getFreeze(String manager);
	
}
