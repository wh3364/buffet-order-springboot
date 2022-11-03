package com.fch.buffetorder.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @program: BuffetOrder
 * @description: 获得微信的openid
 * @CreatedBy: fch
 * @create: 2022-10-16 14:18
 **/
@Component
public class OpenIdUtil {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeiXinParam weiXinParam;

    @Autowired
    private RedisUtil redisUtil;


    public JSONObject getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + weiXinParam.getAPP_ID() + "&secret=" + weiXinParam.getAPP_SECRET() + "&js_code=" + code + "&grant_type=" + weiXinParam.getGRANT_TYPE();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        JSONObject res = new JSONObject();
        if (responseEntity.getStatusCodeValue() <= 200) {
            if (StringUtils.hasText(JSONObject.parseObject(responseEntity.getBody()).getString("errmsg"))) {
                res.put("flag", false);
                res.put("openId", "");
                res.put("msg", JSONObject.parseObject(responseEntity.getBody()).getString("errmsg"));
            } else {
                //rides中从这里加
                res.put("flag", true);
                res.put("session_key", JSONObject.parseObject(responseEntity.getBody()).getString("session_key"));
                res.put("openId", JSONObject.parseObject(responseEntity.getBody()).getString("openid"));
                res.put("msg", "成功获得openId");
                redisUtil.setStr(res.getString("session_key"), res.getString("openId"), 60L * 60L * 2L);
//                RBucket<String> sessionKey = redissonClient.getBucket(res.getString("session_key"));
//                sessionKey.set(res.getString("openId"), 60L, TimeUnit.SECONDS);
            }
            return res;
        }
        res.put("flag", false);
        res.put("msg", "访问微信服务器失败");
        return res;
    }


    public String getOpenIdFromSession(String sessionKey) {
        //RBucket<String> openId = redissonClient.getBucket(sessionKey);
        return redisUtil.getStr(sessionKey);
    }

}
