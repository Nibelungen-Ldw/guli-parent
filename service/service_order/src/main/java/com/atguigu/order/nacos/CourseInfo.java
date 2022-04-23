package com.atguigu.order.nacos;

import com.atguigu.commonutils.entity.CourseInfoVoCommon;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: Niebelungen
 * @create: 2022/4/18-23:09
 * @VERSION: 1.0
 */
@Component
@FeignClient(name = "service-edu",fallback = CourseInfoFallBack.class)
public interface CourseInfo {
    @GetMapping("/eduservice/coursefront/getCourseInfo/{id}")
    public CourseInfoVoCommon getCourseInfo(@PathVariable("id") String id);
}
