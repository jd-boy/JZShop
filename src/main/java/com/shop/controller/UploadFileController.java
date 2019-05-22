package com.shop.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shop.model.HostHolder;
import com.shop.serviceImpl.GoodsServiceImpl;
import com.shop.serviceImpl.MemberServiceImpl;
import com.shop.util.JSONStringUtil;
import com.shop.util.UploadFileUtil;

@Controller
public class UploadFileController {
	
    private static final Logger logger = LoggerFactory.getLogger(UploadFileController.class);
	
	@Autowired
	private GoodsServiceImpl goodsServiceImpl;
	
	@Autowired
	private MemberServiceImpl memberServiceImpl;
	
	@Autowired
	private HostHolder hostHolder;
	
	@RequestMapping(path = {"/image"}, method = {RequestMethod.GET})
	public void getImage(@RequestParam("id") int id,
						HttpServletResponse response) {
		try {
			response.setContentType("image/jpeg");
			StreamUtils.copy(new FileInputStream(new File(UploadFileUtil.IMAGE_DIR+goodsServiceImpl.getPicture(id))),
							response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("读取图片错误" + e.getMessage());
		}
	}
	
	@RequestMapping(path = {"/uploadImage"}, method = {RequestMethod.POST})
	@ResponseBody
	public String uploadImage(@RequestParam("image") MultipartFile image) {
		try {
			String imageName = goodsServiceImpl.saveImage(image);
			if(imageName == null) {
				return JSONStringUtil.getJSONString(1, "上传图片失败");
			}
			return JSONStringUtil.getJSONString(0, imageName);
		} catch(Exception e) {
			logger.error("上传图片失败" + e.getMessage());
			return JSONStringUtil.getJSONString(1, "上传图片失败");
		}
	}
	
	@RequestMapping(path = {"/head"}, method = {RequestMethod.GET})
	public void getHead(@RequestParam("id") int id,
						HttpServletResponse response) {
		try {
			response.setContentType("image/jpeg");
			StreamUtils.copy(new FileInputStream(new File(UploadFileUtil.HEAD_DIR+memberServiceImpl.getHeadById(id))),
							response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("读取图片错误" + e.getMessage());
		}
	}
	
	@RequestMapping(path = {"/uploadHead"}, method = {RequestMethod.POST})
	@ResponseBody
	public String uploadHead(@RequestParam("head") MultipartFile image) {
		
		try {
			String imageName = goodsServiceImpl.saveImage(image);
			if(imageName == null) {
				return JSONStringUtil.getJSONString(1, "上传图片失败");
			}
			return JSONStringUtil.getJSONString(0, imageName);
		} catch(Exception e) {
			logger.error("上传图片失败" + e.getMessage());
			return JSONStringUtil.getJSONString(1, "上传图片失败");
		}
	}
	
	@RequestMapping(path = "/staticImg", method = {RequestMethod.GET})
	public void getStatic(@RequestParam("imgName") String imgName,
						HttpServletResponse response) {
		try {
			response.setContentType("image/jpeg");
			StreamUtils.copy(new FileInputStream(new File(UploadFileUtil.STATIC_DIR+imgName)),
							response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("读取图片错误" + e.getMessage());
		}
	}
	
}
