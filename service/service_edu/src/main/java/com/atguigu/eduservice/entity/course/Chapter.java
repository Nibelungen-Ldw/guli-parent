package com.atguigu.eduservice.entity.course;

import lombok.Data;

import java.util.List;

/**
 * @author: Niebelungen
 * @create: 2022/4/10-10:46
 * @VERSION: 1.0
 */
@Data
public class Chapter {
    private String id;
    private String title;
    private List<Video> children;

}
