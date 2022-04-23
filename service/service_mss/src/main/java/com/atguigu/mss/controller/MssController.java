package com.atguigu.mss.controller;

import com.atguigu.commonutils.Result;
import com.atguigu.commonutils.util.RandomUtil;
import com.atguigu.mss.service.MssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author: Niebelungen
 * @create: 2022/4/15-9:53
 * @VERSION: 1.0
 */
@RestController
@RequestMapping("/edumss/mss")
//@CrossOrigin
public class MssController {

    @Autowired
    MssService mssService;
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @GetMapping("send/{phonenumber}")
    public Result sendMss(@PathVariable("phonenumber") String phonenumber){
        //设置验证码过期时间
        String code = redisTemplate.opsForValue().get(phonenumber);
        if(!StringUtils.isEmpty(code)){
            return Result.error().message("验证码还未过期，请不要重复发送！");
        }

        //需要生成随机的值，传递给阿里云发送
        code = RandomUtil.getFourBitRandom();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("code",code);
        boolean isOk= mssService.send(hashMap,phonenumber);
         if(isOk){
             redisTemplate.opsForValue().set(phonenumber,code,5, TimeUnit.DAYS);
            return Result.ok();
         }else {
            return Result.error();
         }
    }
}
