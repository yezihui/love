package com.ty.yjx.love.dto;

import lombok.Data;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/10
 */
@Data
public class AccessTokenResultDto {

    private String accessToken;

    private int expireIn;

    private long timestamp;
}
