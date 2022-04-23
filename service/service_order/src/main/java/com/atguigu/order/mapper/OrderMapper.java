package com.atguigu.order.mapper;

import com.atguigu.order.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author liudewang
 * @since 2022-04-18
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
