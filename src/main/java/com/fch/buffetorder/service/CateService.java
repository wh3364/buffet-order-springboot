package com.fch.buffetorder.service;

import com.fch.buffetorder.api.ResponseBean;
import com.fch.buffetorder.entity.Cate;


/**
 * @program: BuffetOrder
 * @description: 食物分类服务接口
 * @CreatedBy: fch
 * @create: 2022-10-15 16:44
 **/
public interface CateService {
    ResponseBean queryAllCates();

    ResponseBean adminQueryAllCates();

    ResponseBean updateCate(Cate cate);

    ResponseBean addCate(Cate cate);
}
