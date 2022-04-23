package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.RestCode;
import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exception.MyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-04
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
//@CrossOrigin
public class EduTeacherController {
    @Autowired
    private EduTeacherService teacherService;
    //查询讲师表中的所有数据
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public Result findAllTeacher(){
        List<EduTeacher> list = teacherService.list(null);
        return Result.ok().data("teacherItems",list);
    }

    @DeleteMapping("{id}")
    public Result deleteTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable("id") String id){
        boolean result = teacherService.removeById(id);
//        System.out.println(result);
        return result ? Result.ok() : Result.error();
    }

    @GetMapping("pageTeacher/{current}/{limit}")
    public Result pageListTeacher(@PathVariable("current")long current,
                                  @PathVariable("limit")long limit){
        Page<EduTeacher> page = new Page<>(current, limit);
        teacherService.page(page, null);
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();

        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return Result.ok().data(map);
    }
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public Result pageConditionListTeacher(@PathVariable("current")long current,
                                           @PathVariable("limit")long limit,
                                           @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> page = new Page<>(current, limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.lt("gmt_create",end);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.gt("gmt_create",begin);
        }
        wrapper.orderByDesc("gmt_create");
        teacherService.page(page, wrapper);
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();

        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return Result.ok().data(map);
    }

    @PostMapping("addTeacher")
    public Result addTeacher(@RequestBody EduTeacher teacher){
        boolean b = teacherService.save(teacher);
        return b ? Result.ok():Result.error();
    }

    @GetMapping("{id}")
    public Result getTeacher(@PathVariable("id") String id){
        EduTeacher teacher = teacherService.getById(id);
//        try {
//            int i = 1 / 0 ;
//        } catch (Exception e) {
//            throw new MyException(RestCode.ERROR,"自定义异常出现了");
//        }
        return Result.ok().data("teacher",teacher);
    }
    @PutMapping ("updateTeacher")
    public Result updateTeacher(@RequestBody EduTeacher teacher){
        boolean b = teacherService.updateById(teacher);
        return b ? Result.ok():Result.error();
    }



}

