package com.atguigu.eduservice.vodclient;

import com.atguigu.commonutils.Result;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Niebelungen
 * @create: 2022/4/13-22:19
 * @VERSION: 1.0
 */
//调用端服务降级
@Component
public class VodClientFallBack implements VodClient {
    @Override
    public Result deleteVideo(String videoId) {
        return Result.error().message("删除视频出错了");
    }

    @Override
    public Result deleteBathVideo(List<String> videoIdList) {
        return Result.error().message("删除多个视频出错了");
    }
}
