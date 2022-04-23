package com.atguigu.acl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: Niebelungen
 * @create: 2022/4/4-11:09
 * @VERSION: 1.0
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"})
@MapperScan(basePackages ={"com.atguigu.acl.mapper"} )
@EnableFeignClients
@EnableDiscoveryClient
public class AclApp {
    public static void main(String[] args) {
        SpringApplication.run(AclApp.class,args);
    }
}
