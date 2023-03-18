package com.fch.buffetorder.controller;

import com.fch.buffetorder.api.ResponseBean;
import com.fch.buffetorder.dto.CouponAuditDto;
import com.fch.buffetorder.entity.Cate;
import com.fch.buffetorder.entity.Detail;
import com.fch.buffetorder.entity.Food;
import com.fch.buffetorder.service.AdminService;
import com.fch.buffetorder.service.CateService;
import com.fch.buffetorder.service.CouponService;
import com.fch.buffetorder.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-11-03 13:52
 **/
@RestController
@RequestMapping("Admin")
@RequiredArgsConstructor
public class AdminController {

    private final CateService cateService;

    private final FoodService foodService;

    private final AdminService adminService;

    private final CouponService couponService;

    @GetMapping("GetAllCates")
    public ResponseBean getAllCates() {
        return cateService.adminQueryAllCates();
    }

    @PostMapping("UpdateCate")
    public ResponseBean updateCate(@RequestBody Cate cate) {
        assertCate(cate);
        return cateService.updateCate(cate);
    }

    @PostMapping("AddCate")
    public ResponseBean addCate(@RequestBody Cate cate) {
        assertCate(cate);
        cate.setCateId(null);
        cate.setIsEnable(1);
        return cateService.addCate(cate);
    }

    @GetMapping("GetAllFoods")
    public ResponseBean getAllFoods() {
        return foodService.adminQueryAllFoods();
    }

    @PostMapping("UpdateFoodImg")
    public ResponseBean updateFoodImg(@RequestParam("File") MultipartFile file,
                                      @RequestParam Integer foodId,
                                      HttpServletRequest request) {
        Food food = new Food();
        food.setFoodId(foodId);
        if (!foodService.isExistsByFoodId(food) || file.isEmpty()){
            return ResponseBean.badRequest("还未创建食物或图片为空");
        }
        return foodService.updateFoodImg(food, file, request);
    }

    @PostMapping("UpdateFood")
    public ResponseBean updateFood(@RequestBody Food food) {
        assertFood(food);
        return foodService.updateFood(food);
    }

    @PostMapping("AddFood")
    public ResponseBean addFood(@RequestBody Food food) {
        assertFood(food);
        return foodService.addFood(food);
    }

    @GetMapping("GetAllDetails")
    public ResponseBean getAllDetails() {
        return foodService.queryAllDefault();
    }

    @PostMapping("UpdateDetail")
    public ResponseBean updateDetail(@RequestBody Detail detail) {
        assertDetail(detail);
        return foodService.updateDetail(detail);
    }

    @PostMapping("AddDetail")
    public ResponseBean addDetail(@RequestBody Detail detail) {
        assertDetail(detail);
        return foodService.addDetail(detail);
    }

    @PostMapping("DeleteDetail")
    public ResponseBean deleteDetail(@RequestBody Detail detail) {
        assertDetail(detail);
        return foodService.deleteDetail(detail);
    }

    @GetMapping("QueryAllAdminInfo")
    public ResponseBean queryAllAdminInfo() {
        return adminService.queryAllAdminInfo();
    }

    @PostMapping("reset/{id}")
    public ResponseBean resetAdmin(@PathVariable Integer id) {
        return adminService.resetPasswordById(id);
    }

    @PostMapping("coupon")
    public ResponseBean audit(@RequestBody CouponAuditDto dto) {
        return couponService.audit(dto);
    }

    private void assertCate(Cate cate){
        Assert.hasText(cate.getCateName(), "名字不能为空");
        Assert.notNull(cate.getCateWeight(), "权重不能为空");
    }

    private void assertFood(Food food){
        Assert.hasText(food.getFoodName(), "名字不能为空");
        Assert.notNull(food.getFoodPrice(), "售价不能为空");
        Assert.notNull(food.getFoodWeight(), "权重不能为空");
    }

    private void assertDetail(Detail detail){
        Assert.hasText(detail.getDetailName(), "名字不能为空");
        Assert.notNull(detail.getDetailPrice(), "售价不能为空");
        Assert.notNull(detail.getDetailType(), "标签不能为空");
        if (detail.getDetailType() != 0 && detail.getDetailType() != 1){
            throw new IllegalArgumentException("标签错误");
        }
    }
}
