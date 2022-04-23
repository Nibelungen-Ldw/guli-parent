package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.RestCode;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.course.Chapter;
import com.atguigu.eduservice.entity.course.Video;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exception.MyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author liudewang
 * @since 2022-04-09
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
@Autowired
    EduVideoService eduVideoService;
    @Override
    public List<Chapter> getChapterById(String courseId) {
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapterList = this.list(chapterQueryWrapper);
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(videoQueryWrapper);

        List<Chapter> finalList = new ArrayList<>();
        //遍历章节
        for(EduChapter eduChapter:eduChapterList){
            Chapter chapter = new Chapter();
            BeanUtils.copyProperties(eduChapter,chapter);
            finalList.add(chapter);
           List<Video> videos = new ArrayList<>();
            for (EduVideo eduVideo:eduVideoList){
                if(eduVideo.getChapterId().equals(eduChapter.getId())){
                    Video video = new Video();
                    BeanUtils.copyProperties(eduVideo,video);
                    videos.add(video);
                }
            }
            chapter.setChildren(videos);
        }

        return finalList;
    }

    @Override
    public Boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id",chapterId);
        //不用查询具体数据使用count方法
        int count = eduVideoService.count(videoQueryWrapper);
        if(count >= 1){
            new MyException(RestCode.ERROR,"小节不为空，不能删除章节");
        }else{
            boolean result = this.removeById(chapterId);
            return result;
        }
        return false;
    }

    @Override
    public void removeChapterById(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        this.remove(wrapper);
    }
}
