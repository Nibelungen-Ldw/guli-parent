package com.atguigu.acl.service;

import com.atguigu.acl.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.*;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liudewang
 * @since 2022-04-21
 */
public interface RoleService extends IService<Role> {

    List<Role> selectRoleByUserId(String id);

    Map<String,Object> findRoleByUserId(String userId);

    void saveUserRoleRealtionShip(String userId, String[] roleId);
}
