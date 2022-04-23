package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.course.Chapter;
import com.atguigu.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author liudewang
 * @since 2022-04-09
 */
@RestController
@RequestMapping("/eduservice/chapter")
//@CrossOrigin
public class EduChapterController {
    @Autowired
    EduChapterService eduChapterService;
    @GetMapping("getChapterVideo/{courseId}")
    public Result getChapterVideo(@PathVariable String courseId){
        List<Chapter> chapterList = eduChapterService.getChapterById(courseId);
        return Result.ok().data("chapterList",chapterList);
    }
    @PostMapping("addChapter")
    public Result addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return Result.ok();
    }
    @GetMapping("getChapterIfo/{ChapterId}")
    public Result getChapterIfo(@PathVariable String ChapterId){
        EduChapter eduChapter = eduChapterService.getById(ChapterId);
        return Result.ok().data("eduChapter",eduChapter);
    }
    @PostMapping("updateChapter")
    public Result updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return Result.ok();
    }
    @DeleteMapping("deleteChapter/{ChapterId}")
    public Result deleteChapter(@PathVariable String ChapterId){
        Boolean result = eduChapterService.deleteChapter(ChapterId);
        return result? Result.ok() : Result.error();
    }
}

