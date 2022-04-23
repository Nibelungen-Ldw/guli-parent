package com.atguigu.educms.controller;


import com.atguigu.commonutils.Result;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author liudewang
 * @since 2022-04-14
 */
@RestController
@RequestMapping("/educms/banneruser")
//@CrossOrigin
public class CrmBannerUserController {
    @Autowired
    private CrmBannerService crmBannerService;
    @GetMapping("getAllBanner")
        public Result getAllBanner(){
        List<CrmBanner> crmBanners =  crmBannerService.getAllBanner();
            return Result.ok().data("crmBanners",crmBanners);
        }

}

