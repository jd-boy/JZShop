package com.shop.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shop.model.Supertype;

@Mapper
public interface SupertypeDAO {
	
	String TABLE_NAME = "tb_supertype";
	String INSERT_FIELDS = " typeName,status ";
	String SELECT_FIELDS = "ID," + INSERT_FIELDS;
	
	//插入一个父分类
	@Insert({"INSERT INTO ", TABLE_NAME, "(typeName) VALUES (#{typeName})"})
	public int insertSupertype(String typeName);
	
	//查询全部父分类名
	@Select({"SELECT typeName FROM ", TABLE_NAME, " WHERE status=0"})
	public ArrayList<String> getAllSupertypeName();
	
	//查询全部父分类
	@Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE status=0"})
	public ArrayList<Supertype> getAllSupertype();
	
	//根据分类ID查询分类名
	@Select({"SELECT typeName FROM ", TABLE_NAME, " WHERE ID=#{id} AND status=0"})
	public String getSupertypeName(int id);
	
	//根据分类名查询分类ID
	@Select({"SELECT ID FROM ", TABLE_NAME, " WHERE typeName=#{typeName} AND status=0"})
	public int getSupertypeId(String typeName);
	
	//根据分类名查询分类所有信息
	@Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE typeName=#{typeName} AND status=0"})
	public Supertype getSupertypeByName(String typeName);
	
	//根据ID删除分类
	@Update({"UPDATE ", TABLE_NAME, " SET status=1 WHERE ID=#{id}"})
	public int delSupertype(int id);
	
	//根据ID修改总分类名
	@Update({"UPDATE ", TABLE_NAME, " SET typeName=#{typeName} WHERE ID=#{id}"})
	public int alertSupertypeName(@Param("id") int id, @Param("typeName") String typeName);
	
}
