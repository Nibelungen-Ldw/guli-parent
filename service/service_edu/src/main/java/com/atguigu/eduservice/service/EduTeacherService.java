package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-04
 */
public interface EduTeacherService extends IService<EduTeacher> {

    List<EduTeacher> getIndexTeacher();


    HashMap<String, Object> getPageTeacherFront(Page<EduTeacher> teacherPage);
}
