package com.fch.buffetorder.service;

import com.fch.buffetorder.entity.User;

/**
 * @program: BuffetOrder
 * @description: 登录服务接口
 * @CreatedBy: fch
 * @create: 2022-10-14 15:30
 **/
public interface UserService {

    boolean regUser(User user);

    boolean uploadUserNick(User user);

    boolean uploadUserAvatar(User user);

    boolean isExistByOpenId(User user);

    User getUserByOpenId(User user);

    User queryUserIdByOpenId(User user);

    String queryUserNickByOpenId();

    String queryUserAvatarByOpenId();

    String WeiXinLogin(String code);
}
