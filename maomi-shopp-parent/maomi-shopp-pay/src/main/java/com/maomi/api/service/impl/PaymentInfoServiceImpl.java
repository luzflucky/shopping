package com.maomi.api.service.impl;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.maomi.api.service.PayManager;
import com.maomi.api.service.PaymentInfoService;
import com.maomi.base.BaseController;
import com.maomi.base.ResponseBase;
import com.maomi.constants.Constants;
import com.maomi.domain.PaymentInfo;
import com.maomi.mapper.PaymentInfoMapper;
import com.maomi.util.TokenUtils;

@RestController
public class PaymentInfoServiceImpl extends BaseController implements PaymentInfoService{

	@Autowired
	private PaymentInfoMapper paymentInfoMapper;
	
	@Autowired
	private AliBaBaManagerImpl aliBaBaManagerImpl;
	
	@Override
	public ResponseBase insertPaymentInfo(@RequestBody PaymentInfo payInfo) {
		int resultPayment = paymentInfoMapper.insertPaymentInfo(payInfo);
		if(resultPayment<=0){
			setResultError("系统错误");
		}
		String payToken = TokenUtils.getPayToken();
		String id = String.valueOf(payInfo.getId());
		baseRedisService.setString(payToken,id, Constants.PAY_TOKEN_TIME);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(Constants.PAY_TOKEN, payToken);
		return setResultSuccess(jsonObject);
	}

	@Override
	public ResponseBase payInfo(@RequestParam("payToken") String payToken) {
		if(StringUtils.isEmpty(payToken)){
			return setResultError("token不能为空");
		}
		String id = baseRedisService.getString(payToken);
		if(StringUtils.isEmpty(id)){
			return setResultError("支付已超时");
		}
		PaymentInfo payment = paymentInfoMapper.queryPaymentInfoById(Integer.valueOf(id));
		if(payment==null){
			return setResultError("未找到交易类型");
		}
		Integer typeId = payment.getTypeid();
		PayManager payManager = null;
		if(typeId==1){
			payManager = aliBaBaManagerImpl;
		}
		try {
			String payInfoHtml = payManager.payInfo(payment);
			JSONObject payInfoJSON = new JSONObject();
			payInfoJSON.put(Constants.PAY_INFO_HTML, payInfoHtml);
			return setResultSuccess(payInfoJSON);

		} catch (Exception e) {
			return setResultError("支付错误!");
		}
	}

}
