package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author: Niebelungen
 * @create: 2022/4/17-11:20
 * @VERSION: 1.0
 */
@RestController
@RequestMapping("/eduservice/teacherfront")
//@CrossOrigin
public class EduTeacherFrontController {
    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;

    @GetMapping("getPageTeacherFront/{page}/{limit}")
        public Result getPageTeacherFront(@PathVariable("page")long page,
                                          @PathVariable("limit")long limit){
        Page<EduTeacher> teacherPage = new Page<>(page,limit);
        HashMap<String,Object> map = eduTeacherService.getPageTeacherFront(teacherPage);
        return Result.ok().data(map);
    }

    @GetMapping("getTeacherFrontById/{id}")
    public Result getTeacherFrontById(@PathVariable("id")String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",id);
        List<EduCourse> eduCourseList = eduCourseService.list(wrapper);
        return Result.ok().data("eduTeacher",eduTeacher).data("eduCourseList",eduCourseList);
    }


}
