package com.fch.buffetorder.filter;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.entity.Admin;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-10-31 21:36
 **/
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager = this.getAuthenticationManager();

    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    //得到用户名和密码，然后去验证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;
        try {
            InputStream is = request.getInputStream();
            Admin admin = JSONObject.parseObject(is, Admin.class);
            authRequest = new UsernamePasswordAuthenticationToken(admin.getUsername(), admin.getPassword(), new ArrayList<>());
            request.setAttribute("username", admin.getUsername());
        } catch (IOException e) {
            e.printStackTrace();
            authRequest = new UsernamePasswordAuthenticationToken("", "", null);
            request.setAttribute("username", "");
        }
        setDetails(request, authRequest);
        return authenticationManager.authenticate(authRequest);
    }
}