package com.maomi.util;

import java.util.UUID;

import com.maomi.constants.Constants;

public class TokenUtils {
	
	 public static String getToken(){
		 return Constants.MEMBER_TOKEN+UUID.randomUUID();
	 }	

}
