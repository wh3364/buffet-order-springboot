package com.fch.buffetorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.entity.Admin;
import com.fch.buffetorder.mapper.AdminMapper;
import com.fch.buffetorder.service.AdminService;
import com.fch.buffetorder.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-10-31 19:23
 **/
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminMapper.findAdminByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("身份错误!");
        }
        return admin;
    }

    @Override
    public Admin insertAdmin(Admin newAdmin) {
        Admin admin = new Admin();
        admin.setUsername(newAdmin.getUsername());
        admin = adminMapper.queryAdminByUsername(admin);
        if (admin != null) {
            return null;
        }
        if (!StringUtils.hasText(newAdmin.getRole())) {
            newAdmin.setRole("assistant");
        }
        adminMapper.insertAdmin(newAdmin);
        return newAdmin;
    }

    @Override
    public JSONObject getInfo(String token) {
        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("message", "查询失败");
        Claims claims;
        try {
            claims = jwtUtils.getClaimsFromToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return res;
        }
        Admin admin = new Admin();
        admin.setUsername(claims.getSubject());
        admin = adminMapper.queryAdminByUsername(admin);
        if (admin != null) {
            res.put("message", "查询成功");
            res.put("data", admin);
            res.put("code", 200);
            return res;
        }
        return res;
    }
}
