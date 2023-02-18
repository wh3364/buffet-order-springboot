package com.fch.buffetorder.controller;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.entity.Address;
import com.fch.buffetorder.entity.User;
import com.fch.buffetorder.service.UserService;
import com.fch.buffetorder.util.UploadImgUtil;
import com.fch.buffetorder.util.WeiXinParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping("GetInfo")
//    public ResponseEntity loginWeiXin(@RequestBody() String openId) {
//        JSONObject jsonObject = JSONObject.parseObject(openId);
//        if (StringUtils.hasText(jsonObject.getString("openId"))) {
//            User user = new User();
//            user.setOpenId(jsonObject.getString("openId"));
//            user = userService.getUserByOpenId(user);
//            if (user != null){
//                log.info("返回用户信息{}", user);
//                return new ResponseEntity(user, HttpStatus.OK);
//            }
//        }
//        return new ResponseEntity(HttpStatus.BAD_REQUEST);
//    }

    @PostMapping("AddMoney")
    public ResponseEntity addMoney(@RequestAttribute("openId") String openId) {
        User user = new User();
        user.setOpenId(openId);
        user = userService.addMoney(user, BigDecimal.valueOf(100));
        user.setOpenId(null);
        user.setUserId(null);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("GetAddress")
    public ResponseEntity addOrUploadAddress(@RequestAttribute("openId") String openId) {
        User user = new User();
        user.setOpenId(openId);
        Address address = userService.getAddressByUserId(user);
        JSONObject resp = new JSONObject();
        resp.put("address", address);
        log.info("查询地址{}", address);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("AddOrUploadAddress")
    public ResponseEntity addOrUploadAddress(@RequestBody() String json,
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
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("UploadAvatar")
    public ResponseEntity uploadAvatar(@RequestParam("File") MultipartFile file,
                                       @RequestAttribute("openId") String openId,
                                       HttpServletRequest request) {
        if (!file.isEmpty()) {
            User user = new User();
            user.setOpenId(openId);
            if (userService.uploadUserAvatar(user, file)){
                log.info("更新用户头像{}", user);
                user.setOpenId(null);
                JSONObject resp = new JSONObject();
                resp.put("user", user);
                return new ResponseEntity<>(resp, HttpStatus.OK);
            }else {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("UploadNick")
    public ResponseEntity uploadNick(@RequestBody() String json,
                                     @RequestAttribute("openId") String openId) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (StringUtils.hasText(jsonObject.getString("nick"))) {
            User user = new User();
            user.setOpenId(openId);
            user.setNickName(jsonObject.getString("nick"));
            userService.uploadUserNick(user);
            user.setOpenId(null);
            JSONObject resp = new JSONObject();
            resp.put("user", user);
            log.info("更新姓名{}", user);
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
