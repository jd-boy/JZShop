package com.shop.util;

public class JZShopUtil {
	
	
	public static int getPageCount(int page, int num, int sum) {
		int pageCount = sum / num;
		if(pageCount % num != 0) {
			pageCount++;
		}
		if(sum < num) {
			pageCount = 1;
		}
		
		return pageCount;
	}
}
