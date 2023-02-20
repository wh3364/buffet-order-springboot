package com.fch.buffetorder.controller;

import com.fch.buffetorder.api.ResponseBean;
import com.fch.buffetorder.service.CateService;
import com.fch.buffetorder.service.FoodService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class FoodController {

    private final CateService cateService;

    private final FoodService foodService;

    @GetMapping("GetAllCate")
    public ResponseBean getAllCate() {
        return cateService.queryAllCates();
    }

    @GetMapping("GetAllFood")
    public ResponseBean  getAllFoots(){
        return foodService.queryAllFoods();
    }
}
