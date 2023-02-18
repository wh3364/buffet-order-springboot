package com.fch.buffetorder.service;

import com.fch.buffetorder.entity.Address;
import com.fch.buffetorder.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * @program: BuffetOrder
 * @description: 登录服务接口
 * @CreatedBy: fch
 * @create: 2022-10-14 15:30
 **/
public interface UserService {

    User addMoney(User user, BigDecimal money);

    boolean regUser(User user);

    Address getAddressByUserId(User user);

    boolean addOrUploadAddress(User user, Address newAddress);

    boolean uploadUserNick(User user);

    boolean uploadUserAvatar(User user, MultipartFile file);

    boolean isExistByOpenId(User user);

    User getUserByOpenId(User user);

    User queryUserIdByOpenId(User user);

    String queryUserNickByOpenId();

    String queryUserAvatarByOpenId();

    String WeiXinLogin(String code);

    ResponseEntity<User> uploadUserNickAvatar(User user);
}
