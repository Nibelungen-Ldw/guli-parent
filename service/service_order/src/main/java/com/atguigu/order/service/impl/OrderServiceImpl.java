package com.atguigu.order.service.impl;

import com.atguigu.commonutils.entity.CourseInfoVoCommon;
import com.atguigu.commonutils.entity.UcenterMemberCommom;
import com.atguigu.commonutils.util.RandomUtil;
import com.atguigu.order.entity.Order;
import com.atguigu.order.mapper.OrderMapper;
import com.atguigu.order.nacos.CourseInfo;
import com.atguigu.order.nacos.UserInfo;
import com.atguigu.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author liudewang
 * @since 2022-04-18
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    UserInfo userInfo;
    @Autowired
    CourseInfo courseInfo;

    @Override
    public String createOrder(String courseId, String memberId) {
        UcenterMemberCommom userInfoById = userInfo.getUserInfoById(memberId);
        CourseInfoVoCommon courseInfo = this.courseInfo.getCourseInfo(courseId);
        Order order = new Order();
        order.setOrderNo(RandomUtil.getSixBitRandom());

        order.setCourseId(courseId);
        order.setCourseTitle(courseInfo.getTitle());
        order.setCourseCover(courseInfo.getCover());
        order.setTeacherName(courseInfo.getTeacherName());
        order.setTotalFee(courseInfo.getPrice());

        order.setMemberId(memberId);
        order.setMobile(userInfoById.getMobile());
        order.setNickname(userInfoById.getNickname());

        order.setStatus(0);//未支付
        order.setPayType(1);//支付方式
        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
