package com.atguigu.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.commonutils.RestCode;
import com.atguigu.order.entity.Order;
import com.atguigu.order.entity.PayLog;
import com.atguigu.order.mapper.PayLogMapper;
import com.atguigu.order.service.OrderService;
import com.atguigu.order.service.PayLogService;
import com.atguigu.order.utils.HttpClient;
import com.atguigu.servicebase.exception.MyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author liudewang
 * @since 2022-04-18
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {
    @Autowired
    OrderService orderService;
    @Override
    public Map generateWxCode(String orderNo) {
        //根据订单号查询订单信息
        Order order = orderService.getOne(new QueryWrapper<Order>().eq("order_no", orderNo));
        //map设置发送参数
        HashMap<String, String> m = new HashMap<>();
        //编写常量工具类结合appplication配置文件设置值
        m.put("appid", "wx74862e0dfcf69954");//公众号
        m.put("mch_id", "1558950191");//商户号
        m.put("nonce_str", WXPayUtil.generateNonceStr());//唯一标识字符串
        m.put("body", order.getCourseTitle());//课程名称
        m.put("out_trade_no", orderNo);
        m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
        m.put("spbill_create_ip", "127.0.0.1");//项目域名
        m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");//回调地址
        m.put("trade_type", "NATIVE");//支付类型

        //发送httpclient请求,设置xml格式参数
        HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");

        try {
            httpClient.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            httpClient.setHttps(true);
            httpClient.post();
            //xml格式的返回结果
            String content = httpClient.getContent();
            Map<String, String> xmlToMap = WXPayUtil.xmlToMap(content);
            //最终返回数据封装

            Map map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", xmlToMap.get("result_code"));
            map.put("code_url", xmlToMap.get("code_url"));

            //微信支付二维码2小时过期，可采取2小时未支付取消订单
            //redisTemplate.opsForValue().set(orderNo, map, 120, TimeUnit.MINUTES);
            return map;


        } catch (Exception e) {
            throw new MyException(RestCode.ERROR,"请求支付二维码失败！");
        }
    }

    @Override
    public Map getPayStatus(String orderNo) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //6、转成Map
            //7、返回
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateOrderAndPayLog(Map map) {
        String orderNo = (String) map.get("out_trade_no");
        Order order = orderService.getOne(new QueryWrapper<Order>().eq("order_no", orderNo));
        if(order.getStatus().intValue() == 0){
            order.setStatus(1);
            orderService.updateById(order);
        }
        PayLog payLog = new PayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState((String) map.get("trade_state"));//支付状态
        payLog.setTransactionId((String) map.get("transaction_id"));//流水号
        payLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(payLog);//插入到支付日志表


    }
}
