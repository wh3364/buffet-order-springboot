package com.fch.buffetorder.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: BuffetOrder
 * @description: 微信参数
 * @CreatedBy: fch
 * @create: 2022-10-14 15:42
 **/
@Data
@Component
@ConfigurationProperties(prefix = "wx")
public class WeiXinParam {
    private String APP_ID;

    private String APP_SECRET;

    private String GRANT_TYPE;

    private String IMG_PATH;
}
