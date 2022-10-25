package com.fch.buffetorder.mapper;

import com.fch.buffetorder.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: BuffetOrder
 * @description: 用户Mapper
 * @CreatedBy: fch
 * @create: 2022-10-14 22:39
 **/
@Mapper
public interface UserMapper {
    /**
     * 添加用户
     * @param user
     * @return
     */
    int addUser(User user);

    int uploadUserNick(User user);

    int uploadUserAvatar(User user);

    int uploadUserPay(User user);

    User queryUserByOpenId(User user);

    User queryUserIdByOpenId(User user);


}
