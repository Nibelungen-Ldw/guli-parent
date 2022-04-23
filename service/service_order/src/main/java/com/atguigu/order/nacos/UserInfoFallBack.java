package com.atguigu.order.nacos;

import com.atguigu.commonutils.RestCode;
import com.atguigu.commonutils.entity.UcenterMemberCommom;
import com.atguigu.servicebase.exception.MyException;
import org.springframework.stereotype.Component;

/**
 * @author: Niebelungen
 * @create: 2022/4/18-23:10
 * @VERSION: 1.0
 */
@Component
public class UserInfoFallBack implements UserInfo {
    @Override
    public UcenterMemberCommom getUserInfoById(String id) {
        throw  new MyException(RestCode.ERROR,"远程方法调用失败！");
    }
}
