package com.atguigu.acl.controller;


import com.atguigu.acl.entity.Permission;
import com.atguigu.acl.service.PermissionService;
import com.atguigu.commonutils.Result;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author liudewang
 * @since 2022-04-21
 */
@RestController
@RequestMapping("/admin/acl/permission")
//@CrossOrigin
public class PermissionController {
    @Autowired
    PermissionService permissionService;
    //获取全部菜单（递归）

    @GetMapping
    public Result getAllPermission(){
        List<Permission> permissionList =  permissionService.getAllPermission();
        return Result.ok().data("permissionList",permissionList);
    }

    @DeleteMapping("remove/{id}")
    public Result deletePermissonById(@PathVariable String id){
         permissionService.deletePermissonById(id);
        return Result.ok();
    }

    @PostMapping("doAssign")
    public Result saveRolePerssion( String roleId,
                                   String[] permissionIds){
        permissionService.saveRolePerssion( roleId, permissionIds);
        return Result.ok();
    }


    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public Result toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return Result.ok().data("children", list);
    }



    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public Result save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public Result updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.ok();
    }




    //删除菜单（递归）



    //为角色分配权限

}

