package com.atguigu.mss.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.mss.utils.ConstantPropertiesUtil;
import com.atguigu.mss.service.MssService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * @author: Niebelungen
 * @create: 2022/4/15-9:56
 * @VERSION: 1.0
 */
@Service
public class MssServiceImp implements MssService {


    @Override
    public boolean send(HashMap<String, Object> hashMap, String phonenumber) {


        if(StringUtils.isEmpty(phonenumber)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("default",
                        ConstantPropertiesUtil.ACCESS_KEY_ID,
                        ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        request.putQueryParameter("PhoneNumbers", phonenumber);
        request.putQueryParameter("SignName", "阿里云短信测试");
        request.putQueryParameter("TemplateCode", "SMS_154950909");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(hashMap));

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            //{"RequestId":"46B69BD8-1B18-592C-A648-5A2F8840B415","Message":"只能向已回复授权信息的手机号发送","Code":"isv.SMS_TEST_NUMBER_LIMIT"}
            //这种情况也返回true
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
            return false;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }
    }
