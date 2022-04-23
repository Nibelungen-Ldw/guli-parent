package com.atguigu.statistics.service;

import com.atguigu.statistics.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author liudewang
 * @since 2022-04-19
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void dailyRegistMemb(String date);

    Map<String, Object> getShowData(String type, String begain, String end);
}
