package com.atguigu.order.controller;


import com.atguigu.commonutils.RestCode;
import com.atguigu.commonutils.Result;
import com.atguigu.order.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author liudewang
 * @since 2022-04-18
 */
@RestController
@RequestMapping("/eduorder/paylog")
//@CrossOrigin
public class PayLogController {
    @Autowired
    PayLogService payLogService;
    @GetMapping("generateWxCode/{orderNo}")
    public Result generateWxCode(@PathVariable("orderNo")String orderNo){
        Map map = payLogService.generateWxCode(orderNo);
        return Result.ok().data(map);
    }

    @GetMapping("getPayStatus/{orderNo}")
    public Result getPayStatus(@PathVariable("orderNo")String orderNo){

        Map map = payLogService.getPayStatus(orderNo);
        System.out.println("查询订单支付状态***"+map);
        if(map == null){
            return Result.error().message("支付失败了");

        }
        if("SUCCESS".equals(map.get("trade_state"))){
            payLogService.updateOrderAndPayLog(map);
            return Result.ok().message("支付成功");

        }

            return Result.ok().code(RestCode.UNDEFINE).message("正在支付中");

    }

}

