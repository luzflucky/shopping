package com.maomi.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

import com.maomi.api.service.PaymentInfoService;

@Component
@FeignClient("pay")
public interface PayServiceFeign extends PaymentInfoService{

}
