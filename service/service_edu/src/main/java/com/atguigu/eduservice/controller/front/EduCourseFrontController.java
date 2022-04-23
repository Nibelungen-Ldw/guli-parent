package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.Result;
import com.atguigu.commonutils.entity.CourseInfoVoCommon;
import com.atguigu.commonutils.util.JwtUtils;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.course.Chapter;
import com.atguigu.eduservice.entity.frontvo.CourseConditionVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebInfo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.vodclient.OrderClient;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author: Niebelungen
 * @create: 2022/4/17-17:39
 * @VERSION: 1.0
 */
@RestController
@RequestMapping("/eduservice/coursefront")
//@CrossOrigin
public class EduCourseFrontController {
    @Autowired
    EduCourseService eduCourseService;
    @Autowired
    EduChapterService eduChapterService;
    @Autowired
    OrderClient orderClient;

    @PostMapping("getPageCourseFront/{page}/{limit}")
    public Result getPageCourseFront(@PathVariable("page")long page,
                                      @PathVariable("limit")long limit,
                                      @RequestBody(required = false)CourseConditionVo conditionVo){
        Page<EduCourse> coursePage = new Page<>(page,limit);
        HashMap<String,Object> map = eduCourseService.getPageCourseFront(coursePage,conditionVo);
        return Result.ok().data(map);
    }
    //查询课程详情方法
    @GetMapping("getFrontCourseInfo/{id}")
    public Result getFrontCourseInfo(@PathVariable("id") String id, HttpServletRequest request){
        CourseWebInfo courseWebInfo = eduCourseService.getFrontCourseInfo(id);

        List<Chapter> chapterList = eduChapterService.getChapterById(id);

        //获取课程是否购买
        boolean isbuyCourse = false;
        String memberId= JwtUtils.getMemberIdByJwtToken(request);
        if (!StringUtils.isEmpty(memberId)){

             isbuyCourse = orderClient.isBuyCourse(id, memberId);
        }


        return Result.ok().data("courseWebInfo",courseWebInfo).data("chapterList",chapterList)
                .data("isbuyCourse",isbuyCourse);

    }

    @GetMapping("getCourseInfo/{id}")
    public CourseInfoVoCommon getCourseInfo(@PathVariable("id") String id){
        CourseWebInfo courseWebInfo = eduCourseService.getFrontCourseInfo(id);
        CourseInfoVoCommon courseInfoVoCommon = new CourseInfoVoCommon();
        BeanUtils.copyProperties(courseWebInfo,courseInfoVoCommon);
        return courseInfoVoCommon;

    }
}
