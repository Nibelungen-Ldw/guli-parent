package com.atguigu.demo.exec;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Niebelungen
 * @create: 2022/4/8-22:21
 * @VERSION: 1.0
 */
public class TestEasyExcel {
    public static void main(String[] args) {
        String fileName ="D:/write.xlsx";
        EasyExcel.write(fileName,DemoData.class).sheet("学生列表").
                doWrite(TestEasyExcel.getDemoData());
    }

    @Test
    public void testRead() {
        String fileName ="D:/write.xlsx";
        EasyExcel.read(fileName,DemoData.class,new ReadExcelListener()).sheet().doRead();
    }

    private static List<DemoData> getDemoData(){
        ArrayList<DemoData> list = new ArrayList<>();
        for(int i = 0;i < 10;i++){
            DemoData data = new DemoData();
            data.setSnum(i);
            data.setName("lucy"+i);
            list.add(data);
        }
        return list;
    }
}
