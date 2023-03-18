package com.fch.buffetorder.mapper;

import com.fch.buffetorder.entity.CouponTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 8.优惠券模板表(CouponTemplate)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-18 10:29:49
 */
@Mapper
public interface CouponTemplateMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CouponTemplate queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<CouponTemplate> queryAll();

    /**
     * 新增数据
     *
     * @param couponTemplate 实例对象
     * @return 影响行数
     */
    int insert(CouponTemplate couponTemplate);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<CouponTemplate> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<CouponTemplate> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<CouponTemplate> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<CouponTemplate> entities);

    /**
     * 修改数据
     *
     * @param couponTemplate 实例对象
     * @return 影响行数
     */
    int update(CouponTemplate couponTemplate);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

