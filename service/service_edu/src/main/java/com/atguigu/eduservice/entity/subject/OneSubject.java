package com.atguigu.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Niebelungen
 * @create: 2022/4/9-11:04
 * @VERSION: 1.0
 */
@Data
public class OneSubject {
    private String id;
    private String title;
    private List<TwoSubject> children;
}
