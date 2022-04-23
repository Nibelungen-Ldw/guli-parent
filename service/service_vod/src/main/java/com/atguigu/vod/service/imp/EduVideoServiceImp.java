package com.atguigu.vod.service.imp;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.atguigu.commonutils.RestCode;
import com.atguigu.servicebase.exception.MyException;
import com.atguigu.vod.service.EduVideoService;
import com.atguigu.vod.utils.ConstantPropertiesUtil;
import com.atguigu.vod.utils.VodInit;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * @author: Niebelungen
 * @create: 2022/4/12-16:58
 * @VERSION: 1.0
 */
@Service
public class EduVideoServiceImp implements EduVideoService {
    @Override
    public String uploadVideoAliyun(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        UploadStreamRequest request = null;
        try {
            request = new UploadStreamRequest(ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET,
                    fileName.substring(0,fileName.lastIndexOf(".")),
                    fileName,
                    file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
        return response.getVideoId();
    }
    //awt类型的list与util类型的不能自动转换
    @Override
    public void deleteBathVideo(List videoIdList) {
        try {
            DefaultAcsClient client = VodInit.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            DeleteVideoResponse response = new DeleteVideoResponse();
            String videoIds= StringUtils.join(videoIdList, ",");
            request.setVideoIds(videoIds);
            response = client.getAcsResponse(request);
            //涉及调用微服务服务,删除数据库数据（也可前端页面实现）


        } catch (ClientException e) {
            e.printStackTrace();
            throw new MyException(RestCode.ERROR,"删除视频失败");
        }
    }
}
