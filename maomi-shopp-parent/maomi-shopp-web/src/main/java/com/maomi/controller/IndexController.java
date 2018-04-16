package com.maomi.controller;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maomi.base.ResponseBase;
import com.maomi.constants.Constants;
import com.maomi.domain.MbUser;
import com.maomi.feign.MemberServiceFeign;
import com.maomi.util.CookieUtil;

@Controller
public class IndexController {

	@Autowired
	private MemberServiceFeign memberServiceFeign;
	
	private static final String INDEX = "index";
	
	private static final String LOGIN = "login";
	
	@RequestMapping("/")
	public String index(HttpServletRequest request){
		String token = CookieUtil.getCookie(request, Constants.MEMBER_TOKEN);
		if(StringUtils.isEmpty(token)){
			return LOGIN;
		}
		ResponseBase rootData = memberServiceFeign.queryUserByToken(token);
		if(rootData.getCode().equals(Constants.HTTP_RES_CODE_200)){
			LinkedHashMap resultData = (LinkedHashMap) rootData.getData();
			String username = (String) resultData.get(Constants.USER_NAME);
			request.setAttribute(Constants.USER_NAME, username);
		}
		return INDEX;
	}
	
}
