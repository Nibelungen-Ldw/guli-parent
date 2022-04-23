package com.atguigu.servicebase.exception;

import com.atguigu.commonutils.RestCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Niebelungen
 * @create: 2022/4/5-11:33
 * @VERSION: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyException extends RuntimeException {
    private RestCode code;
    private String  msg;

    @Override
    public String toString() {
        return "MyException{" +
                "code=" + code.getStatus()+
                ", msg='" + msg + '\'' +
                '}';
    }
}
