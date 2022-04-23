package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * @author: Niebelungen
 * @create: 2022/4/12-16:57
 * @VERSION: 1.0
 */
public interface EduVideoService {
    String uploadVideoAliyun(MultipartFile file);

    void deleteBathVideo(List videoIdList);
}
