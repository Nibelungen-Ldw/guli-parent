package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.course.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author liudewang
 * @since 2022-04-09
 */
public interface EduChapterService extends IService<EduChapter> {

    List<Chapter> getChapterById(String courseId);

    Boolean deleteChapter(String chapterId);

    void removeChapterById(String courseId);
}
