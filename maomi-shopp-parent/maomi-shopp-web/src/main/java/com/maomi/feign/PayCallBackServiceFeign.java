package com.maomi.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

import com.maomi.api.service.PayCallBackService;

@Component
@FeignClient("pay")
public interface PayCallBackServiceFeign extends PayCallBackService{

}
