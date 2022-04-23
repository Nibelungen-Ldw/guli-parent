package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.RestCode;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.frontvo.CourseConditionVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebInfo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublish;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exception.MyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author liudewang
 * @since 2022-04-09
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        if(!this.save(eduCourse)){
            throw new MyException(RestCode.ERROR,"课程信息添加失败");
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
        //设置一对一关联关系
        eduCourseDescription.setId(eduCourse.getId());
        eduCourseDescriptionService.save(eduCourseDescription);
        return eduCourse.getId();
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        EduCourse eduCourse = this.getById(courseId);
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        CourseInfoVo vo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,vo);
        BeanUtils.copyProperties(courseDescription,vo);


        return vo;
    }

    @Override
    public String updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        if(!this.updateById(eduCourse)){
            throw new MyException(RestCode.ERROR,"课程信息修改失败");
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
        //设置一对一关联关系
        eduCourseDescription.setId(eduCourse.getId());
        eduCourseDescriptionService.updateById(eduCourseDescription);
        return eduCourse.getId();
    }

    @Override
    //使用basemapper调用xml编写的文件
    public CoursePublish getPublishCourseInfo(String courseId) {
        CoursePublish publishCourseInfo = baseMapper.getPublishCourseInfo(courseId);
        return publishCourseInfo;
    }

    @Override
    public void removeCourseById(String courseId) {
        eduVideoService.removeVideoById(courseId);
        eduChapterService.removeChapterById(courseId);
        eduCourseDescriptionService.removeById(courseId);
        //当courseID不存在时，basemapper返回的值能反应真实情况
//        this调用返回结果无变化
        if (baseMapper.deleteById(courseId)==0) {
            throw new MyException(RestCode.ERROR,"删除课程失败");
        }
    }

    @Override
    @Cacheable(value = "indexcourse")
    public List<EduCourse> getIndexCourse() {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id").last("limit 8");
        List<EduCourse> eduCourseList = this.list(wrapper);
        return eduCourseList;
    }

    @Override
    public HashMap<String, Object> getPageCourseFront(Page<EduCourse> coursePage, CourseConditionVo conditionVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(conditionVo.getSubjectParentId())){
            wrapper.eq("subject_parent_id",conditionVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(conditionVo.getSubjectId())){
            wrapper.eq("subject_id",conditionVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(conditionVo.getBuyCountSort())){
            wrapper.orderByDesc("buy_count");
        }
        if(!StringUtils.isEmpty(conditionVo.getGmtCreateSort())){
            wrapper.orderByDesc("gmt_create");
        }
        if(!StringUtils.isEmpty(conditionVo.getPriceSort())){
            wrapper.orderByDesc("price");
        }
       this.page(coursePage, wrapper);

        HashMap<String, Object> hashMap = new HashMap<>();
        List<EduCourse> records = coursePage.getRecords();
        long total = coursePage.getTotal();
        long pageCurrent = coursePage.getCurrent();
        long pages = coursePage.getPages();

        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();

        hashMap.put("records",records);
        hashMap.put("total",total);
        hashMap.put("pageCurrent",pageCurrent);
        hashMap.put("pages",pages);
        hashMap.put("hasNext",hasNext);
        hashMap.put("hasPrevious",hasPrevious);

        return hashMap;

    }

    @Override
    public CourseWebInfo getFrontCourseInfo(String id) {
        CourseWebInfo courseWebInfo = baseMapper.getFrontCourseInfo(id);
        return courseWebInfo;
    }
}
