package com.maomi.controller;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.maomi.base.ResponseBase;
import com.maomi.constants.Constants;
import com.maomi.domain.MbUser;
import com.maomi.feign.MemberServiceFeign;
import com.maomi.util.CookieUtil;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;

@Controller
public class LoginController {

	@Autowired
	private MemberServiceFeign memberServiceFeign;
	
	private static final String LOGIN = "login";
	
	private static final String INDEX = "redirect:/";
	
	private static final String QQ_RELATION = "qqrelation";

	@RequestMapping(value="/login",method = RequestMethod.GET)
	public String login(){
		return LOGIN;
	}
	
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public String login(MbUser user,HttpServletRequest request,HttpServletResponse response){
		if(StringUtils.isEmpty(user.getUsername())){
			request.setAttribute(Constants.ERROR, "用户名不能为空");
			return LOGIN;
		}
		if(StringUtils.isEmpty(user.getPassword())){
			request.setAttribute(Constants.ERROR, "密码不能为空");
			return LOGIN;
		}
		ResponseBase rootLogin = memberServiceFeign.login(user);
		if(rootLogin.getCode().equals(Constants.HTTP_RES_CODE_200)){
			LinkedHashMap resultData = (LinkedHashMap) rootLogin.getData();
			String token = (String) resultData.get(Constants.TOKEN);
			if(StringUtils.isEmpty(token)){
				request.setAttribute(Constants.ERROR, "token已经失效");
				return LOGIN;
			}
			CookieUtil.addCookie(Constants.MEMBER_TOKEN, token, null, Constants.COOKIE_TOKEN_TIME, response);
		}
		return INDEX;
	}
	
	@RequestMapping(value="/qqLogin",method = RequestMethod.GET)
	public String qqLogin(HttpServletRequest request){
		String authorizeURL=null;
		try {
			authorizeURL = new Oauth().getAuthorizeURL(request);
		} catch (QQConnectException e) {
			e.printStackTrace();
		}
		return "redirect:"+authorizeURL;
	}
	
	@RequestMapping(value="/qqLoginCallback",method = RequestMethod.GET)
	public String qqLoginCallback(HttpServletRequest request, HttpServletResponse response,HttpSession httpSession){
		AccessToken accessTokenObject =null;
		try {
			//1.获取qq授权accessToken
			accessTokenObject = new Oauth().getAccessTokenByRequest(request);
			String accessToken = accessTokenObject.getAccessToken();
			//2.获取qq授权openId
			OpenID openIdObject = new OpenID(accessToken);
			String openId = openIdObject.getUserOpenID();
			//3.使用openId查询用户信息
			ResponseBase resultOpen = memberServiceFeign.queryUserByOpenId(openId);
			//4.判断是否存在关联的用户
			if(!resultOpen.getCode().equals(Constants.HTTP_RES_CODE_201)){
				httpSession.setAttribute(Constants.OPEN_ID, openId);
				return QQ_RELATION;
			}
			//5.拿到返回的token存到cookie中
			LinkedHashMap resultData = (LinkedHashMap) resultOpen.getData();
			String token = (String) resultData.get(Constants.TOKEN);
			CookieUtil.addCookie(Constants.MEMBER_TOKEN, token, null, Constants.COOKIE_TOKEN_TIME, response);
		} catch (QQConnectException e) {
			e.printStackTrace();
		}
		return INDEX;
	}
	
	@RequestMapping(value="/qqRelation",method = RequestMethod.POST)
	public String qqRelation(MbUser user,HttpServletRequest request, HttpServletResponse response,HttpSession httpSession){
		//1.拿到存在session中的openId
		String openId = (String) httpSession.getAttribute(Constants.OPEN_ID);
		if(StringUtils.isEmpty(openId)){
			request.setAttribute(Constants.ERROR, "openId失效");
		}
		user.setOpenId(openId);
		ResponseBase result = memberServiceFeign.qqLoginRelationOpenId(user);
		if(!result.getCode().equals(Constants.HTTP_RES_CODE_200)){
			request.setAttribute(Constants.ERROR, "账号或密码错误");
		}
		LinkedHashMap resultData = (LinkedHashMap) result.getData();
		String token = (String) resultData.get(Constants.TOKEN);
		if(StringUtils.isEmpty(token)){
			request.setAttribute(Constants.ERROR, "回话已失效");
		}
		CookieUtil.addCookie(Constants.MEMBER_TOKEN, token, null, Constants.COOKIE_TOKEN_TIME, response);
		return INDEX;
	}
}
