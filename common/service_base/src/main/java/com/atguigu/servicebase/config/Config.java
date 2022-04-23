package com.atguigu.servicebase.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Niebelungen
 * @create: 2022/4/4-11:11
 * @VERSION: 1.0
 */
@Configuration
public class Config {
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }


}
