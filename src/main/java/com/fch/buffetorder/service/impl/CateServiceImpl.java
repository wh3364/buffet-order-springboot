package com.fch.buffetorder.service.impl;

import com.fch.buffetorder.entity.Cate;
import com.fch.buffetorder.mapper.CateMapper;
import com.fch.buffetorder.service.CateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: BuffetOrder
 * @description: 食物分类服务接口实现
 * @CreatedBy: fch
 * @create: 2022-10-15 16:45
 **/
@Service
public class CateServiceImpl implements CateService {

    @Autowired
    CateMapper cateMapper;
    /**
     * 查询所有食物分类
     * @return
     */
    @Override
    public List<Cate> queryAllCates() {
        return cateMapper.queryAllCates();
    }
}
