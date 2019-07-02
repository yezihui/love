package com.ty.yjx.love.service;

import com.ty.yjx.love.dto.weixin.ScanInDto;
import com.ty.yjx.love.dto.weixin.ScanOutDto;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/12
 */
public interface MessageHandleStrategy {

    ScanOutDto handle(ScanInDto scanInDto);
}
