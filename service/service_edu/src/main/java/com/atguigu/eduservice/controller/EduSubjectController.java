package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author liudewang
 * @since 2022-04-08
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/subject")
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("addsubject")
    public Result addSubject(MultipartFile file) {
        eduSubjectService.saveSubject(file,eduSubjectService);
        return Result.ok();
    }

    @GetMapping("getAllSubject")
    public Result getSubject(){
        List<OneSubject> subjects =  eduSubjectService.getSubject();
        return Result.ok().data("subjects",subjects);

    }


}

