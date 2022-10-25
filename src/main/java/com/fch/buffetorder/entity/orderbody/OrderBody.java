package com.fch.buffetorder.entity.orderbody;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: BuffetOrder
 * @description: 前端请求的格式
 * @CreatedBy: fch
 * @create: 2022-10-21 20:08
 **/
@Data
public class OrderBody implements Serializable {
    private Integer id;
    private Integer hD;
    private Integer numb;
    private List<DM> m;
    private List<DR> r;
}
