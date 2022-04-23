package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.mapper.EduCommentMapper;
import com.atguigu.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author liudewang
 * @since 2022-04-18
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Override
    public HashMap<String, Object> getPageCourseCommnet(Page<EduComment> page, String courseid) {
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseid);
        wrapper.orderByDesc("gmt_create");
        this.page(page, wrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        List<EduComment> records = page.getRecords();
        long total = page.getTotal();
        long pageCurrent = page.getCurrent();
        long pages = page.getPages();

        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();

        hashMap.put("records",records);
        hashMap.put("total",total);
        hashMap.put("pageCurrent",pageCurrent);
        hashMap.put("pages",pages);
        hashMap.put("hasNext",hasNext);
        hashMap.put("hasPrevious",hasPrevious);


        return hashMap;
    }
}
