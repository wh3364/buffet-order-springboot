package com.fch.buffetorder.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: BuffetOrder
 * @description: 微信参数
 * @CreatedBy: fch
 * @create: 2022-10-14 15:42
 **/
@Component
public class WeiXinParam {
    @Value("${weiXin.app-id}")
    public String APP_ID;

    @Value("${weiXin.app-secret}")
    public String APP_SECRET;

    @Value("${weiXin.grant-type}")
    public String GRANT_TYPE;

    @Value("${weiXin.img-path}")
    public String IMG_PATH;
}
