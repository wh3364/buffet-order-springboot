package com.fch.buffetorder.filter;

import com.fch.buffetorder.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chenwei
 * 所有请求都会被拦截
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    JwtUtils jwtUtils;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //读取request头部的token
        String header = request.getHeader("token");
        if (header == null) {
            if (request.getRequestURI().matches("^/BuffetOrder/img/food/\\S*\\.png$")){
                chain.doFilter(request, response);
            }
            else {
                response.setStatus(401);
                chain.doFilter(request, response);
            }
            return;
        }
        //解析token
        Claims token = jwtUtils.getClaimsFromToken(header);
        if (token == null) {
            //过期了，无认证放行
            chain.doFilter(request, response);
            return;
        }
        //从token中获取角色信息
        UsernamePasswordAuthenticationToken authenticationToken = jwtUtils.buildAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //token临近过期，发放新的token
        if (jwtUtils.isRefress(token)) {
            //快过期了，刷新token
            header = jwtUtils.generatorToken(token.getSubject(), token.get("role", String.class));
            response.addHeader("token", header);
        }
        //认证放行
        chain.doFilter(request, response);
    }

}
