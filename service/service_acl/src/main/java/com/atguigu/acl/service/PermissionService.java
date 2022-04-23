package com.atguigu.acl.service;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.acl.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author liudewang
 * @since 2022-04-21
 */
public interface PermissionService extends IService<Permission> {

    List<Permission> getAllPermission();

    void deletePermissonById(String id);
    void saveRolePerssion(String roleId,String[] permissionIds);

    List<String> selectPermissionValueByUserId(String id);

    List<JSONObject> selectPermissionByUserId(String id);

    List<Permission> selectAllMenu(String roleId);
}
