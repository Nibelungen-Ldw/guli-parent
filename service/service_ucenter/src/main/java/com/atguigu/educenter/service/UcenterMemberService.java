package com.atguigu.educenter.service;

import com.atguigu.commonutils.Result;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author liudewang
 * @since 2022-04-15
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember ucenterMember);

    Result register(RegisterVo registerVo);

    UcenterMember getMemberByopenid(String openid);

    Integer getRegsMemb(String date);
}
