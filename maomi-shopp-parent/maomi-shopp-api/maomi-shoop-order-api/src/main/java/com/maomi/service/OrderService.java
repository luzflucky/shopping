package com.maomi.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.maomi.base.ResponseBase;
import com.maomi.domain.OrderInfo;

@RequestMapping("/order")
public interface OrderService {
	
	@RequestMapping("/updateOrderId")
	ResponseBase updateOrderId(@RequestParam("isPay") Integer isPay, @RequestParam("payId") String aliPayId,
			@RequestParam("orderNumber") String orderNumber);
	
	@RequestMapping("/insertOrderInfo")
	ResponseBase insertOrderInfo(@RequestBody OrderInfo order);
	
	

}
