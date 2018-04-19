package com.maomi.api.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.maomi.base.ResponseBase;
import com.maomi.domain.PaymentInfo;

@RequestMapping("/pay")
public interface PaymentInfoService {

	/**
	 * 
	 * @param payInfo
	 * @return
	 */
	@RequestMapping("/insertPaymentInfo")
	ResponseBase insertPaymentInfo(@RequestBody PaymentInfo payInfo);
	
	@RequestMapping("/payInfo")
	ResponseBase payInfo(@RequestParam("payToken") String payToken);
}
