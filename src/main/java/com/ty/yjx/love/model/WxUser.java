package com.ty.yjx.love.model;

import lombok.Data;

import java.util.Date;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/25
 */
@Data
public class WxUser {

    /** 自增主键id */
    private Integer id;

    /** openId */
    private String openId;

    /** 微信昵称 */
    private String nickName;

    /** 头像url */
    private String headImg;

    /** 创建时间 */
    private Date createTime;

}