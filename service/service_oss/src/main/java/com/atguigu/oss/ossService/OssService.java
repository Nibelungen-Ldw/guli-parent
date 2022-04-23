package com.atguigu.oss.ossService;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author: Niebelungen
 * @create: 2022/4/8-11:59
 * @VERSION: 1.0
 */
public interface OssService {
    String upLoadAvatar(MultipartFile file);
}
