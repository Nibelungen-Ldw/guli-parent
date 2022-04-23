package com.atguigu.statistics.schedule;

import com.atguigu.commonutils.util.DateUtil;
import com.atguigu.statistics.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: Niebelungen
 * @create: 2022/4/20-16:26
 * @VERSION: 1.0
 */
@Component
public class ScheduleTask {

    @Autowired
    StatisticsDailyService statisticsDailyService;
    @Scheduled(cron = "0/5 * * * * ?")
    public void testSchedule(){
        System.out.println("******测试任务调度");
    }
    @Scheduled(cron = "* * * 1 * ?")
    public void dailyRegistMemb(){
        statisticsDailyService.dailyRegistMemb
                (DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }

}
