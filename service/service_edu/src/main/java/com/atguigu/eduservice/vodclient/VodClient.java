package com.atguigu.eduservice.vodclient;

import com.atguigu.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @author: Niebelungen
 * @create: 2022/4/13-11:57
 * @VERSION: 1.0
 */
@Component
@FeignClient(name = "service-vod",fallback = VodClientFallBack.class)
public interface VodClient {

    @DeleteMapping("/eduvod/video/deleteVideo/{videoId}")
    public Result deleteVideo(@PathVariable("videoId") String videoId);

    @DeleteMapping("/eduvod/video/deleteBathVideo")
    public Result deleteBathVideo( @RequestParam("videoIdList") List<String> videoIdList);

}
