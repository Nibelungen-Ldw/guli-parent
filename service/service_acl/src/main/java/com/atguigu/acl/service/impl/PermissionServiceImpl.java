package com.atguigu.acl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.acl.entity.Permission;
import com.atguigu.acl.entity.RolePermission;
import com.atguigu.acl.entity.User;
import com.atguigu.acl.mapper.PermissionMapper;
import com.atguigu.acl.service.PermissionService;
import com.atguigu.acl.service.RolePermissionService;
import com.atguigu.acl.service.UserService;
import com.atguigu.acl.utils.MemuHelper;
import com.atguigu.acl.utils.RecursionUtils;
import com.atguigu.commonutils.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.javafx.tk.PermissionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author liudewang
 * @since 2022-04-21
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Autowired
    RolePermissionService rolePermissionService;
    @Autowired
    UserService userService;

    @Override
    public List<Permission> getAllPermission() {

        List<Permission> permissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByDesc("id"));

        List<Permission> resultList =  RecursionUtils.build(permissionList);
        return resultList;
    }

    @Override
    public void deletePermissonById(String id) {
    List<String> Idlist = new ArrayList<>();

        Idlist = selectPermissonById(id,Idlist);

        Idlist.add(id);
        System.out.println(Idlist);

        baseMapper.deleteBatchIds(Idlist);
    }

    @Override
    public void saveRolePerssion(String roleId, String[] permissionIds) {
        ArrayList<RolePermission> rolePermissions = new ArrayList<>();
        for (String  permissionId : permissionIds){
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermissionId(permissionId);
            rolePermission.setRoleId(roleId);
            rolePermissions.add(rolePermission);

        }
        rolePermissionService.saveBatch(rolePermissions);
    }

    @Override
    public List<String> selectPermissionValueByUserId(String id) {
        List<String> selectPermissionValueList = null;
        if(this.isSysAdmin(id)) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(String id) {
        List<Permission> selectPermissionList = null;
        if(this.isSysAdmin(id)) {
            //如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId(id);
        }

        List<Permission> permissionList = RecursionUtils.build(selectPermissionList);
        List<JSONObject> result = MemuHelper.bulid(permissionList);
        return result;
    }

    @Override
    public List<Permission> selectAllMenu(String roleId) {
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("id"));

        //根据角色id获取角色权限
        List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id",roleId));
        //转换给角色id与角色权限对应Map对象
//        List<String> permissionIdList = rolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
//        allPermissionList.forEach(permission -> {
//            if(permissionIdList.contains(permission.getId())) {
//                permission.setSelect(true);
//            } else {
//                permission.setSelect(false);
//            }
//        });
        for (int i = 0; i < allPermissionList.size(); i++) {
            Permission permission = allPermissionList.get(i);
            for (int m = 0; m < rolePermissionList.size(); m++) {
                RolePermission rolePermission = rolePermissionList.get(m);
                if(rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }


        List<Permission> permissionList = RecursionUtils.build(allPermissionList);
        return permissionList;    }

    private  List<String> selectPermissonById(String id, List<String>  idlist) {
        List<Permission> childIdList = baseMapper.selectList
                (new QueryWrapper<Permission>().select("id")
                        .eq("pid", id));

        //把childIdList的ID取出来递归查询子节点的ID
        childIdList.stream().forEach(permission -> {
            idlist.add(permission.getId());
            this.selectPermissonById(permission.getId(),idlist);
        });

        return idlist;

    }
    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);

        if(null != user && "admin".equals(user.getUsername())) {
            return true;
        }
        return false;
    }
}
