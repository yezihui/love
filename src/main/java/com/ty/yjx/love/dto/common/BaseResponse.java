package com.ty.yjx.love.dto.common;

import lombok.Data;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/11
 */
@Data
public class BaseResponse {

    private String result;
    private String retMsg;
    private Object retData;
}
