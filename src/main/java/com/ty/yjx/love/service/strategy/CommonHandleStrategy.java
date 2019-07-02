package com.ty.yjx.love.service.strategy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ty.yjx.love.config.IndexReceiver;
import com.ty.yjx.love.dto.weixin.ScanInDto;
import com.ty.yjx.love.dto.weixin.ScanOutDto;
import com.ty.yjx.love.service.MessageHandleStrategy;
import com.ty.yjx.love.util.DateUtil;
import com.ty.yjx.love.util.HttpUtil;
import com.ty.yjx.love.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/12
 */
@Slf4j
@Service
public class CommonHandleStrategy implements MessageHandleStrategy {

    @Autowired
    private IndexReceiver indexReceiver;

    @Autowired
    private HttpUtil httpUtil;

    @Value("${juhe.key.simpleWeather}")
    private String weatherKey;

    @Value("${juhe.api.simpleWeather}")
    private String weatherUrl;

    @Override
    public ScanOutDto handle(ScanInDto scanInDto) {
        if (scanInDto.getContent().contains("3")) {
            return MessageUtil.getRespBody(scanInDto, defaultWeather("1845"));
        } else if (scanInDto.getContent().contains("1") || scanInDto.getContent().contains("2")) {
            return MessageUtil.getRespBody(scanInDto, "功能正在开发中.....，你可以鼓励一下你亲爱的男朋友快速开发哟");
        } else if (scanInDto.getContent().contains("4")) {
            return MessageUtil.getRespBody(scanInDto, null);
        } else if (indexReceiver.getWeatherMap().containsKey(scanInDto.getContent())){
            return MessageUtil.getRespBody(scanInDto, defaultWeather(indexReceiver.getWeatherMap().get(scanInDto.getContent())));
        } else {
            return MessageUtil.getRespBody(scanInDto, null);
        }
    }

    private String defaultWeather(String cityId) {
        try {
            String resStr = httpUtil.getForString(weatherUrl, "city=" + cityId + "&key=" + weatherKey);
            return MessageUtil.handleWeatherJson(resStr);
        } catch (IOException e) {
            log.info("获取天气json异常：{}", e);
            return null;
        }
    }
}
