package com.atguigu.educenter.mapper;

import com.atguigu.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author liudewang
 * @since 2022-04-15
 */
//可以替代mapperscan注解
@Mapper
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer getRegsMemb(@Param("date") String date);
}
