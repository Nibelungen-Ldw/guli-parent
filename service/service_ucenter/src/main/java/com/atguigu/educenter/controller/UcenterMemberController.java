package com.atguigu.educenter.controller;


import com.atguigu.commonutils.Result;
import com.atguigu.commonutils.entity.UcenterMemberCommom;
import com.atguigu.commonutils.util.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author liudewang
 * @since 2022-04-15
 */
@RestController
@RequestMapping("/educenter/member")
//@CrossOrigin
public class UcenterMemberController {
    @Autowired
    UcenterMemberService memberService;
    @PostMapping("login")
    public Result loginUser(@RequestBody UcenterMember ucenterMember){
        String token = memberService.login(ucenterMember);
        return Result.ok().data("token",token);

    }

    @PostMapping("register")
    public Result registerUser(@RequestBody RegisterVo registerVo){
        Result result = memberService.register(registerVo);
        return result;

    }

    //根据token获取用户信息
    @GetMapping("getUserInfoByToken")
    public Result getUserInfoByToken(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(memberId);
        return Result.ok().data("member",member);
    }

    @GetMapping("getUserInfoById/{id}")
    public UcenterMemberCommom getUserInfoById(@PathVariable("id") String id){
        UcenterMember member = memberService.getById(id);
        UcenterMemberCommom ucenterMemberCommom = new UcenterMemberCommom();
        BeanUtils.copyProperties(member,ucenterMemberCommom);
        return ucenterMemberCommom;
    }

    @GetMapping("getRegsMemb/{date}")
    public Result getRegsMemb(@PathVariable("date") String date){
       Integer count = memberService.getRegsMemb(date);
        return Result.ok().data("count",count);
    }



}

