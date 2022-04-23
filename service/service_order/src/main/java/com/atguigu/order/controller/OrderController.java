package com.atguigu.order.controller;


import com.atguigu.commonutils.RestCode;
import com.atguigu.commonutils.Result;
import com.atguigu.commonutils.util.JwtUtils;
import com.atguigu.order.entity.Order;
import com.atguigu.order.nacos.CourseInfo;
import com.atguigu.order.nacos.UserInfo;
import com.atguigu.order.service.OrderService;
import com.atguigu.servicebase.exception.MyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author liudewang
 * @since 2022-04-18
 */
@RestController
@RequestMapping("/eduorder/order")
//@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("createOrder/{id}")
    public Result createOrder(@PathVariable("id") String courseId, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)){
            throw new MyException(RestCode.ERROR,"支付需要先登录！");
        }
        String orderNo = orderService.createOrder(courseId,memberId);


        return Result.ok().data("orderNo",orderNo);
    }

    @GetMapping("getOrder/{orderNo}")
    public Result getOrder(@PathVariable("orderNo") String orderNo){
        Order order = orderService.getOne(new QueryWrapper<Order>().eq("order_no", orderNo));
        return Result.ok().data("order",order);
    }

    @GetMapping("isBuyCourse/{courseid}/{memberid}")
    public boolean isBuyCourse(@PathVariable("courseid") String courseid,
                              @PathVariable("memberid") String memberid){
        int count = orderService.count(new QueryWrapper<Order>().eq("course_id", courseid)
                .eq("member_id", memberid).eq("status", 1));
        return count > 0 ? true : false;
    }


}

