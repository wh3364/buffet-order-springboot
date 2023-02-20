package com.fch.buffetorder.handler;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.entity.Admin;
import com.fch.buffetorder.mapper.AdminMapper;
import com.fch.buffetorder.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-10-31 22:06
 **/
@Component
@RequiredArgsConstructor
public class SuccessHandler implements AuthenticationSuccessHandler {

    private final AdminMapper adminMapper;

    private final JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Admin admin = adminMapper.findAdminByUsername(String.valueOf(request.getAttribute("username")));
        //获取用户名
        String userName = admin.getUsername();
        //获取user中角色
        String role = admin.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        //生成token
        String token = jwtUtils.generatorToken(userName, role);
        response.addHeader("token", token);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 200);
        jsonObject.put("message", "登录成功");
        response.setContentType("application/json;charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            out.write(jsonObject.toJSONString());
            out.close();
            out.flush();
        }
    }
}
