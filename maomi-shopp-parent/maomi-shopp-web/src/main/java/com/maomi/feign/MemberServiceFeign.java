package com.maomi.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

import com.maomi.api.service.MbUserService;

@Component
@FeignClient("member")
public interface MemberServiceFeign extends MbUserService{

}
