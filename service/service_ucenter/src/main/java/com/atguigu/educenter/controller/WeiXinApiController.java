package com.atguigu.educenter.controller;

import com.atguigu.commonutils.RestCode;
import com.atguigu.commonutils.Result;
import com.atguigu.commonutils.util.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.HttpClientUtils;
import com.atguigu.educenter.utils.WeiXinConstantUtils;
import com.atguigu.servicebase.exception.MyException;
import com.google.gson.Gson;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Niebelungen
 * @create: 2022/4/16-19:08
 * @VERSION: 1.0
 */
@Controller
@RequestMapping("/api/ucenter/wx")
//@CrossOrigin
public class WeiXinApiController {

    @Autowired
    UcenterMemberService memberService;
    @GetMapping("login")
    public String getWxCode(){
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 回调地址
        String redirectUrl = WeiXinConstantUtils.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new MyException(RestCode.ERROR, e.getMessage());
        }

        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        String state = "atguigu";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名

        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：satte
        //过期时间：30分钟

        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                WeiXinConstantUtils.WX_OPEN_APP_ID,
                redirectUrl,
                state);

        return "redirect:" + qrcodeUrl;
    }

    ///api/ucenter/wx/callback?code=031h0ZFa1eIPZC0SKmFa1iUU5h0h0ZFu&state=atguigu
    @GetMapping("callback")
//    @ResponseBody
    public String loginUser(@RequestParam("code") String code,@RequestParam("state") String state){
        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        String accessTokenUrl = String.format(baseAccessTokenUrl,
                WeiXinConstantUtils.WX_OPEN_APP_ID,
                WeiXinConstantUtils.WX_OPEN_APP_SECRET,
                code);
        String accessTokenInfo = null;

        try {
             accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
//            System.out.println(accessTokenInfo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        HashMap<String,String> map = gson.fromJson(accessTokenInfo, HashMap.class);
        String accessToken = map.get("access_token");
        String openid = map.get("openid");



        UcenterMember member = memberService.getMemberByopenid(openid);
        if(member == null){
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
            String resultUserInfo = null;
            try {
                resultUserInfo = HttpClientUtils.get(userInfoUrl);
//            System.out.println("resultUserInfo==========" + resultUserInfo);
            } catch (Exception e) {
                throw new MyException(RestCode.ERROR, "获取用户信息失败");
            }
            HashMap<String,String> UserInfoMap = gson.fromJson(resultUserInfo, HashMap.class);
            String nickname = UserInfoMap.get("nickname");
            String headimgurl = UserInfoMap.get("headimgurl");

            UcenterMember ucenterMember = new UcenterMember();
            ucenterMember.setAvatar(headimgurl);
            ucenterMember.setNickname(nickname);
            ucenterMember.setOpenid(openid);
            memberService.save(ucenterMember);


            String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), nickname);
            return "redirect:http://localhost:3000?token="+jwtToken ;

        }
        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return "redirect:http://localhost:3000?token="+jwtToken ;

    }

}
