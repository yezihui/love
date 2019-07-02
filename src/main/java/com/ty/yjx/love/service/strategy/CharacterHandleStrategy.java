package com.ty.yjx.love.service.strategy;

import com.ty.yjx.love.dto.weixin.ScanInDto;
import com.ty.yjx.love.dto.weixin.ScanOutDto;
import com.ty.yjx.love.service.MessageHandleStrategy;
import org.springframework.stereotype.Service;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/12
 */
@Service
public class CharacterHandleStrategy implements MessageHandleStrategy {
    @Override
    public ScanOutDto handle(ScanInDto scanInDto) {
        return null;
    }
}
