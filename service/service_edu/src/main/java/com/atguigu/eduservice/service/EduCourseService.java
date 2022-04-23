package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.frontvo.CourseConditionVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebInfo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublish;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author liudewang
 * @since 2022-04-09
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    String updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublish getPublishCourseInfo(String courseId);

    void removeCourseById(String courseId);

    List<EduCourse> getIndexCourse();

    HashMap<String, Object> getPageCourseFront(Page<EduCourse> coursePage, CourseConditionVo conditionVo);

    CourseWebInfo getFrontCourseInfo(String id);
}
