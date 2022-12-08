package com.fch.buffetorder.controller;

import com.fch.buffetorder.service.CateService;
import com.fch.buffetorder.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @program: BuffetOrder
 * @description: 顾客请求的食物接口
 * @CreatedBy: fch
 * @create: 2022-10-15 16:47
 **/
@CrossOrigin
@RestController
@RequestMapping("Food")
public class FoodController {

    @Autowired
    private CateService cateService;

    @Autowired
    private FoodService foodService;

    @GetMapping("GetAllCate")
    public ResponseEntity getAllCate() {
        return new ResponseEntity<>(cateService.queryAllCates(), HttpStatus.OK);
    }

    @GetMapping("GetAllFood")
    public ResponseEntity  getAllFoots(){
        return new ResponseEntity<>(foodService.queryAllFoods(), HttpStatus.OK);
    }
}
