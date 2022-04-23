package com.atguigu.acl.mapper;

import com.atguigu.acl.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author liudewang
 * @since 2022-04-21
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    List<String> selectPermissionValueByUserId(String id);

    List<String> selectAllPermissionValue();

    List<Permission> selectPermissionByUserId(String id);
}
