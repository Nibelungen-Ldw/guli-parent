package com.atguigu.eduservice.Listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.commonutils.RestCode;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.eduservice.service.impl.EduSubjectServiceImpl;
import com.atguigu.servicebase.exception.MyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import javax.management.Query;
import javax.sound.midi.Soundbank;
import java.util.Map;

/**
 * @author: Niebelungen
 * @create: 2022/4/8-23:07
 * @VERSION: 1.0
 */
public class SubjectExcelsListener extends AnalysisEventListener<SubjectData> {
    //不能纳入spring容器管理
    public EduSubjectService eduSubjectService;

    public SubjectExcelsListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    public SubjectExcelsListener() {
    }


    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        System.out.println(subjectData);
        if(null == subjectData){
            new MyException(RestCode.ERROR,"文件数据为空！");
        }
        //一级分类不能重复添加
        EduSubject oneSubject = this.OneSubjecExist(subjectData.getOneSubjectName(), eduSubjectService);
        if(null == oneSubject){
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(oneSubject);
        }
        String pid = oneSubject.getId();
        EduSubject twoSubject= this.TwoSubjecExist(subjectData.getTwoSubjectName(),pid, eduSubjectService);
        if(null == twoSubject){
            EduSubject eduSubject = new EduSubject();
            eduSubject.setParentId(pid);
            eduSubject.setTitle(subjectData.getTwoSubjectName());
            eduSubjectService.save(eduSubject);
        }

    }

    private EduSubject OneSubjecExist(String OneSubjecName,EduSubjectService eduSubjectService){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",OneSubjecName).eq("parent_id",0);
        EduSubject eduSubject = eduSubjectService.getOne(wrapper);
        return eduSubject;
    }
    private EduSubject TwoSubjecExist(String twoSubjecName,String parentId,EduSubjectService eduSubjectService){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",twoSubjecName).eq("parent_id",parentId);
        EduSubject eduSubject = eduSubjectService.getOne(wrapper);
        return eduSubject ;
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
        System.out.println(headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
