package com.shop.serviceImpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.dao.SubtypeDAO;
import com.shop.dao.SupertypeDAO;
import com.shop.model.Subtype;
import com.shop.model.Supertype;
import com.shop.service.GoodsTypeService;
import com.shop.util.JSONStringUtil;

@Service
public class GoodsTypeServiceImpl implements GoodsTypeService {
	
	@Autowired 
	private SupertypeDAO supertypeDAO;
	
	@Autowired 
	private SubtypeDAO subtypeDAO;

	@Override
	public String addSupertype(String typeName) {
		
		if(supertypeDAO.getSupertypeByName(typeName) == null) {
			return JSONStringUtil.getJSONString(1, "该类别名已存在，请重新输入");
		}
		
		if(supertypeDAO.insertSupertype(typeName) == 0) {
			return JSONStringUtil.getJSONString(1, "添加总分类失败");
		} else {
			return JSONStringUtil.getJSONString(0, "添加总分类成功");
		}
		
	}

	@Override
	public ArrayList<String> getAllSupertypeName() {
		return supertypeDAO.getAllSupertypeName();
	}
	
	@Override
	public ArrayList<Supertype> getAllSupertype() {
		return supertypeDAO.getAllSupertype();
	}
	
	@Override
	public String getSupertypeName(int id) {
		return supertypeDAO.getSupertypeName(id);
	}

	@Override
	public int getSupertypeId(String typeName) {
		return supertypeDAO.getSupertypeId(typeName);
	}

	@Override
	public int delSupertype(int id) {
		return supertypeDAO.delSupertype(id);
	}
	
	@Override
	public String alertSupertypeName(int id, String typeName) {
		
		if(supertypeDAO.getSupertypeByName(typeName) == null) {
			return JSONStringUtil.getJSONString(1, "该类别名已存在，请重新输入");
		}
		
		if(supertypeDAO.alertSupertypeName(id, typeName) == 0) {
			return JSONStringUtil.getJSONString(1, "修改总商品类名失败");
		} else {
			return JSONStringUtil.getJSONString(0, "修改总商品类名成功");
		}
		
	}

	
	
	@Override
	public String addSubtype(int superType, String typeName) {
		
		if(subtypeDAO.getSubtypeByName(typeName) != null) {
			return JSONStringUtil.getJSONString(1, "此类别名已存在，请重新输入");
		}
		
		if(subtypeDAO.insertSubtype(superType, typeName) == 0) {
			return JSONStringUtil.getJSONString(1, "添加子商品类别失败");
		} else {
			return JSONStringUtil.getJSONString(0, "添加子商品类别成功");
		}
	}

	@Override
	public ArrayList<String> getAllSubtypeName() {
		return subtypeDAO.getAllSubtypeName();
	}
	
	@Override
	public ArrayList<Subtype> getAllSubtype() {
		return subtypeDAO.getAllSubtype();
	}

	@Override
	public ArrayList<String> getAllSubtypeNameBySuperType(int superType) {
		return subtypeDAO.getAllSubtypeNameBySuperType(superType);
	}

	@Override
	public ArrayList<String> getAllSubtypeIdBySuperType(int superType) {
		return subtypeDAO.getAllSubtypeIdBySuperType(superType);
	}

	@Override
	public int getSuperType(String typeName) {
		return subtypeDAO.getSuperType(typeName);
	}

	@Override
	public int delSubtype(int id) {
		return subtypeDAO.delSubtype(id);
	}

	@Override
	public String setTypeName(int id, String typeName) {
		
		if(subtypeDAO.getSubtypeByName(typeName) != null) {
			return JSONStringUtil.getJSONString(1, "此类别名已存在，请重新输入");
		}
		
		if(subtypeDAO.setTypeName(id, typeName) == 0) {
			return JSONStringUtil.getJSONString(1, "修改子商品类别名失败");
		} else {
			return JSONStringUtil.getJSONString(0, "修改子商品类别名成功");
		}
	}
	
}
