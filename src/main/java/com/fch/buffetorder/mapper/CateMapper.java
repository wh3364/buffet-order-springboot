package com.fch.buffetorder.mapper;

import com.fch.buffetorder.entity.Cate;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: BuffetOrder
 * @description: 食物分类Mapper
 * @CreatedBy: fch
 * @create: 2022-10-15 16:39
 **/
@Mapper
@Repository
public interface CateMapper {
    List<Cate> queryAllCates();

    List<Cate> adminQueryAllCates();

    int updateCate(Cate cate);

    int insertCate(Cate cate);
}
