package com.fch.buffetorder.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-10-27 22:57
 **/
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("Admin")
public class AdminController {

    @PostMapping("login")
    public ResponseEntity adminLogin(){
        log.info("执行了:adminLogin()");
        JSONObject r = new JSONObject();
        JSONObject resp = new JSONObject();
        r.put("token", "admin-token");
        resp.put("code", 20000);
        resp.put("data", r);
        return new ResponseEntity(resp, HttpStatus.OK);
    }

    @GetMapping("Info")
    public ResponseEntity getAdminInfo(){
        log.info("执行了:getAdminInfo()");
        JSONObject r = new JSONObject();
        JSONObject resp = new JSONObject();
        r.put("roles", "admin");
        r.put("introduction", "I am a super administrator");
        r.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        r.put("name", "Super Admin");
        resp.put("code", 20000);
        resp.put("data", r);
        return new ResponseEntity(resp, HttpStatus.OK);
    }

    @PostMapping("Logout")
    public ResponseEntity adminLogout(){
        log.info("执行了:adminLogout()");
        JSONObject resp = new JSONObject();
        resp.put("code", 20000);
        resp.put("data", "success");
        return new ResponseEntity(resp, HttpStatus.OK);
    }

    @GetMapping("List")
    public ResponseEntity getList(){
        log.info("执行了:adminLogout()");
        JSONArray rs = new JSONArray();
        JSONObject r = new JSONObject();
        JSONObject data = new JSONObject();
        r.put("id", "id");
        r.put("title", "title");
        r.put("author", "author");
        r.put("display_time", "display_time");
        r.put("pageviews", "pageviews");
        r.put("status", "published");
        rs.add(r);
        JSONObject resp = new JSONObject();
        resp.put("code", 20000);
        data.put("total", 1);
        data.put("items", rs);
        resp.put("data", data);
        return new ResponseEntity(resp, HttpStatus.OK);
    }
}
