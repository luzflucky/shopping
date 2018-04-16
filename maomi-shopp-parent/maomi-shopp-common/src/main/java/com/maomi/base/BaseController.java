package com.maomi.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maomi.constants.Constants;

@Component
public class BaseController {
	
	@Autowired
	protected BaseRedisService baseRedisService;

	// 返回成功 ,data值为null
	public ResponseBase setResultSuccess() {
		return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, null);
	}
	// 返回成功 ,data可传
	public ResponseBase setResultSuccess(Object data) {
		return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, data);
	}
	// 返回成功 ,data可传
	public ResponseBase setResultQqSuccess(Object data) {
		return setResult(Constants.HTTP_RES_CODE_201, Constants.HTTP_RES_CODE_200_VALUE, data);
	}
	// 返回失败
	public ResponseBase setResultError(String msg){
		return setResult(Constants.HTTP_RES_CODE_500,msg, null);
	}
	// 自定义返回结果
	public ResponseBase setResult(Integer code, String msg, Object data) {
		ResponseBase responseBase = new ResponseBase();
		responseBase.setCode(code);
		responseBase.setMsg(msg);
		if (data != null)
			responseBase.setData(data);
		return responseBase;
	}

}
