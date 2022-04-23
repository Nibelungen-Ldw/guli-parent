package com.atguigu.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.Value;

/**
 * @author: Niebelungen
 * @create: 2022/4/8-23:00
 * @VERSION: 1.0
 */
@Data
public class SubjectData {
    @ExcelProperty(index = 0)
    private String oneSubjectName;//首字母，第二位字母不为大写
    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
