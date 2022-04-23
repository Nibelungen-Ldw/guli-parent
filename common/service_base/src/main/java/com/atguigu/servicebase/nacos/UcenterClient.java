package com.atguigu.servicebase.nacos;

import com.atguigu.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: Niebelungen
 * @create: 2022/4/20-0:00
 * @VERSION: 1.0
 */
@FeignClient(name = "service-ucenter")
@Component
public interface UcenterClient {

    @GetMapping("/educenter/member/getRegsMemb/{date}")
    public Result getRegsMemb(@PathVariable("date") String date);
}
