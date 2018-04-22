package com.maomi.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.maomi.base.BaseController;
import com.maomi.base.ResponseBase;
import com.maomi.domain.OrderInfo;
import com.maomi.mapper.OrderInfoMapper;
import com.maomi.service.OrderService;

@RestController
public class OrderServiceImpl extends BaseController implements OrderService{

	@Autowired
	private OrderInfoMapper orderInfoMapper;
	
	@Override
	public ResponseBase updateOrderId(Integer isPay, String aliPayId, String orderNumber) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("isPay", isPay);
		map.put("aliPayId", aliPayId);
		map.put("orderNumber", orderNumber);
		int result = orderInfoMapper.updateOrderByOrderNumber(map);
		if (result <= 0) {
			return setResultError("系统错误!");
		}
		return setResultSuccess();
	}

	@Override
	public ResponseBase insertOrderInfo(OrderInfo order) {
		int result = orderInfoMapper.insertOrderInfo(order);
		if(result<=0){
			setResultError("系统错误");
		}
		return setResultSuccess();
	}

}
