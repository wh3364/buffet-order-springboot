package com.fch.buffetorder.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.util.OpenIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-10-25 17:42
 **/
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private OpenIdUtil openIdUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        // log.info("执行了preHandle（）");
        String session_key = request.getHeader("session_key");
        String code = request.getHeader("code");
        if (StringUtils.hasText(session_key)) {
            log.info("session_key:{}", session_key);
            String openId = openIdUtil.getOpenIdFromSession(session_key);
            if (StringUtils.hasText(openId)) {
                request.setAttribute("openId", openId);
                //request.setAttribute("session_key", session_key);
                response.setHeader("session_key", session_key);
                log.info("通过session_key登录");
                return true;
            } else {
                response.setStatus(401);
                return false;
            }

        }
        if (StringUtils.hasText(code)) {
            JSONObject openIdres = openIdUtil.getOpenId(code);
            log.info("code:{}", code);
            if (openIdres.getBoolean("flag")) {
                request.setAttribute("openId", openIdres.getString("openId"));
                //request.setAttribute("session_key", openIdres.getString("session_key"));
                response.setHeader("session_key", openIdres.getString("session_key"));
                log.info("通过code登录");
                return true;
            } else {
                response.setStatus(408);
                try (PrintWriter writer = response.getWriter()) {
                    writer.write(openIdres.toJSONString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        }
        response.setStatus(400);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //log.info("执行了postHandle（）");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //log.info("执行了afterCompletion（）");
    }
}
