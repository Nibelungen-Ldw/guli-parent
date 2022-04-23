package com.atguigu.eduservice.ucenterclient;

import com.atguigu.commonutils.RestCode;
import com.atguigu.commonutils.entity.UcenterMemberCommom;
import com.atguigu.servicebase.exception.MyException;
import org.springframework.stereotype.Component;

/**
 * @author: Niebelungen
 * @create: 2022/4/18-17:40
 * @VERSION: 1.0
 */
@Component
public class UcenterFallBack implements Ucenter {
    @Override
    public UcenterMemberCommom getUserInfoById(String id) {
        throw new MyException(RestCode.ERROR,"调用查询用户信息出错了");
    }
}
