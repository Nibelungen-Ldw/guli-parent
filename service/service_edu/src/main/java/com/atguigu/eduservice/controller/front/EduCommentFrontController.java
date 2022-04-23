package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.RestCode;
import com.atguigu.commonutils.Result;
import com.atguigu.commonutils.entity.UcenterMemberCommom;
import com.atguigu.commonutils.util.JwtUtils;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.service.EduCommentService;
import com.atguigu.eduservice.ucenterclient.Ucenter;
import com.atguigu.servicebase.exception.MyException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author: Niebelungen
 * @create: 2022/4/18-16:56
 * @VERSION: 1.0
 */
@RestController
@RequestMapping("/eduservice/commentfront")
//@CrossOrigin
public class EduCommentFrontController {
    @Autowired
    EduCommentService eduCommentService;
    @Autowired
    Ucenter ucenter;

    @GetMapping("getCourseCommnet/{courseid}/{current}/{limit}")
    public Result getCourseCommnet(@PathVariable("current") long current,
                                   @PathVariable("limit") long limit,
                                   @PathVariable("courseid")String courseid){
        Page<EduComment> page = new Page<>(current,limit);
        HashMap<String,Object> map = eduCommentService.getPageCourseCommnet(page,courseid);
        return Result.ok().data(map);
    }

    @PostMapping("addCourseCommnet")
    public Result addCourseCommnet(@RequestBody EduComment comment, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)){
            throw new MyException(RestCode.ERROR,"请登录后评论");
        }
        comment.setMemberId(memberId);
        //远程调用方法获取用户信息
        UcenterMemberCommom member = ucenter.getUserInfoById(memberId);

        comment.setNickname(member.getNickname());
        comment.setAvatar(member.getAvatar());
        eduCommentService.save(comment);
        return Result.ok();
    }
}
