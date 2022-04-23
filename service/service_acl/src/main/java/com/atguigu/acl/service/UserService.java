package com.atguigu.acl.service;

import com.atguigu.acl.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author liudewang
 * @since 2022-04-21
 */
public interface UserService extends IService<User> {

    User selectByUsername(String username);
}
