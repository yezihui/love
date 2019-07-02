package com.ty.yjx.love.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ty.yjx.love.dto.weixin.ScanInDto;
import com.ty.yjx.love.dto.weixin.ScanOutDto;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/25
 */
public class MessageUtil {

    /**
     * 微信事件 返回信息
     *
     * @param reqBody 返回信息
     * @param message 提示信息
     * @return 返回对象
     */
    public static ScanOutDto getRespBody(ScanInDto reqBody, String message) {
        System.out.println(reqBody);
        System.out.println(message);
        if (message == null || message.isEmpty()) {
            message = "····欢迎雅哈哈····\n" +
                    "请通过以下数字选择功能：\n" +
                    "1、回忆相册 \n" +
                    "2、愿望清单 \n" +
                    "3、天气查询 \n" +
                    "4、性格测试 \n";
        }
        ScanOutDto respBody = new ScanOutDto();
        respBody.setContent(message);
        respBody.setMsgType("text");
        respBody.setCreateTime(System.currentTimeMillis());
        respBody.setFromUserName(reqBody.getToUserName());
        respBody.setToUserName(reqBody.getFromUserName());
        return respBody;
    }

    public static String handleWeatherJson(String retStr) {
        Gson gson = new GsonBuilder().setDateFormat(DateUtil.YYYY_MM_DD).create();
        Map<String, Object> map = gson.fromJson(retStr, Map.class);
        StringBuilder sb = new StringBuilder();
        if ((double) map.get("error_code") >= 0D) {
            Map<String, Object> result = (Map<String, Object>) map.get("result");
            Map<String, Object> realtime = (Map<String, Object>) result.get("realtime");
            List<Map<String, Object>> future = (List<Map<String, Object>>) result.get("future");
            sb.append("城市：").append(result.get("city")).append("\n");
            sb.append("当前温度：").append(realtime.get("temperature")).append("℃\n");
            sb.append("当前湿度：").append(realtime.get("humidity")).append("\n");
            sb.append("当前风向：").append(realtime.get("direct")).append("\n");
            sb.append("当前风力：").append(realtime.get("power")).append("\n");
            sb.append("空气质量：").append(realtime.get("aqi")).append("\n");
            sb.append("·····今后五天天气·····\n");
            for (Map<String, Object> weather : future) {
                sb.append("日期：").append(weather.get("date")).append("\t").append("温度:").append(weather.get("temperature")).append("\n");
                sb.append("天气：").append(weather.get("weather")).append("\t").append("风向:").append(weather.get("direct")).append("\n");
            }
            sb.append("回复城市名称，可以查询其他城市天气\n");
        }
        return sb.toString();
    }
}
