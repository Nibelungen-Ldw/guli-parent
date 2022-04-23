package com.atguigu.order.service;

import com.atguigu.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author liudewang
 * @since 2022-04-18
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, String memberId);
}
