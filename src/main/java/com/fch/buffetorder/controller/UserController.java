package com.fch.buffetorder.controller;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.entity.User;
import com.fch.buffetorder.service.UserService;
import com.fch.buffetorder.util.JsonUtil;
import com.fch.buffetorder.util.WeiXinParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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

    @Autowired
    private WeiXinParam weiXinParam;

    @Autowired
    private JsonUtil jsonUtil;

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

    @PostMapping("UploadAvatar")
    public ResponseEntity uploadAvatar(@RequestParam("File") MultipartFile file
            , @RequestAttribute("openId") String openId, @RequestAttribute("session_key") String sessionKey, HttpServletRequest request) {
        if (!file.isEmpty()) {
            if (jsonUtil.needReg(openId)) {
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
            User user = new User();
            user.setOpenId(openId);
            String avatarPath;
            try {
                avatarPath = weiXinParam.IMG_PATH + uploadImg("img/avatar/", UUID.randomUUID().toString(), file, request);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            user.setAvatarPath(avatarPath);
            userService.uploadUserAvatar(user);
            log.info("更新用户头像{}", user);
            user.setOpenId(null);
            JSONObject resp = new JSONObject();
            resp.put("user", user);
            resp.put("session_key", sessionKey);
            return new ResponseEntity(resp, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("UploadNick")
    public ResponseEntity uploadNick(@RequestBody() String json
            , @RequestAttribute("openId") String openId, @RequestAttribute("session_key") String sessionKey) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (StringUtils.hasText(jsonObject.getString("nick"))) {
            if (jsonUtil.needReg(openId)) {
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
            User user = new User();
            user.setOpenId(openId);
            user.setNickName(jsonObject.getString("nick"));
            userService.uploadUserNick(user);
            user.setOpenId(null);
            JSONObject resp = new JSONObject();
            resp.put("user", user);
            resp.put("session_key", sessionKey);
            log.info("更新姓名{}", user);
            return new ResponseEntity(resp, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    private String uploadImg(String childPath, String imgName, MultipartFile file, HttpServletRequest request) throws IOException {
        //1.创建上传的文件存放的文件夹
        //得到存放文件的路径:静态资源路径+那你自己定义的"upload/2022-04-13/"
        //静态资源路径：如果resources下面有public或者static文件夹，那么我们就把它当成静态资源路径（idea需要设置），如过没有，才会用那个临时文件夹
        String path = request.getServletContext().getRealPath(childPath);
        //创建文件夹，但凡你要操作文件或者文件夹，你都要先创建它对应的File对象
        File desDir = new File(path);
        if (!desDir.exists()) {
            //mkdirs()和mkdir()区别
            desDir.mkdirs();
        }
        //2.上传文件
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String desFileName = imgName + suffix;
        //但凡你要操作文件或者文件夹，你都要先创建它对应的File对象
        File desFile = new File(path, desFileName);
        try {
            file.transferTo(desFile);
        } catch (IOException e) {
            throw e;
        }
        String reFilePath = childPath + desFileName;
        return reFilePath;
    }

}
