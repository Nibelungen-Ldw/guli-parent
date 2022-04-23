package com.atguigu.mss.service;

import java.util.HashMap;

/**
 * @author: Niebelungen
 * @create: 2022/4/15-9:55
 * @VERSION: 1.0
 */
public interface MssService {
    boolean send(HashMap<String, Object> hashMap, String phonenumber);
}
