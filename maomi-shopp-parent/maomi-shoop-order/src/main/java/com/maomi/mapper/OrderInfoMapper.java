package com.maomi.mapper;

import java.util.Map;

import com.maomi.domain.OrderInfo;

public interface OrderInfoMapper {

    int insertOrderInfo(OrderInfo record);
    
    OrderInfo queryOrderByOrderNumber(String orderNumber);

    int updateOrderByOrderNumber(Map record);
    
    int updateOrderInfo(OrderInfo order);
}