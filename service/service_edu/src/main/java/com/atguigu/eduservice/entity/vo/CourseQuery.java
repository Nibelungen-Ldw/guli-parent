package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: Niebelungen
 * @create: 2022/4/11-18:46
 * @VERSION: 1.0
 */

@Data
public class CourseQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;

    private String status;

    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    private String end;
}