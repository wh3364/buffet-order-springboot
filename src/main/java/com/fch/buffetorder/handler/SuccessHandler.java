package com.fch.buffetorder.handler;

import com.fch.buffetorder.entity.Admin;
import com.fch.buffetorder.mapper.AdminMapper;
import com.fch.buffetorder.util.JwtUtils;
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
public class SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    JwtUtils jwtUtils;

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

        //返回登陆成功信息
        response.setContentType("application/json;charset=utf-8");
//        BaseResponse baseResponse = new BaseResponse();
//        baseResponse.setCode(0);
//        baseResponse.setMsg("成功");
//        user.setUserPasw("");
//        baseResponse.setData(user);
//        Gson gson = new Gson();
        try (PrintWriter out = response.getWriter()) {
            out.write("{\"code\":1,\"msg\":\"成功\"}");
            out.close();
            out.flush();
        }
    }
}
