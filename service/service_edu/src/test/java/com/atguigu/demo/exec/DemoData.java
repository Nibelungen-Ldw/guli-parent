package com.atguigu.demo.exec;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author: Niebelungen
 * @create: 2022/4/8-22:18
 * @VERSION: 1.0
 */
@Data
public class DemoData {
    @ExcelProperty(value = "学生编号",index = 0)
    private Integer snum;
    @ExcelProperty(value = "学生姓名",index = 1)
    private String  name;
}
