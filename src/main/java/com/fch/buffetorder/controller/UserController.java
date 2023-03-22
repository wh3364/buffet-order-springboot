package com.fch.buffetorder.controller;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.api.ResponseBean;
import com.fch.buffetorder.entity.Address;
import com.fch.buffetorder.entity.User;
import com.fch.buffetorder.service.UserService;
import com.fch.buffetorder.util.OpenIdUtil;
import com.fch.buffetorder.util.UploadImgUtil;
import com.fch.buffetorder.util.WeiXinParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * @program: BuffetOrder
 * @description: 用户控制类
 * @CreatedBy: fch
 * @create: 2022-10-15 15:15
 **/
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("User")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final OpenIdUtil openIdUtil;

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
    public ResponseEntity<?> loginUser(@RequestAttribute("openId") String openId,
                                       HttpServletResponse response) {
        if (StringUtils.hasText(openId)) {
            User user = new User();
            user.setOpenId(openId);
            String sessionKey = response.getHeader("session_key");
            if (!StringUtils.hasText(sessionKey)) {
                return ResponseEntity.badRequest().build();
            }
            user = userService.getUserByOpenId("user:" + sessionKey, user);
            JSONObject resp = new JSONObject();
            user.setOpenId(null);
            resp.put("user", user);
            log.info("登录用户{}", user);
            return ResponseEntity.ok(resp);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * 根据微信更新昵称和头像
     *
     * @param json
     * @return
     */
    @PostMapping("UploadInfo")
    public ResponseEntity<User> uploadUserNickAvatar(@RequestBody() String json,
                                                     @RequestAttribute("openId") String openId,
                                                     HttpServletRequest request) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        User user = new User();
        user.setOpenId(openId);
        if (!userService.isExistByOpenId(user)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        user.setNickName(jsonObject.getString("nick"));
        user.setAvatarPath(jsonObject.getString("avatar"));
        return userService.uploadUserNickAvatar("user:" + request.getHeader("session_key") ,user);
    }

    @PostMapping("AddMoney")
    public ResponseEntity<User> addMoney(@RequestAttribute("openId") String openId,
                                         HttpServletResponse response) {
        User user = new User();
        user.setOpenId(openId);
        user = userService.addMoney("user:" + response.getHeader("session_key"), user, BigDecimal.valueOf(100));
        user.setOpenId(null);
        user.setUserId(null);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("GetAddress")
    public ResponseEntity<?> addOrUploadAddress(@RequestAttribute("openId") String openId) {
        User user = new User();
        user.setOpenId(openId);
        Address address = userService.getAddressByUserId(user);
        JSONObject resp = new JSONObject();
        resp.put("address", address);
        log.info("查询地址{}", address);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("AddOrUploadAddress")
    public ResponseEntity<?> addOrUploadAddress(@RequestBody() String json,
                                                @RequestAttribute("openId") String openId) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (StringUtils.hasText(jsonObject.getString("address"))) {
            User user = new User();
            user.setOpenId(openId);
            Address address = new Address();
            address.setAddress(jsonObject.getString("address"));
            userService.addOrUploadAddress(user, address);
            user.setOpenId(null);
            JSONObject resp = new JSONObject();
            resp.put("user", user);
            log.info("添加或修改地址{}", user);
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("UploadAvatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("File") MultipartFile file,
                                          @RequestAttribute("openId") String openId,
                                          HttpServletResponse response) {
        if (!file.isEmpty()) {
            User user = new User();
            user.setOpenId(openId);
            if (userService.uploadUserAvatar("user:" + response.getHeader("session_key"), user, file)) {
                log.info("更新用户头像{}", user);
                user.setOpenId(null);
                JSONObject resp = new JSONObject();
                resp.put("user", user);
                return new ResponseEntity<>(resp, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("UploadNick")
    public ResponseEntity<?> uploadNick(@RequestBody() String json,
                                        @RequestAttribute("openId") String openId,
                                        HttpServletResponse response) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (StringUtils.hasText(jsonObject.getString("nick"))) {
            User user = new User();
            user.setOpenId(openId);
            user.setNickName(jsonObject.getString("nick"));
            userService.uploadUserNick("user:" + response.getHeader("session_key"), user);
            user.setOpenId(null);
            JSONObject resp = new JSONObject();
            resp.put("user", user);
            log.info("更新姓名{}", user);
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
