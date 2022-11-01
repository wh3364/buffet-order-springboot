package com.fch.buffetorder.service;

import com.fch.buffetorder.entity.Admin;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-10-31 19:23
 **/
public interface AdminService extends UserDetailsService {

    Admin insertAdmin(Admin admin);
}
