package com.maomi.controller;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.maomi.base.ResponseBase;
import com.maomi.constants.Constants;
import com.maomi.domain.MbUser;
import com.maomi.feign.MemberServiceFeign;
import com.sun.research.ws.wadl.Method;

@Controller
public class RegisterController {

	@Autowired
	private MemberServiceFeign memberServiceFeign;
	
	private static final String REGISTER = "register";
	
	private static final String LOGIN = "login";
	
	@RequestMapping(value="/register",method = RequestMethod.GET)
	public String register(){
		return REGISTER;
	}
	
	@RequestMapping(value="/register",method = RequestMethod.POST)
	public String register(MbUser user,HttpServletRequest request){
		if(StringUtils.isEmpty(user.getUsername())){
			request.setAttribute(Constants.ERROR, "用户名不能为空");
			return REGISTER;
		}
		if(StringUtils.isEmpty(user.getPassword())){
			request.setAttribute(Constants.ERROR, "密码不能为空");
			return REGISTER;
		}
		ResponseBase resultData = memberServiceFeign.register(user);
		if(!resultData.getCode().equals(Constants.HTTP_RES_CODE_200)){
			request.setAttribute(Constants.ERROR, "注册失败");
			return REGISTER;
		}
		return LOGIN;
	}
}
