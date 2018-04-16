package com.maomi.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

import com.maomi.api.service.MemberService;
import com.maomi.base.BaseController;
import com.maomi.base.ResponseBase;

@RestController
public class MemberServiceImpl extends BaseController implements MemberService {

	@Override
	public Map<String, Object> testRest() {
		Map<String, Object> result = new HashMap<>();
		result.put("errorCode", "200");
		result.put("errorMsg", "success");
		return result;
	}

	@Override
	public ResponseBase addRedis(String key, String value) {
		baseRedisService.setString(key, value);
		return setResultSuccess();
	}

}