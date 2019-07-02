package com.ty.yjx.love.service;

import com.ty.yjx.love.cache.LoveCache;
import com.ty.yjx.love.constant.LoveConstant;
import com.ty.yjx.love.dto.weixin.ScanInDto;
import com.ty.yjx.love.dto.weixin.ScanOutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/12
 */
@Component
public class MessageHandleContext {

    @Autowired
    private Map<String, MessageHandleStrategy> messageHandleStrategyMap;

    @Autowired
    private LoveCache loveCache;

    public ScanOutDto handle(ScanInDto scanInDto) {
        MessageHandleStrategy strategy;
        if (LoveConstant.IMAGE.equals(scanInDto.getMsgType())) {
            strategy  = messageHandleStrategyMap.get("imageHandleStrategy");
            return strategy.handle(scanInDto);
        }
        String change = loveCache.getUserChange(scanInDto.getFromUserName());
        if ("3".equals(change)) {
            strategy  = messageHandleStrategyMap.get("weatherHandleStrategy");
        } else {
            strategy = messageHandleStrategyMap.get("commonHandleStrategy");
        }
        return strategy.handle(scanInDto);
    }
}
