package com.fch.buffetorder.dot;

import lombok.Data;

/**
 * @author fch
 * @program BuffetOrder
 * @description admin的更新密码
 * @create 2023-03-08 20:50
 **/
@Data
public class AdminDto {
    private String oldPassword;
    private String newPassword;
}
