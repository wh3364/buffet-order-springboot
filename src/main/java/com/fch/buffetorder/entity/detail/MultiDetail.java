package com.fch.buffetorder.entity.detail;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: BuffetOrder
 * @description: 细节多选
 * @CreatedBy: fch
 * @create: 2022-10-18 18:40
 **/
@Data
public class MultiDetail {

    private String mI;
    private List<M> mS;

}
