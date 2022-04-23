package com.atguigu.eduservice.ucenterclient;

import com.atguigu.commonutils.entity.UcenterMemberCommom;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: Niebelungen
 * @create: 2022/4/18-17:35
 * @VERSION: 1.0
 */
@Component
@FeignClient(name = "service-ucenter",fallback = UcenterFallBack.class )
public interface Ucenter {

    @GetMapping("/educenter/member/getUserInfoById/{id}")
    public UcenterMemberCommom getUserInfoById(@PathVariable("id") String id);
}
