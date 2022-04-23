package com.atguigu.commonutils;


/**
 * @author: Niebelungen
 * @create: 2022/4/4-17:45
 * @VERSION: 1.0
 */
public enum RestCode {
    SUCESSS(20000),
    ERROR(20001),
    UNDEFINE(25000);
    private Integer status ;
    private RestCode(Integer status){
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

}
