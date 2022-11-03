package com.fch.buffetorder.service;

import com.fch.buffetorder.entity.Cate;

import java.util.List;

/**
 * @program: BuffetOrder
 * @description: 食物分类服务接口
 * @CreatedBy: fch
 * @create: 2022-10-15 16:44
 **/
public interface CateService {
    List<Cate> queryAllCates();

    List<Cate> AdminQueryAllCates();
}
