package com.ty.yjx.love.service.strategy;

import com.ty.yjx.love.config.IndexReceiver;
import com.ty.yjx.love.dto.weixin.ScanInDto;
import com.ty.yjx.love.dto.weixin.ScanOutDto;
import com.ty.yjx.love.service.MessageHandleStrategy;
import com.ty.yjx.love.util.HttpUtil;
import com.ty.yjx.love.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/25
 */
@Service
public class WeatherHandleStrategy implements MessageHandleStrategy {

    @Autowired
    private HttpUtil httpUtil;

    @Autowired
    private IndexReceiver indexReceiver;

    @Value("${juhe.key.simpleWeather}")
    private String weatherKey;

    @Value("${juhe.api.simpleWeather}")
    private String weatherUrl;

    @Override
    public ScanOutDto handle(ScanInDto scanInDto) {
        try {
            if (indexReceiver.getWeatherMap().containsKey(scanInDto.getContent())) {
                String resStr = httpUtil.getForString(weatherUrl, "city=" + indexReceiver.getWeatherMap().get(scanInDto.getContent()) + "&key=" + weatherKey);
                return MessageUtil.getRespBody(scanInDto, MessageUtil.handleWeatherJson(resStr));
            } else {
                return MessageUtil.getRespBody(scanInDto, "没有找到对应的城市，请正确输入城市名称");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
