package com.fch.buffetorder.mapper;

import com.fch.buffetorder.entity.Address;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-10-30 23:09
 **/
@Mapper
@Repository
public interface AddressMapper {

    int addAddress(Address address);

    int uploadAddress(Address address);

    Address queryAddressByUserId(Address address);
}
