package com.atguigu.educms.controller;


import com.atguigu.commonutils.Result;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author liudewang
 * @since 2022-04-14
 */
@RestController
@RequestMapping("/educms/banneradmin")
//@CrossOrigin
public class CrmBannerAdminController {
    @Autowired
    CrmBannerService crmBannerService;

    @GetMapping("pageBanner/{current}/{limit}")
    public Result pageBanner(@PathVariable("current")long current,
                             @PathVariable("limit")long limit){
        Page<CrmBanner> page = new Page<>();
        crmBannerService.page(page,null);
        return Result.ok().data("page",page);


    }

    @PostMapping("addBanner")
    public Result addBanner(@RequestBody CrmBanner crmBanner){
       crmBannerService.save(crmBanner);
        return Result.ok();


    }

    @DeleteMapping("deleteBanner/{id}")
    public Result deleteBanner(@PathVariable("id") String id){
        crmBannerService.removeById(id);
        return Result.ok();
    }

    @GetMapping("getBanner/{id}")
    public Result getBanner(@PathVariable("id") String id){
        CrmBanner crmBanner = crmBannerService.getById(id);
        return Result.ok().data("crmBanner",crmBanner);
    }

    @PutMapping("editBanner/{id}")
    public Result editBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.updateById(crmBanner);
        return Result.ok();
    }

}

