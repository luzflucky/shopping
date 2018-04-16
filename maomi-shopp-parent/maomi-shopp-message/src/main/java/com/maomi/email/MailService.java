package com.maomi.email;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.maomi.adapter.MessageAdapter;
import com.maomi.constants.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailService implements MessageAdapter{

	@Value("${msg.subject}")
	private String subject;
	
	@Value("${msg.text}")
	private String text;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void sendMsg(JSONObject body) {
		log.info("######email开始发送######"+body.toJSONString());
		String email = body.getString(Constants.MSG_EMAIL);
		if (StringUtils.isEmpty(email)) {
			return;
		}
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(email);
		simpleMailMessage.setTo(email);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(text);
		mailSender.send(simpleMailMessage);
	}

}
