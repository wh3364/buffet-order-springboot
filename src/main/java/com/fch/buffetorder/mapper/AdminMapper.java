package com.fch.buffetorder.mapper;

import com.fch.buffetorder.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
}
