package com.fch.buffetorder.service.impl;

import com.fch.buffetorder.api.ResponseBean;
import com.fch.buffetorder.aspect.AfterClearCache;
import com.fch.buffetorder.aspect.Cache;
import com.fch.buffetorder.aspect.BeforeClearCache;
import com.fch.buffetorder.entity.Cate;
import com.fch.buffetorder.mapper.CateMapper;
import com.fch.buffetorder.service.CateService;
import com.fch.buffetorder.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: BuffetOrder
 * @description: 食物分类服务接口实现
 * @CreatedBy: fch
 * @create: 2022-10-15 16:45
 **/
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class CateServiceImpl implements CateService {

    public static final String ALL_CATES_KEY = "buffetorder:cate:allcates";

    private final CateMapper cateMapper;

    private final RedisUtil redisUtil;

    /**
     * 查询所有食物分类
     *
     * @return List<Cate>
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Cache(key = ALL_CATES_KEY)
    public ResponseBean queryAllCates() {
//        String catesJson = Optional
//                .ofNullable(redisUtil.getStr(ALL_CATES_KEY))
//                .orElseGet(() -> {
//                    String json = JSONObject.toJSONString(cateMapper.queryAllCates());
//                    redisUtil.setStr(ALL_CATES_KEY, json, 1000 * 60 * 60);
//                    return json;
//                });

        return ResponseBean.ok(cateMapper.queryAllCates());
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ResponseBean adminQueryAllCates() {
        return ResponseBean.ok(cateMapper.adminQueryAllCates());
    }

    @Override
    @BeforeClearCache(key = ALL_CATES_KEY)
    @AfterClearCache(key = FoodServiceImpl.ALL_FOODS_KEY)
    public ResponseBean updateCate(Cate cate) {
        return cateMapper.updateCate(cate) > 0 ? ResponseBean.ok(cate, "修改成功") : ResponseBean.badRequest("修改失败");
    }

    @Override
    @BeforeClearCache(key = ALL_CATES_KEY)
    @AfterClearCache(key = FoodServiceImpl.ALL_FOODS_KEY)
    public ResponseBean addCate(Cate cate) {
        return cateMapper.insertCate(cate) > 0 ? ResponseBean.ok(cate, "添加成功") : ResponseBean.badRequest("添加失败");
    }
}
