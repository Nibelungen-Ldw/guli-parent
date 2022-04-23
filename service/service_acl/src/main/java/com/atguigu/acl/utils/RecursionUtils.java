package com.atguigu.acl.utils;

import com.atguigu.acl.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Niebelungen
 * @create: 2022/4/21-11:57
 * @VERSION: 1.0
 */
public class RecursionUtils {
    public static List<Permission> build(List<Permission> permissionList){
        //方法入口，获取Pid=0的菜单项，设置level=1，然后recursion
        ArrayList<Permission> finallyList = new ArrayList<>();
        for(Permission permission : permissionList){
            if("0".equals(permission.getPid())){
                permission.setLevel(1);
                finallyList.add(selectChildren(permission,permissionList));
            }

        }
        return finallyList;
    }

    private static Permission selectChildren(Permission permission, List<Permission> permissionList) {
        //初始化
        permission.setChildren(new ArrayList<Permission>());

        //递归遍历比较
        for (Permission permissionSub : permissionList){
            if(permission.getId().equals(permissionSub.getPid())){
                permissionSub.setLevel(permission.getLevel() + 1);
                permission.getChildren().add(selectChildren(permissionSub,permissionList));
            }
        }

        return permission;
    }


}
