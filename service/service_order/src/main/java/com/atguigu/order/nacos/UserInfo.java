package com.atguigu.order.nacos;

import com.atguigu.commonutils.entity.UcenterMemberCommom;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: Niebelungen
 * @create: 2022/4/18-23:09
 * @VERSION: 1.0
 */
@Component
@FeignClient(name = "service-ucenter",fallback = UserInfoFallBack.class)
public interface UserInfo {
    @GetMapping("/educenter/member/getUserInfoById/{id}")
    public UcenterMemberCommom getUserInfoById(@PathVariable("id") String id);
}
