package com.maomi.api.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.maomi.base.ResponseBase;
import com.maomi.domain.MbUser;

@RequestMapping("/member")
public interface MbUserService {

	/**
	 * 
	 * @描述:  用户注册
	 * @方法名: register
	 * @param user
	 * @return
	 * @返回类型 ResponseBase
	 * @创建人 zflu
	 * @创建时间 2018年4月12日 15:37:38
	 */
	@RequestMapping("/register")
	ResponseBase register(@RequestBody MbUser user);
	
	/**
	 * 
	 * @描述:  使用token查询用户信息
	 * @方法名: register
	 * @param token
	 * @return
	 * @返回类型 ResponseBase
	 * @创建人 zflu
	 * @创建时间 2018年4月12日 15:37:38
	 */
	@RequestMapping("/queryUserByToken")
	ResponseBase queryUserByToken(@RequestParam("token") String token);
	
	/**
	 * 
	 * @描述:  用户登录
	 * @方法名: login
	 * @param user
	 * @return
	 * @返回类型 ResponseBase
	 * @创建人 zflu
	 * @创建时间 2018年4月12日 15:37:38
	 */
	@RequestMapping("/login")
	ResponseBase login(@RequestBody MbUser user);
	
	/**
	 * 
	 * @描述:  
	 * @方法名: queryUserByOpenId
	 * @param openId
	 * @return
	 * @返回类型 ResponseBase
	 * @创建人 zflu
	 * @创建时间 2018年4月14日下午5:20:46
	 */
	@RequestMapping("/queryUserByOpenId")
	ResponseBase queryUserByOpenId(@RequestParam("openId") String openId);
	
	/**
	 * 
	 * @描述:  用户使用qq openId关联用户信息
	 * @方法名: qqLoginRelationOpenId
	 * @param user
	 * @return
	 * @返回类型 ResponseBase
	 * @创建人 zflu
	 * @创建时间 2018年4月14日下午5:35:35
	 */
	@RequestMapping("/qqLoginRelationOpenId")
	ResponseBase qqLoginRelationOpenId(@RequestBody MbUser user);
}
