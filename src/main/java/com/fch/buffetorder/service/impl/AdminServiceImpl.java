package com.fch.buffetorder.service.impl;

import com.fch.buffetorder.entity.Admin;
import com.fch.buffetorder.mapper.AdminMapper;
import com.fch.buffetorder.service.AdminService;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminMapper.findAdminByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("身份错误!"); //抛出异常
        }
        return admin;
    }

    @Override
    public Admin insertAdmin(Admin admin) {
        if (!StringUtils.hasText(admin.getRole())){
            admin.setRole("assistant");
        }
        adminMapper.insertAdmin(admin);
        return admin;
    }
}
