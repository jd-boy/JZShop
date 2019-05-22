package com.shop.service;

import java.util.ArrayList;

import com.shop.model.Subtype;
import com.shop.model.Supertype;

public interface GoodsTypeService {
	
	//插入一个父分类
	public String addSupertype(String typeName);
	
	//查询全部父分类名
	public ArrayList<String> getAllSupertypeName();
	
	//查询全部父分类
	public ArrayList<Supertype> getAllSupertype();

	//根据分类ID查询分类名
	public String getSupertypeName(int id);
	
	//根据分类名查询分类ID
	public int getSupertypeId(String typeName);
	
	//根据ID删除总分类
	public int delSupertype(int id);
	
	//根据ID修改总分类名
	public String alertSupertypeName(int id, String typeName);
	
	
	
	//插入一个子分类
	public String addSubtype(int superType, String typeName);
	
	//查询全部子分类名
	public ArrayList<String> getAllSubtypeName();
	
	//查询全部子分类的全部信息
	public ArrayList<Subtype> getAllSubtype();
	
	//根据父类Id查询全部子分类名
	public ArrayList<String> getAllSubtypeNameBySuperType(int superType);
	
	//根据父类Id查询全部子分类的Id
	public ArrayList<String> getAllSubtypeIdBySuperType(int superType);
	
	//根据子类别名查询其父类Id
	public int getSuperType(String typeName);
	
	//根据ID删除类别
	public int delSubtype(int id);
	
	//根据id修改子类别名
	public String setTypeName(int id, String typeName);
	
}
