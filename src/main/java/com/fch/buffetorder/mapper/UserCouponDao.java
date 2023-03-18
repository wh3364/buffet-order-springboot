package com.fch.buffetorder.mapper;

import com.fch.buffetorder.entity.UserCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 9.用户优惠券表(UserCoupon)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-18 10:25:51
 */
@Mapper
public interface UserCouponDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserCoupon queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<UserCoupon> queryAll();

    /**
     * 新增数据
     *
     * @param userCoupon 实例对象
     * @return 影响行数
     */
    int insert(UserCoupon userCoupon);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserCoupon> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<UserCoupon> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserCoupon> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<UserCoupon> entities);

    /**
     * 修改数据
     *
     * @param userCoupon 实例对象
     * @return 影响行数
     */
    int update(UserCoupon userCoupon);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

