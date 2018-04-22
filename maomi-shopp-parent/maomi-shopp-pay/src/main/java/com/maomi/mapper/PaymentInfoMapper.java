package com.maomi.mapper;

import com.maomi.domain.PaymentInfo;

public interface PaymentInfoMapper {

    int insertPaymentInfo(PaymentInfo payInfo);

    PaymentInfo queryPaymentInfoById(Integer id);
    
    PaymentInfo queryPaymentInfoByOrderNumber(String orderNumber);
    
    int updatePayMentInfo(PaymentInfo payInfo);
}