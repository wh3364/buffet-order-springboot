package com.fch.buffetorder.service;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.api.ResponseBean;
import com.fch.buffetorder.entity.Admin;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-10-31 19:23
 **/
public interface AdminService extends UserDetailsService {

    Admin insertAdmin(Admin admin);

    ResponseBean getInfo(String token);

    ResponseBean queryAllAdminInfo();
}
