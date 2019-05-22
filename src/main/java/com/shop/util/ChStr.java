package com.shop.util;

import java.io.UnsupportedEncodingException;

/*
 *  功能：解决中文乱码问题
 */

public class ChStr {
	
	public static String chStr(String str) {
		if(str == null) {
			str = "";
		} else {
			try {
				str = (new String(str.getBytes("iso-8859-1"), "UTF-8")).trim();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return str;
	}
}
