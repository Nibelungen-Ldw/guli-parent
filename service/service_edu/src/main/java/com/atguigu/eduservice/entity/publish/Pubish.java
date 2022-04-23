package com.atguigu.eduservice.entity.publish;

/**
 * @author: Niebelungen
 * @create: 2022/4/11-17:45
 * @VERSION: 1.0
 */
 public enum  Pubish {
    NORMAL("Normal"),DRAFT("Draft");

    private String sataus;

    private Pubish(String sataus) {
        this.sataus = sataus;
    }

    public String getSataus() {
        return sataus;
    }
}
