package com.atguigu.oss.controller;

import com.atguigu.commonutils.Result;
import com.atguigu.oss.ossService.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: Niebelungen
 * @create: 2022/4/8-11:58
 * @VERSION: 1.0
 */
@RestController
@RequestMapping("/eduoss/fileoss")
//@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;
    @PostMapping
    public Result upLoad(MultipartFile file){
        String fileUrl =ossService.upLoadAvatar(file);
        return  Result.ok().data("avatar",fileUrl);
    }
}
