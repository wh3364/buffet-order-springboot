package com.fch.buffetorder.controller;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.entity.Cate;
import com.fch.buffetorder.service.CateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-11-03 13:52
 **/
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("Admin")
public class AdminController {
    @Autowired
    CateService cateService;

    @GetMapping("GetAllCates")
    public ResponseEntity getAllCates() {
        return new ResponseEntity(cateService.adminQueryAllCates(), HttpStatus.OK);
    }

    @PostMapping("UpdateCate")
    public ResponseEntity updateCate(@RequestBody String json) {
        Cate cate;
        try {
            cate = JSONObject.parseObject(json, Cate.class);
            if (!StringUtils.hasText(cate.getCateName())){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(cateService.updateCate(cate), HttpStatus.OK);
    }

    @PostMapping("AddCate")
    public ResponseEntity addCate(@RequestBody String json) {
        Cate cate;
        try {
            cate = JSONObject.parseObject(json, Cate.class);
            if (!StringUtils.hasText(cate.getCateName())){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        log.info(cate.toString());
        return new ResponseEntity(cateService.addCate(cate), HttpStatus.OK);
    }
}
