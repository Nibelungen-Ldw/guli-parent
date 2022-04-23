package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.RestCode;
import com.atguigu.commonutils.Result;
import com.atguigu.servicebase.exception.MyException;
import com.atguigu.vod.service.EduVideoService;
import com.atguigu.vod.utils.ConstantPropertiesUtil;
import com.atguigu.vod.utils.VodInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * @author: Niebelungen
 * @create: 2022/4/12-16:52
 * @VERSION: 1.0
 */
@RequestMapping("/eduvod/video")
//@CrossOrigin
@RestController
public class VodController {
    @Autowired
    private EduVideoService eduVideoService;

    @PostMapping("uploadVideo")
    public Result uploadVideo(MultipartFile file){
        String videoId =  eduVideoService.uploadVideoAliyun(file);
        return Result.ok().data("videoId",videoId);
    }

    @DeleteMapping("deleteVideo/{videoId}")
    public Result deleteVideo(@PathVariable String videoId){
        try {
            DefaultAcsClient client = VodInit.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            DeleteVideoResponse response = new DeleteVideoResponse();
            request.setVideoIds(videoId);
             response = client.getAcsResponse(request);
            //涉及调用微服务服务,删除数据库数据（也可前端页面实现）

            return Result.ok();

        } catch (ClientException e) {
            e.printStackTrace();
            throw new MyException(RestCode.ERROR,"删除视频失败");
        }
    }


    @DeleteMapping("deleteBathVideo")
    public Result deleteBathVideo( @RequestParam("videoIdList") List<String> videoIdList){
        eduVideoService.deleteBathVideo(videoIdList);
        return Result.ok();
    }

    @GetMapping("getPlayAuto/{videoid}")
    public Result getPlayAuto( @PathVariable("videoid") String videoid){
        try {
            DefaultAcsClient client = VodInit.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
            request.setVideoId(videoid);
            request.setAuthInfoTimeout(2000L);
            response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return Result.ok().data("playAuth",playAuth);
        } catch (ClientException e) {
            throw new MyException(RestCode.ERROR,"获取凭证失败");
        }


    }

}
