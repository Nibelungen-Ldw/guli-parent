package com.atguigu.eduservice.vodclient;

import com.atguigu.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: Niebelungen
 * @create: 2022/4/19-22:15
 * @VERSION: 1.0
 */
@Component
@FeignClient(name = "service-order")
public interface OrderClient {

    @GetMapping("/eduorder/order/isBuyCourse/{courseid}/{memberid}")
    public boolean isBuyCourse(@PathVariable("courseid") String courseid,
                              @PathVariable("memberid") String memberid);
}
