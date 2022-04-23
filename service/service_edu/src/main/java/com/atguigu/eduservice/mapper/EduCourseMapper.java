package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontvo.CourseWebInfo;
import com.atguigu.eduservice.entity.vo.CoursePublish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author liudewang
 * @since 2022-04-09
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    public CoursePublish getPublishCourseInfo(@Param("id") String courseId);


    CourseWebInfo getFrontCourseInfo(String id);
}
