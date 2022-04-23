package com.atguigu.cannal;


import com.atguigu.cannal.client.CanalClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * @author: Niebelungen
 * @create: 2022/4/14-11:32
 * @VERSION: 1.0
 */
@SpringBootApplication
public class CannalApp implements CommandLineRunner {
    @Resource
    private CanalClient canalClient;
    public static void main(String[] args) {
        SpringApplication.run(CannalApp.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        canalClient.run();
    }
}
