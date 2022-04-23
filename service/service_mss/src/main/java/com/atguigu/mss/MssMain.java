package com.atguigu.mss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: Niebelungen
 * @create: 2022/4/14-11:32
 * @VERSION: 1.0
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan("com.atguigu")
//@EnableDiscoveryClient
public class MssMain {
    public static void main(String[] args) {
        SpringApplication.run(MssMain.class,args);
    }
}
