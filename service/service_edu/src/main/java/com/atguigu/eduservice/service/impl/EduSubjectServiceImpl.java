package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.Listener.SubjectExcelsListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author liudewang
 * @since 2022-04-08
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            EasyExcel.read(file.getInputStream(), SubjectData.class,new SubjectExcelsListener(eduSubjectService))
            .sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getSubject() {
        //分别查询一级，二级分类
        QueryWrapper<EduSubject> oneSubjectQueryWrapper = new QueryWrapper<>();
        oneSubjectQueryWrapper.eq("parent_id","0");
        List<EduSubject> oneSubjects = this.list(oneSubjectQueryWrapper);

        QueryWrapper<EduSubject> twoSubjectQueryWrapper = new QueryWrapper<>();
        twoSubjectQueryWrapper.ne("parent_id","0");
        List<EduSubject> twoSubjects = this.list(twoSubjectQueryWrapper);
        List<OneSubject> result = new ArrayList<>();
        for(EduSubject subject: oneSubjects){
            //添加一级分类
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(subject.getId());
//            oneSubject.setTitle(subject.getTitle());
            BeanUtils.copyProperties(subject,oneSubject);
            //添加二级分类
            List<TwoSubject> twoSubjectList = new ArrayList<>();
            for(EduSubject subject2: twoSubjects){
                TwoSubject twoSubject = new TwoSubject();
                //对象判断千万不能用“==”
                if(subject.getId().equals(subject2.getParentId())){
//                    twoSubject.setId(subject2.getId());
//                    twoSubject.setTitle(subject2.getTitle());
                    BeanUtils.copyProperties(subject2,twoSubject);
                    twoSubjectList.add(twoSubject);
                }
            }

            oneSubject.setChildren(twoSubjectList);
            result.add(oneSubject);
        }


        return result;
    }
}
