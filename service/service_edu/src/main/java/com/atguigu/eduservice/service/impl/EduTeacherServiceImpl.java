package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-04
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    @Cacheable(value = "idnexteacher")
    public List<EduTeacher> getIndexTeacher() {
        QueryWrapper<EduTeacher> eduTeacherQueryWrapper = new QueryWrapper<>();
        eduTeacherQueryWrapper.orderByDesc("id").last("limit 4");
        List<EduTeacher> eduTeacherList = this.list(eduTeacherQueryWrapper);
        return eduTeacherList;
    }

    @Override
    public HashMap<String,Object> getPageTeacherFront(Page<EduTeacher> teacherPage) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        this.page(teacherPage, wrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        List<EduTeacher> records = teacherPage.getRecords();
        long total = teacherPage.getTotal();
        long pageCurrent = teacherPage.getCurrent();
        long pages = teacherPage.getPages();

        boolean hasNext = teacherPage.hasNext();
        boolean hasPrevious = teacherPage.hasPrevious();

        hashMap.put("records",records);
        hashMap.put("total",total);
        hashMap.put("pageCurrent",pageCurrent);
        hashMap.put("pages",pages);
        hashMap.put("hasNext",hasNext);
        hashMap.put("hasPrevious",hasPrevious);


        return hashMap;
    }
}
