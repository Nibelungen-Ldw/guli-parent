package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author liudewang
 * @since 2022-04-18
 */
public interface EduCommentService extends IService<EduComment> {

    HashMap<String, Object> getPageCourseCommnet(Page<EduComment> page, String courseid);
}
