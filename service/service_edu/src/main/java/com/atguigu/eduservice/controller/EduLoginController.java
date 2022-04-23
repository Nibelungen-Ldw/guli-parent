package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Niebelungen
 * @create: 2022/4/7-10:36
 * @VERSION: 1.0
 */
@RestController
@RequestMapping("/eduservice/user")
//@CrossOrigin
public class EduLoginController {
    @Autowired
    private EduTeacherService eduTeacherService;
    @PostMapping("login")
    public Result login(){
        return  Result.ok().data("token","admin");
    }
    @GetMapping("info")
    public Result getInfo(){
        return Result.ok().data("roles","[admin]").data("name","admin").
                data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
