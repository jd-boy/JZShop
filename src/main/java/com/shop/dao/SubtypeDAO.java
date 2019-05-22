package com.shop.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shop.model.Subtype;
import com.shop.model.Supertype;

@Mapper
public interface SubtypeDAO {
	
	String TABLE_NAME = "tb_subtype";
	String INSERT_FIELDS = " superType,typeName,status ";
	String SELECT_FIELDS = "ID," + INSERT_FIELDS;
	
	//插入一个子分类
	@Insert({"INSERT INTO ", TABLE_NAME, "(superType,typeName) VALUES (#{superType},",
		"#{typeName})"})
	public int insertSubtype(@Param("superType") int superType, @Param("typeName") String typeName);
	
	//查询全部子分类名
	@Select({"SELECT typeName FROM ", TABLE_NAME, " WHERE status=0"})
	public ArrayList<String> getAllSubtypeName();
	
	//查询全部分类的全部信息
	@Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE status=0"})
	public ArrayList<Subtype> getAllSubtype();
	
	//根据分类名查询分类所有信息
	@Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE typeName=#{typeName} AND status=0"})
	public Supertype getSubtypeByName(String typeName);
	
	//根据父类Id查询全部子分类名
	@Select({"SELECT typeName FROM ", TABLE_NAME, " WHERE superType=#{superType},status=0"})
	public ArrayList<String> getAllSubtypeNameBySuperType(int superType);
	
	//根据父类Id查询全部子分类的Id
	@Select({"SELECT ID FROM ", TABLE_NAME, " WHERE superType=#{superType},status=0"})
	public ArrayList<String> getAllSubtypeIdBySuperType(int superType);
	
	//根据子类别名查询其父类Id
	@Select({"SELECT superType FROM ", TABLE_NAME, " WHERE typeName=#{typeName},status=0"})
	public int getSuperType(String typeName);
	
	//根据ID删除类别
	@Update({"UPDATE ", TABLE_NAME, " SET status=1 WHERE ID=#{id}"})
	public int delSubtype(int id);
	
	//根据id修改子类别名
	@Update({"UPDATE ", TABLE_NAME, " SET typeName=#{typeName} WHERE ID=#{id}"})
	public int setTypeName(@Param("id") int id, @Param("typeName") String typeName);
	
	
}
