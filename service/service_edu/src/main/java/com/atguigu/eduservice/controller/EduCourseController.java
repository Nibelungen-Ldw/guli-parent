package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.publish.Pubish;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublish;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author liudewang
 * @since 2022-04-09
 */
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;
    @PostMapping("addCourseInfo")
    public Result addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String courseId = eduCourseService.saveCourseInfo(courseInfoVo);
        return Result.ok().data("id",courseId);
    }
    @GetMapping("getCourseInfo/{courseId}")
    public Result getCourseInfo(@PathVariable String courseId){
          CourseInfoVo courseInfo = eduCourseService.getCourseInfo(courseId);
        return Result.ok().data("courseInfo",courseInfo);
    }
    @PostMapping("updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String courseId = eduCourseService.updateCourseInfo(courseInfoVo);
        return Result.ok().data("id",courseId);
    }
    @GetMapping("getPublishCourseInfo/{courseId}")
    public Result getPublishCourseInfo(@PathVariable String courseId){
        CoursePublish coursePublish = eduCourseService.getPublishCourseInfo(courseId);
        return Result.ok().data("coursePublish",coursePublish);
    }
    @PutMapping("PublishCourse/{courseId}")
    public Result PublishCourse(@PathVariable String courseId){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId).setStatus(Pubish.NORMAL.getSataus());
        eduCourseService.updateById(eduCourse);
        return Result.ok();
    }
    // @RequestBody(required = false) 带条件和不带条件二合一
    @PostMapping("getCoursePageConditionInfo/{current}/{limit}")
    public Result getCoursePageConditionInfo(@PathVariable("current") long current,
                                             @PathVariable("limit") long limit,
                                             @RequestBody(required = false) CourseQuery courseQuery){
        Page<EduCourse> page = new Page<>(current, limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();


        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.lt("gmt_create",end);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.gt("gmt_create",begin);
        }
        wrapper.orderByDesc("gmt_create");

        eduCourseService.page(page, wrapper);
        return Result.ok().data("CoursePageInfo",page);
    }
    @DeleteMapping("deleteCourse/{courseId}")
    public Result deleteCourse(@PathVariable String courseId){
        eduCourseService.removeCourseById(courseId);
        return Result.ok();
    }

}

