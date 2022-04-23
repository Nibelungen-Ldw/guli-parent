package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.RestCode;
import com.atguigu.commonutils.Result;
import com.atguigu.commonutils.util.JwtUtils;
import com.atguigu.commonutils.util.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exception.MyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author liudewang
 * @since 2022-04-15
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(UcenterMember ucenterMember) {
        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();
        String passwordencrypt = MD5.encrypt(password);
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new MyException(RestCode.ERROR,"登录失败");
        }
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);

        UcenterMember member = this.getOne(queryWrapper);
        if (member==null){
            throw new MyException(RestCode.ERROR,"登录失败");
        }
        if(!passwordencrypt.equals(member.getPassword())){
            throw new MyException(RestCode.ERROR,"登录失败");

        }
        if(member.getIsDeleted()){
            throw new MyException(RestCode.ERROR,"登录失败");

        }
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return token;
    }

    @Override
    public Result register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
        ||StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)){
            throw new MyException(RestCode.ERROR,"注册失败");
            //抛出异常，返回为error;但前端需要对异常做出判断
        }

        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (StringUtils.isEmpty(redisCode)){
            throw new MyException(RestCode.ERROR,"验证码无效，注册失败");

        }
        if(!redisCode.equals(code)){
            throw new MyException(RestCode.ERROR,"验证码错误，注册失败");

        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        int count = this.count(wrapper);
        if (count > 0){
            throw new MyException(RestCode.ERROR,"手机号已经注册，注册失败");

        }
        UcenterMember ucenterMember = new UcenterMember();
        registerVo.setPassword(MD5.encrypt(password));
        BeanUtils.copyProperties(registerVo,ucenterMember);
        ucenterMember.setAvatar("https://edu-avatar02.oss-cn-shanghai.aliyuncs.com/2022/04/11/57fb8c9738b440919f37ff63438ca464file.png");
        this.save(ucenterMember);
        return Result.ok();
    }

    @Override
    public UcenterMember getMemberByopenid(String openid) {

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        return this.getOne(wrapper);

    }

    @Override
    public Integer getRegsMemb(String date) {
         return baseMapper.getRegsMemb(date);
    }
}
