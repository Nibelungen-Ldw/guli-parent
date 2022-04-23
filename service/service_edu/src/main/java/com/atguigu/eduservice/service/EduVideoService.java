package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author liudewang
 * @since 2022-04-09
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeVideoById(String courseId);
}
