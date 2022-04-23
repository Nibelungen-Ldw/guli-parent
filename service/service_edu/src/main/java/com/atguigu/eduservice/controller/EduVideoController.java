package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.RestCode;
import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.eduservice.vodclient.VodClient;
import com.atguigu.servicebase.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author liudewang
 * @since 2022-04-09
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private VodClient vodClient;
    @PostMapping("addVideo")
    public Result addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return Result.ok();

    }
    @PostMapping("editVideo")
    public Result editVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById(eduVideo);
        return Result.ok();

    }

    @DeleteMapping("removeVideo/{videoId}")
    public Result removeVideo(@PathVariable String videoId){
        EduVideo eduVideo = eduVideoService.getById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            Result result = vodClient.deleteVideo(videoSourceId);

            if(result.getCode()==20001){
                throw  new MyException(RestCode.ERROR,"删除视频失败hystrix");
            }

        }
        //先删除视频再删除小节
        eduVideoService.removeById(videoId);
        return Result.ok();

    }
    @GetMapping("getVideo/{videoid}")
    public Result getVideo(@PathVariable String videoid){
        EduVideo eduVideo = eduVideoService.getById(videoid);
        return Result.ok().data("eduVideo",eduVideo);

    }


}

