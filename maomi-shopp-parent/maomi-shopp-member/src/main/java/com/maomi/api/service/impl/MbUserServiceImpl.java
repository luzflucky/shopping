package com.maomi.api.service.impl;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.maomi.api.service.MbUserService;
import com.maomi.base.BaseController;
import com.maomi.base.ResponseBase;
import com.maomi.constants.Constants;
import com.maomi.domain.MbUser;
import com.maomi.mapper.MbUserMapper;
import com.maomi.mq.RegisterMailboxProducer;
import com.maomi.util.MD5Util;
import com.maomi.util.TokenUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MbUserServiceImpl extends BaseController implements MbUserService{

	@Value("${messages.queue}")
	private String MESSAGESQUEUE;
	
	@Autowired
	private MbUserMapper mbUserMapper;
	
	@Autowired
	private RegisterMailboxProducer registerMailboxProducer;
	
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
	@Override
	public ResponseBase register(@RequestBody MbUser user) {
		String password = user.getPassword();
		if(StringUtils.isEmpty(password)){
			return setResultError("密码不能为空！");
		}
		user.setPassword(MD5Util.MD5(password));
		int result = mbUserMapper.insertMbUser(user);
		if(result<=0){
			return setResultError("注册失败!");
		}
		String email = user.getEmail();
		if(StringUtils.isEmpty(email)){
			return setResultError("邮箱为空,注册失败!");
		}else{
			String json = message(email);
			log.info("#############邮件发送中{}",json);
			sendMessage(json);
		}
		return setResultSuccess("注册成功！");
	}

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
	@Override
	public ResponseBase queryUserByToken(@RequestParam("token") String token) {
		if(StringUtils.isEmpty(token)){
			return setResultError("token不能为空");
		}
		String userId = baseRedisService.getString(token);
		if(StringUtils.isEmpty(userId)){
			return setResultError("token已经失效");
		}
		Long id = Long.parseLong(userId);
		MbUser mbUser = mbUserMapper.queryUserById(id);
		if(mbUser==null){
			return setResultError("token已失效");
		}
		mbUser.setPassword(null);
		return setResultSuccess(mbUser);
	}
	

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
	@Override
	public ResponseBase login(@RequestBody MbUser user) {
		if(StringUtils.isEmpty(user.getUsername())){
			return setResultError("登录失败,用户名不能为空");
		}
		String password = MD5Util.MD5(user.getPassword());
		user.setPassword(password);
		MbUser is_user = mbUserMapper.queryUserByLogin(user);
		if(is_user==null){
			return setResultError("登录失败,用户名或密码错误");
		}
		String value = String.valueOf(is_user.getId());
		JSONObject result = setRedisToken(value);
		return setResultSuccess(result);
	}
	
	/**
	 * 
	 * @描述:	  将userId存入token
	 * @方法名: setRedisToken
	 * @param token
	 * @param value
	 * @return
	 * @返回类型 JSONObject
	 * @创建人 zflu
	 * @创建时间 2018年4月14日下午5:32:52
	 */
	private JSONObject setRedisToken(String value){
		String token = TokenUtils.getToken();
		baseRedisService.setString(token,value, Constants.TOKEN_TIME);
		JSONObject result = new JSONObject();
		result.put(Constants.TOKEN, token);
		return result;
	}
	
	
	/**
	 * 
	 * @描述:  用于组装邮件
	 * @方法名: message
	 * @param email
	 * @return
	 * @返回类型 String
	 * @创建人 zflu
	 * @创建时间 2018年4月11日下午9:30:50
	 */
	public String message(String email){
		JSONObject root = new JSONObject();
		JSONObject header = new JSONObject();
		JSONObject content = new JSONObject();
		content.put(Constants.MSG_EMAIL, email);
		header.put(Constants.INTERFACE_TYPE, Constants.MSG_EMAIL);
		root.put(Constants.HEADER,header);
		root.put(Constants.CONTENT, content);
		return root.toJSONString();
	}
	
	/**
	 * 
	 * @描述:	  发送短信
	 * @方法名: sendMessage
	 * @param json
	 * @返回类型 void
	 * @创建人 zflu
	 * @创建时间 2018年4月11日下午9:32:59
	 */
	public void sendMessage(String json){
		ActiveMQQueue activeMQQueue = new ActiveMQQueue(MESSAGESQUEUE);
		registerMailboxProducer.sendMsg(activeMQQueue, json);
	}

	/**
	 * 
	 * @描述:  根据openId查询用户
	 * @方法名: queryUserByOpenId
	 * @param openId
	 * @return
	 * @返回类型 ResponseBase
	 * @创建人 zflu
	 * @创建时间 2018年4月14日下午5:20:46
	 */
	@Override
	public ResponseBase queryUserByOpenId(@RequestParam("openId") String openId) {
		if(StringUtils.isEmpty(openId)){
			return setResultError("openId不能为空");
		}
		MbUser user = mbUserMapper.queryUserByOpenId(openId);
		if(user == null){
			return setResultError("openId没有关联用户");
		}
		String value = String.valueOf(user.getId());
		JSONObject result = setRedisToken(value);
		return setResultQqSuccess(result);
	}

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
	@Override
	public ResponseBase qqLoginRelationOpenId(@RequestBody MbUser user) {
		String openId = user.getOpenId();
		if(StringUtils.isEmpty(openId)){
			return setResultError("关联失败,openId不能为空");
		}
		if(StringUtils.isEmpty(user.getUsername())){
			return setResultError("关联失败,用户名不能为空");
		}
		String password = MD5Util.MD5(user.getPassword());
		user.setPassword(password);
		MbUser loginUser = mbUserMapper.queryUserByLogin(user);
		if(loginUser==null){
			return setResultError("关联失败，账号或密码错误");
		}
		loginUser.setOpenId(openId);
		int updateUser= mbUserMapper.updateUserOpenId(loginUser);
		if(updateUser<=0){
			return setResultError("关联失败");
		}
		String value = String.valueOf(loginUser.getId());
		JSONObject result = setRedisToken(value);
		return setResultSuccess(result);
	}
	
	

}
