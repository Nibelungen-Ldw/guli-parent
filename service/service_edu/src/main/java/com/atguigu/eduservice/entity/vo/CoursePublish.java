package com.atguigu.eduservice.entity.vo;

import lombok.Data;

/**
 * @author: Niebelungen
 * @create: 2022/4/11-10:45
 * @VERSION: 1.0
 */
@Data
public class CoursePublish {
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String description;
    private String price;//只用于显示
}
