package com.shop.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shop.dao.CommentDAO;
import com.shop.dao.GoodsDAO;
import com.shop.dao.SubtypeDAO;
import com.shop.dao.SupertypeDAO;
import com.shop.model.Comment;
import com.shop.model.Goods;
import com.shop.model.HostHolder;
import com.shop.service.GoodsService;
import com.shop.util.UploadFileUtil;

@Service
public class GoodsServiceImpl implements GoodsService {
	
	@Autowired
	private GoodsDAO goodsDAO;
	
	@Autowired
	private CommentDAO commentDAO;
	
	@Autowired
	private SubtypeDAO subtypeDAO;
	
	@Autowired
	private SupertypeDAO supertypeDAO;
	
	@Autowired
	private HostHolder hostHolder;
	
	@Override
	public Goods getGoodsByID(int id) {
		return goodsDAO.getGoodsByID(id);
	}
	
	@Override
	public ArrayList<Goods> getAllGoodsByStore(int userId) {
		return goodsDAO.getAllGoodsByStore(userId);
	}

	@Override
	public int addGoods(Goods goods) {
		
		goods.setUserId(hostHolder.getMember().getID());
		goods.setINTime(new Date());
		return goodsDAO.addGoods(goods);
	}

	@Override
	public int delGoodsById(int id) {
		return goodsDAO.delGoodsById(id);
	}

	@Override
	public ArrayList<Goods> getNewGoods() {
		return goodsDAO.getNewGoods();
	}
	
	@Override
	public ArrayList<Goods> getNewGoodsByType(int typeID){
		return goodsDAO.getNewGoodsByType(typeID);
	}
	
	@Override
	public ArrayList<Goods> getNewGoodsByStore(int userId) {
		return goodsDAO.getNewGoodsByStore(userId);
	}

	@Override
	public ArrayList<Goods> getDiscountGoods() {
		return  goodsDAO.getDiscountGoods();
	}

	public ArrayList<Goods> getDiscountGoodsByType(int typeID){
		return goodsDAO.getDiscountGoodsByType(typeID);
	}
	
	public ArrayList<Goods> getDiscountGoodsByStore(int userId) {
		return goodsDAO.getDiscountGoodsByStore(userId);
	}
	
	@Override
	public ArrayList<Goods> getHotGoods() {
		return  goodsDAO.getHotGoods();
	}
	
	@Override
	public ArrayList<Goods> getHotGoodsByType(int typeID) {
		return goodsDAO.getHotGoodsByType(typeID);
	}

	@Override
	public ArrayList<Goods> getGoodsByTypeID(int typeID) {
		return goodsDAO.getGoodsByTypeID(typeID);
	}

	@Override
	public ArrayList<Goods> getHotGoodsByTypeID(int typeID) {
		return goodsDAO.getHotGoodsByTypeID(typeID);
	}
	
	@Override
	public ArrayList<Goods> getHotGoodsByStore(int userId) {
		return goodsDAO.getHotGoodsByStore(userId);
	}

	@Override
	public ArrayList<Goods> getHotGoodsBySuperID(int superID) {
		return goodsDAO.getHotGoodsBySuperID(superID);
	}

	@Override
	public String saveImage(MultipartFile image) throws IOException{
		int doPos = image.getOriginalFilename().lastIndexOf(".");
		
		if(doPos < 0) {
			return null;
		}
		String imageExt = image.getOriginalFilename().substring(doPos+1).toLowerCase();
		if(!UploadFileUtil.isImageAllowed(imageExt)) {
			return null;
		}
		
		String imageName = UUID.randomUUID().toString().replaceAll("-", "") + "." + imageExt;
		Files.copy(image.getInputStream(), new File(UploadFileUtil.IMAGE_DIR + imageName).toPath(),
				StandardCopyOption.REPLACE_EXISTING);
		return imageName;
	}

	@Override
	public String getPicture(int id) {
		return goodsDAO.getPicture(id);
	}

	@Override
	public int setPicture(int id, String picture) {
		return goodsDAO.setPicture(id, picture);
	}

	@Override
	public int updateHit(int id, int hit) {
		return goodsDAO.updateHit(id, hit);
	}

	@Override
	public int getBuy(int id) {
		return goodsDAO.getBuy(id);
	}

	@Override
	public int setBuy(int id, int buy) {
		return goodsDAO.setBuy(id, buy);
	}

	@Override
	public int getStatus(int id) {
		return goodsDAO.getStatus(id);
	}

	@Override
	public int setStatus(int id, int status) {
		return goodsDAO.setStatus(id, status);
	}

	@Override
	public int getUserId(int id) {
		return goodsDAO.getUserId(id);
	}

	@Override
	public ArrayList<Goods> getGoodsByKeyword(String keyword, int start, int number) {
		
		ArrayList<Goods> list = new ArrayList<Goods>();
		
		keyword = keyword.replaceAll("\\pP", " ").trim();
		keyword = keyword.replaceAll("\\s+", "+ ");
		
		list = goodsDAO.getGoodsByKeyword("+"+keyword, start, number);
		
		/*String[] key = keyword.split(" ");
		
		for(String s : key) {
			//subtypeDAO
		}*/
		
		return list;
	}
	
	@Override
	public int getCountByKeyword(String keyword) {
		return goodsDAO.getCountByKeyword(keyword);
	}

	@Override
	public int setInfo(Goods goods) {
		return goodsDAO.setInfo(goods);
	}

	@Override
	public int getMaxId() {
		return goodsDAO.getMaxId();
	}

	@Override
	public int addComment(Comment comment) {
		return commentDAO.addComment(comment);
	}

	@Override
	public ArrayList<Comment> getCommentsByGoodsId(int goodsId, int start, int number) {
		return commentDAO.getCommentsByGoodsId(goodsId, start, number);
	}

	@Override
	public ArrayList<Comment> getCommentsByUserid(int userId) {
		return commentDAO.getCommentsByUserid(userId);
	}

	@Override
	public int getCommentCountByGoodsId(int id) {
		return commentDAO.getCommentCountByGoodsId(id);
	}

	@Override
	public ArrayList<Goods> getAllByStore(int userId, int start, int num) {
		return goodsDAO.getAllByStore(userId, start, num);
	}

	@Override
	public int getGoodsNumByStore(int userId) {
		return goodsDAO.getGoodsNumByStore(userId);
	}

	@Override
	public int getGoodsNum(int id) {
		return goodsDAO.getGoodsNum(id);
	}

	@Override
	public int setGoodsNum(int id, int num) {
		return goodsDAO.setGoodsNum(id, num);
	}
	
}
