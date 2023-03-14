package com.fch.buffetorder.service;

import com.fch.buffetorder.api.ResponseBean;
import com.fch.buffetorder.dto.AdminDto;
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

    ResponseBean getInfo(String token);

    ResponseBean queryAllAdminInfo();

    ResponseBean resetPasswordById(Integer id);

    ResponseBean updatePassword(AdminDto adminDto);
}
