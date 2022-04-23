package com.atguigu.statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author: Niebelungen
 * @create: 2022/4/4-11:09
 * @VERSION: 1.0
 */
@SpringBootApplication
//项目外的实列，都需要扫描包
@ComponentScan(basePackages = {"com.atguigu"})
@EnableFeignClients(basePackages = {"com.atguigu"})
@EnableDiscoveryClient
@EnableScheduling
public class StatisticsApp {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsApp.class,args);
    }
}
