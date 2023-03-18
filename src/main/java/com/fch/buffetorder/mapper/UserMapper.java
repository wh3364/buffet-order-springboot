package com.fch.buffetorder.mapper;

import com.fch.buffetorder.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: BuffetOrder
 * @description: 用户Mapper
 * @CreatedBy: fch
 * @create: 2022-10-14 22:39
 **/
@Mapper
@Repository
public interface UserMapper {
    /**
     * 添加用户
     * @param user
     * @return
     */
    int addUser(User user);

    int uploadUserMoney(User user);

    int uploadUserNick(User user);

    int uploadUserAvatar(User user);

    int uploadUserPay(User user);

    User queryUserByOpenId(User user);

    User queryUserIdByOpenId(User user);

    List<Integer> queryAllUserId();

}
