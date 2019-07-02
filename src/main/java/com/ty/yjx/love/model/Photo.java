package com.ty.yjx.love.model;

import lombok.Data;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/25
 */
@Data
public class Photo {
    /** 自增主键id */
    private Integer id;

    /** 图片url */
    private String photoUrl;

    /** 分组日期 */
    private String groupDate;
}