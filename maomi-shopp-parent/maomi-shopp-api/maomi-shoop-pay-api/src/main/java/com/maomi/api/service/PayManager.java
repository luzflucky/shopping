package com.maomi.api.service;

import com.maomi.domain.PaymentInfo;

public interface PayManager {

	String payInfo(PaymentInfo payInfo) throws Exception;
}
