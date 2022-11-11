package com.fch.buffetorder.controller;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.entity.Cate;
import com.fch.buffetorder.entity.Food;
import com.fch.buffetorder.service.CateService;
import com.fch.buffetorder.service.FoodService;
import com.fch.buffetorder.util.UploadImgUtil;
import com.fch.buffetorder.util.WeiXinParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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

    @Autowired
    FoodService foodService;

    @Autowired
    UploadImgUtil uploadImgUtil;

    @Autowired
    WeiXinParam weiXinParam;

    @GetMapping("GetAllCates")
    public ResponseEntity getAllCates() {
        return new ResponseEntity(cateService.adminQueryAllCates(), HttpStatus.OK);
    }

    @PostMapping("UpdateCate")
    public ResponseEntity updateCate(@RequestBody Cate cate) {
        if (!StringUtils.hasText(cate.getCateName())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (cate.getCateWeight() == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(cateService.updateCate(cate), HttpStatus.OK);
    }

    @PostMapping("AddCate")
    public ResponseEntity addCate(@RequestBody Cate cate) {
        cate.setCateId(null);
        cate.setIsEnable(1);
        if (!StringUtils.hasText(cate.getCateName())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (cate.getCateWeight() == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(cateService.addCate(cate), HttpStatus.OK);
    }

    @GetMapping("GetAllFoods")
    public ResponseEntity getAllFoods() { return new ResponseEntity(foodService.adminQueryAllFoods(), HttpStatus.OK); }

    @PostMapping("UpdateFoodImg")
    public ResponseEntity updateFoodImg(@RequestParam("File") MultipartFile file,
                                        @RequestParam() Integer foodId,
                                        HttpServletRequest request) {
        Food food = new Food();
        food.setFoodId(foodId);
        if (!foodService.isExistsByFoodId(food) || file.isEmpty()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        JSONObject res = new JSONObject();
        String imgPath;
        try {
            imgPath =  weiXinParam.getIMG_PATH() + uploadImgUtil.uploadImg("img/food/", foodId.toString(), file, request);
        } catch (IOException e) {
            res.put("code", 500);
            res.put("message", "上传图片失败");
            return new ResponseEntity(res, HttpStatus.OK);
        }
        res = foodService.updateFoodImg(foodId, imgPath);
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PostMapping("UpdateFood")
    public ResponseEntity updateFood(@RequestBody String json) {
        log.info(json);
        Food food = JSONObject.parseObject(json, Food.class);
        JSONObject res = foodService.updateFood(food);
        log.info(food.toString());
       return new ResponseEntity(res, HttpStatus.OK);
    }

    @PostMapping("AddFood")
    public ResponseEntity addFood(@RequestBody String json) {
        log.info(json);
        Food food = JSONObject.parseObject(json, Food.class);
        JSONObject res = foodService.addFood(food);
        log.info(food.toString());
        return new ResponseEntity(res, HttpStatus.OK);
    }
}
