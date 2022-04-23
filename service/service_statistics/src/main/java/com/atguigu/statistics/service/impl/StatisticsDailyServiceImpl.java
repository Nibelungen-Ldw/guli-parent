package com.atguigu.statistics.service.impl;

import com.atguigu.commonutils.Result;
import com.atguigu.commonutils.util.RandomUtil;
import com.atguigu.servicebase.nacos.UcenterClient;
import com.atguigu.statistics.entity.StatisticsDaily;
import com.atguigu.statistics.mapper.StatisticsDailyMapper;
import com.atguigu.statistics.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author liudewang
 * @since 2022-04-19
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    UcenterClient ucenterClient;

    @Override
    public void dailyRegistMemb(String date) {
        Result regsMemb = ucenterClient.getRegsMemb(date);
        Integer count = (Integer) regsMemb.getData().get("count");
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(count);
        statisticsDaily.setDateCalculated(date);

        statisticsDaily.setCourseNum(Integer.parseInt(RandomUtil.getFourBitRandom()));
        statisticsDaily.setLoginNum(Integer.parseInt(RandomUtil.getFourBitRandom()));
        statisticsDaily.setVideoViewNum(Integer.parseInt(RandomUtil.getFourBitRandom()));
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",date);
        baseMapper.delete(wrapper);
        baseMapper.insert(statisticsDaily);

    }

    @Override
    public Map<String, Object> getShowData(String type, String begain, String end) {


        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.select("date_calculated",type);
        wrapper.between("date_calculated",begain,end);
        List<StatisticsDaily> dailyList = baseMapper.selectList(wrapper);
        //list=>数组；MAP，对象=>json
        ArrayList<String> datelist = new ArrayList<>();
        ArrayList<Integer> typelist = new ArrayList<>();
        for(StatisticsDaily statisticsDaily : dailyList){
            datelist.add(statisticsDaily.getDateCalculated());
           switch (type){
               case "login_num" : typelist.add(statisticsDaily.getLoginNum());
                   break;
               case "video_view_num" : typelist.add(statisticsDaily.getVideoViewNum());
                   break;
               case "course_num" : typelist.add(statisticsDaily.getCourseNum());
                   break;
               case "register_num" : typelist.add(statisticsDaily.getRegisterNum());
                   break;
               default:
                   break;

           }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("datelist",datelist);
        map.put("typelist",typelist);

        return map;
    }
}
