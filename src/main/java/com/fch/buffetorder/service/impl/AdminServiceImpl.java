package com.fch.buffetorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.api.ResponseBean;
import com.fch.buffetorder.entity.Admin;
import com.fch.buffetorder.mapper.AdminMapper;
import com.fch.buffetorder.service.AdminService;
import com.fch.buffetorder.util.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-10-31 19:23
 **/
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;

    private final JwtUtils jwtUtils;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
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
        if (adminMapper.insertAdmin(newAdmin) > 0) {
            return newAdmin;
        } else {
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public ResponseBean getInfo(String token) {
        Claims claims;
        try {
            claims = jwtUtils.getClaimsFromToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBean.badRequest("查询失败");
        }
        Admin admin = new Admin();
        admin.setUsername(claims.getSubject());
        admin = adminMapper.queryAdminByUsername(admin);
        return admin != null ? ResponseBean.ok(admin) : ResponseBean.badRequest("查询失败");
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ResponseBean queryAllAdminInfo() {
        return ResponseBean.ok(adminMapper.queryAllAdminInfo());
    }
}
