package com.fch.buffetorder.mapper;

import com.fch.buffetorder.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-10-31 19:45
 **/
@Mapper
@Repository
public interface AdminMapper {
    int insertAdmin(Admin admin);

    Admin queryAdminByUsername(Admin admin);

    Admin findAdminByUsername(@Param("username")String username);

    List<Admin> queryAllAdminInfo();

    Admin queryAdminById(Integer id);

    int updatePasswordById(Admin admin);

    String queryAdminPasswordByUsername(String username);

    int updatePasswordByUsername(@Param("password")String password, @Param("username")String username);
}
