package com.maomi.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.maomi.base.ResponseBase;
import com.maomi.constants.Constants;
import com.maomi.feign.PayServiceFeign;

@Controller
public class PayInfoController {

	@Autowired
	private PayServiceFeign payServiceFeign;
	
	@RequestMapping(value="/pay",method=RequestMethod.GET)
	public void pay(String payToken,HttpServletRequest request,HttpServletResponse response){
		PrintWriter out=null;
		response.setContentType("text/html;charset=utf-8");
		try {
			out = response.getWriter();
			if(StringUtils.isEmpty(payToken)){
				out.println("error,payToken不能为空");
				return ;
			}
			ResponseBase result = payServiceFeign.payInfo(payToken);
			if(!result.getCode().equals(Constants.HTTP_RES_CODE_200)){
				out.println("ERROR");
				return ;
			}
			LinkedHashMap resultMap = (LinkedHashMap) result.getData();
			String payResult = (String) resultMap.get(Constants.PAY_INFO_HTML);
			out.println(payResult);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(out!=null){
				out.close();
			}
		}
		
	}
	
}
