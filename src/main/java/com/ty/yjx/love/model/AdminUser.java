package com.ty.yjx.love.model;

import lombok.Data;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/25
 */
@Data
public class AdminUser {

    /** 自增主键id */
    private Integer id;

    /** 登录名 */
    private String userName;

    /** 密码 */
    private String password;

    /** 昵称 */
    private String nickname;

}