package com.fch.buffetorder.controller;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.api.ResponseBean;
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
import javax.servlet.http.HttpServletResponse;

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
    public ResponseBean regUser(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getHeader("code");
        JSONObject openIdres = openIdUtil.getOpenId(code);
        if (openIdres.getBoolean("flag") && StringUtils.hasText(openIdres.getString("openId"))) {
            User user = new User();
            user.setOpenId(openIdres.getString("openId"));
            if (!userService.isExistByOpenId(user)) {
                userService.regUser(user);
            }
            user.setOpenId(null);
            log.info("注册用户{}", user);
            response.setHeader("session_key", openIdres.getString("session_key"));
            return ResponseBean.ok(user);
        }
        return ResponseBean.badRequest("坏请求");
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

    /**
     * 根据微信更新昵称和头像
     *
     * @param json
     * @return
     */
    @PostMapping("UploadInfo")
    public ResponseEntity<User> uploadUserNickAvatar(@RequestBody() String json,
                                               @RequestAttribute("openId") String openId) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        User user = new User();
        user.setOpenId(openId);
        if (!userService.isExistByOpenId(user)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        user.setNickName(jsonObject.getString("nick"));
        user.setAvatarPath(jsonObject.getString("avatar"));
        return userService.uploadUserNickAvatar(user);
    }
}
