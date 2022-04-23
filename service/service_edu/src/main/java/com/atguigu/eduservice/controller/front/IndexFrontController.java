package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Niebelungen
 * @create: 2022/4/14-15:35
 * @VERSION: 1.0
 */
@RestController
@RequestMapping("/eduservice/front")
//@CrossOrigin
public class IndexFrontController {
    @Autowired
    EduCourseService eduCourseService;
    @Autowired
    EduTeacherService eduTeacherService;

    //查询前八个热门课程和前4个热门讲师
    @GetMapping("index")
    public Result index(){

        List<EduCourse> eduCourseList = eduCourseService.getIndexCourse();



        List<EduTeacher> eduTeacherList = eduTeacherService.getIndexTeacher();
        return Result.ok().data("eduCourseList",eduCourseList).data("eduTeacherList",eduTeacherList);

    }
}
