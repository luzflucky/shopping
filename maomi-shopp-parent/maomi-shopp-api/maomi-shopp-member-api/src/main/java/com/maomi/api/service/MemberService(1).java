package com.maomi.api.service;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;

import com.maomi.base.ResponseBase;

@RequestMapping("/member")
public interface MemberService {
	@RequestMapping("/testRest")
	public Map<String, Object> testRest();
	
	@RequestMapping("/addRedis")
	public ResponseBase addRedis(String key,String value);
}
