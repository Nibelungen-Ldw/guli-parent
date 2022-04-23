package com.atguigu.statistics.controller;


import com.atguigu.commonutils.Result;
import com.atguigu.servicebase.nacos.UcenterClient;
import com.atguigu.statistics.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author liudewang
 * @since 2022-04-19
 */
@RestController
@RequestMapping("/edusta/sta")
//@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    StatisticsDailyService statisticsDailyService;

    @PostMapping("dailyRegistMemb/{date}")
    public Result dailyRegistMemb(@PathVariable("date")String date){
        statisticsDailyService.dailyRegistMemb(date);
        return Result.ok();
    }

    @GetMapping("showData/{type}/{begain}/{end}")
    public Result showData(@PathVariable("type")String type,
                           @PathVariable("begain")String begain,
                           @PathVariable("end")String end){
        Map<String,Object> map = statisticsDailyService.getShowData(type,begain,end);
        return Result.ok().data(map);
    }

}

