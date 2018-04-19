package com.maomi.mapper;

import com.maomi.domain.PaymentInfo;

public interface PaymentInfoMapper {

    int insertPaymentInfo(PaymentInfo payInfo);

    PaymentInfo queryPaymentInfoById(Integer id);
}