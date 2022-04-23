package com.atguigu.order.service;

import com.atguigu.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author liudewang
 * @since 2022-04-18
 */
public interface PayLogService extends IService<PayLog> {

    Map generateWxCode(String orderNo);

    Map getPayStatus(String orderNo);

    void updateOrderAndPayLog(Map map);
}
