package com.fch.buffetorder.controller;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.entity.User;
import com.fch.buffetorder.service.UserService;
import com.fch.buffetorder.util.OpenIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: BuffetOrder
 * @description: 登录
 * @CreatedBy: fch
 * @create: 2022-10-14 12:21
 **/
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("Login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private OpenIdUtil openIdUtil;

    @PostMapping("RegUser")
    public ResponseEntity regUser(HttpServletRequest request) {
        String code = request.getHeader("code");
        JSONObject openIdres = openIdUtil.getOpenId(code);
        if (openIdres.getBoolean("flag") && StringUtils.hasText(openIdres.getString("openId"))) {
            User user = new User();
            user.setOpenId(openIdres.getString("openId"));
            if (!userService.isExistByOpenId(user)) {
                userService.regUser(user);
            }
            user.setOpenId(null);
            JSONObject resp = new JSONObject();
            resp.put("user", user);
            log.info("注册用户{}", user);
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("LoginUser")
    public ResponseEntity loginUser(@RequestAttribute("openId") String openId) {
        if (StringUtils.hasText(openId)) {
            User user = new User();
            user.setOpenId(openId);
            user = userService.getUserByOpenId(user);
            JSONObject resp = new JSONObject();
            user.setOpenId(null);
            resp.put("user", user);
            log.info("登录用户{}", user);
            return new ResponseEntity<>(resp, HttpStatus.OK);

        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }

//    @PostMapping("Test")
//    public ResponseEntity testRides(@RequestBody String json) {
//        return new ResponseEntity(openIdUtil.getOpenIdFromSession(JSONObject.parseObject(json).getString("session_key")), HttpStatus.OK);
//    }

    /**
     * 根据微信更新昵称和头像
     *
     * @param json
     * @return
     */
    @PostMapping("UploadInfo")
    public ResponseEntity uploadUserNickAvatar(@RequestBody() String json,
                                               @RequestAttribute("openId") String openId) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        User user = new User();
        user.setOpenId(openId);
        if (!userService.isExistByOpenId(user)) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        String msg = "更新失败";
        String nick = jsonObject.getString("nick");
        String avatar = jsonObject.getString("avatar");
        user.setNickName(nick);
        user.setAvatarPath(avatar);
        boolean haveException = false;
        try {
            userService.uploadUserAvatar(user);
        } catch (Exception e) {
            log.info("更新头像出错{}", user);
            user.setAvatarPath("https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0");
            msg = "更新头像出错";
            userService.uploadUserAvatar(user);
            haveException = true;
        }
        try {
            userService.uploadUserNick(user);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("更新昵称出错{}", user);
            user.setNickName("微信用户");
            msg = "更新昵称出错";
            userService.uploadUserNick(user);
            haveException = true;
        }
        if (haveException) {
            Map<String, Object> map = new HashMap<>();
            map.put("msg", msg);
            map.put("user", user);
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("更新用户信息成功{}", user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
