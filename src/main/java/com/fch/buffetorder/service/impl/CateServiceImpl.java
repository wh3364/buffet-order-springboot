package com.fch.buffetorder.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.entity.Cate;
import com.fch.buffetorder.mapper.CateMapper;
import com.fch.buffetorder.service.CateService;
import com.fch.buffetorder.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @program: BuffetOrder
 * @description: 食物分类服务接口实现
 * @CreatedBy: fch
 * @create: 2022-10-15 16:45
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class CateServiceImpl implements CateService {

    public static final String ALL_CATES_KEY = "buffetorder.cate.allcates";

    @Autowired
    private CateMapper cateMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 查询所有食物分类
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Cate> queryAllCates() {
        String catesJson = Optional
                .ofNullable(redisUtil.getStr(ALL_CATES_KEY))
                .orElseGet(() -> {
                    String json = JSONObject.toJSONString(cateMapper.queryAllCates());
                    redisUtil.setStr(ALL_CATES_KEY, json, 1000 * 60 * 60);
                    return json;
                });
        return JSONArray.parseArray(catesJson, Cate.class);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public JSONObject adminQueryAllCates() {
        JSONObject res = new JSONObject();
        List<Cate> cates = cateMapper.adminQueryAllCates();
        if (cates.size() > 0) {
            res.put("data", cates);
            res.put("code", 200);
        } else {
            res.put("message", "查询失败");
            res.put("code", 0);
        }
        return res;
    }

    @Override
    public JSONObject updateCate(Cate cate) {
        JSONObject res = new JSONObject();
        if (cateMapper.updateCate(cate) > 0) {
            res.put("data", cate);
            res.put("code", 200);
            res.put("message", "修改成功");
        } else {
            res.put("code", 0);
            res.put("message", "修改失败");
        }
        return res;
    }

    @Override
    public JSONObject addCate(Cate cate) {
        JSONObject res = new JSONObject();
        if (cateMapper.insertCate(cate) > 0) {
            res.put("data", cate);
            res.put("code", 200);
            res.put("message", "添加成功");
        } else {
            res.put("code", 0);
            res.put("message", "修改失败");
        }
        return res;
    }
}
