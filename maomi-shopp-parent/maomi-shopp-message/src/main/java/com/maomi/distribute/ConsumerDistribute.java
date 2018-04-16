package com.maomi.distribute;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.maomi.adapter.MessageAdapter;
import com.maomi.constants.Constants;
import com.maomi.email.MailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ConsumerDistribute {

	@Autowired
	private MailService mailService;
	
	private MessageAdapter messageAdapter;
	
	@JmsListener(destination = "messages_queue")
	public void distribute(String json){
		log.info("############消息解析现在开始############");
		if(StringUtils.isEmpty(json)){
			return ;
		}
		JSONObject root = JSONObject.parseObject(json);
		JSONObject header = root.getJSONObject(Constants.HEADER);
		String interfaceType = header.getString(Constants.INTERFACE_TYPE);
		if(StringUtils.isEmpty(interfaceType)){
			return ;
		}
		if(interfaceType.equals(Constants.MSG_EMAIL)){
			messageAdapter = mailService;
		}
		if(messageAdapter==null){
			return ;
		}
		JSONObject body = root.getJSONObject(Constants.CONTENT);
		messageAdapter.sendMsg(body);
	}
}
