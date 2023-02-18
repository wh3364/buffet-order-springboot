package com.fch.buffetorder.service.impl;

import com.fch.buffetorder.entity.Address;
import com.fch.buffetorder.entity.User;
import com.fch.buffetorder.mapper.AddressMapper;
import com.fch.buffetorder.mapper.UserMapper;
import com.fch.buffetorder.service.UserService;
import com.fch.buffetorder.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @program: BuffetOrder
 * @description: 登录接口实现
 * @CreatedBy: fch
 * @create: 2022-10-14 15:31
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    private RequestUtil requestUtil;

    @Override
    public User addMoney(User user, BigDecimal money) {
        User u = userMapper.queryUserByOpenId(user);
        u.setMoney(u.getMoney().add(money));
        userMapper.uploadUserMoney(u);
        return userMapper.uploadUserMoney(u) > 0 ? u : null;
    }

    /**
     * 首次登录,注册信息到数据库
     *
     * @param user
     * @return
     */
    @Override
    public boolean regUser(User user) {
        //微信用户默认姓名
        user.setNickName("微信用户");
        //微信用户默认头像
        user.setAvatarPath("https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0");
        return userMapper.addUser(user) > 0;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Address getAddressByUserId(User user) {
        user = userMapper.queryUserIdByOpenId(user);
        Address address = new Address();
        address.setUserId(user.getUserId());
        address = addressMapper.queryAddressByUserId(address);
        return address;
    }

    @Override
    public boolean addOrUploadAddress(User user, Address newAddress) {
        user = userMapper.queryUserIdByOpenId(user);
        newAddress.setUserId(user.getUserId());
        Address ad = addressMapper.queryAddressByUserId(newAddress);
        if (ad == null) {
            addressMapper.addAddress(newAddress);
            return true;
        } else {
            addressMapper.uploadAddress(newAddress);
            return true;
        }
    }

    /**
     * 个系用户昵称
     *
     * @param user
     * @return
     */
    @Override
    public boolean uploadUserNick(User user) {
        return userMapper.uploadUserNick(user) > 0;
    }

    /**
     * 更新用户头像
     *
     * @param user
     * @return
     */
    @Override
    public boolean uploadUserAvatar(User user, MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String objectName = UUID.randomUUID().toString().replaceAll("-", "")
                + fileName.substring(fileName.lastIndexOf("."));
        String url = requestUtil.uploadImg(file, objectName, "img/avatar/");
        if ("上传失败".equals(url)){
            return false;
        }else {
            user.setAvatarPath(url);
            return userMapper.uploadUserAvatar(user) > 0;
        }
    }

    /**
     * 用户是否存在
     *
     * @param user
     * @return
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public boolean isExistByOpenId(User user) {
        try {
            User u = userMapper.queryUserByOpenId(user);
            if (u != null)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User getUserByOpenId(User user) {
        try {
            return userMapper.queryUserByOpenId(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User queryUserIdByOpenId(User user) {
        return userMapper.queryUserIdByOpenId(user);
    }

    @Override
    public String queryUserNickByOpenId() {
        return null;
    }

    @Override
    public String queryUserAvatarByOpenId() {
        return null;
    }

    /**
     * 微信登录
     *
     * @param code
     * @return
     */
    @Override
    public String WeiXinLogin(String code) {
        return null;
    }

    @Override
    public ResponseEntity<User> uploadUserNickAvatar(User user) {
        return userMapper.uploadUserNick(user) + userMapper.uploadUserAvatar(user) > 1 ? ResponseEntity.ok(user) : new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
