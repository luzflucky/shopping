package com.maomi.fegin;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

import com.maomi.service.OrderService;

@Component
@FeignClient("order")
public interface OrderServiceFegin  extends OrderService{

}
