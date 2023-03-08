package com.fch.buffetorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.api.ResponseBean;
import com.fch.buffetorder.dot.AdminDto;
import com.fch.buffetorder.entity.Admin;
import com.fch.buffetorder.entity.detail.R;
import com.fch.buffetorder.mapper.AdminMapper;
import com.fch.buffetorder.service.AdminService;
import com.fch.buffetorder.util.JwtUtils;
import com.fch.buffetorder.util.ThreadLocalUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

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
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
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

    @Override
    public ResponseBean resetPasswordById(Integer id) {
        Admin admin = adminMapper.queryAdminById(id);
        if (Objects.isNull(admin)){
            return ResponseBean.badRequest("此账号不存在");
        }
        String password = "123456";
        admin.setPassword(password);
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        admin.setPassword(bcryptPasswordEncoder.encode(admin.getPassword()));
        if (adminMapper.updatePasswordById(admin) > 0){
            admin.setPassword(password);
            return ResponseBean.ok(admin);
        }else {
            return ResponseBean.badRequest("修改失败");
        }
    }

    @Override
    public ResponseBean updatePassword(AdminDto adminDto) {
        String username = (String) ThreadLocalUtils.get("username");
        if (Objects.isNull(username)){
            return ResponseBean.badRequest("身份认证失败");
        }
        if (adminDto.getNewPassword().length() < 6){
            return ResponseBean.badRequest("密码小于6位");
        }
        String password = adminMapper.queryAdminPasswordByUsername(username);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (bCryptPasswordEncoder.matches(adminDto.getOldPassword(), password)) {
            adminMapper.updatePasswordByUsername(bCryptPasswordEncoder.encode(adminDto.getNewPassword()), username);
            return ResponseBean.ok(null, "更新成功");
        }else {
            return ResponseBean.badRequest("密码不匹配");
        }
    }
}
