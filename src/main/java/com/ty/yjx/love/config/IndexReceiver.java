package com.ty.yjx.love.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ty.yjx.love.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/26
 */
@Slf4j
@Component
public class IndexReceiver {

    /**
     * 获取内存的天气城市列表map
     *
     * @return 天气城市列表
     */
    public Map<String, String> getWeatherMap() {
        IndexSingleton index = IndexSingleton.getInstance();
        Map<String, String> map = index.getWeatherMap();
        if (map == null || map.size() <= 0) {
            log.info("天气map为空 重新读取配置文件");
            index.setWeatherMap(loadWeatherMap());
            return index.getWeatherMap();
        }
        return map;
    }

    /**
     * 加载本地天气json 为map对象
     * @return map对象
     */
    private Map<String, String> loadWeatherMap() {
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            String fileName = this.getClass().getClassLoader().getResource("weatherJson.txt").getPath();
            InputStreamReader reader = new InputStreamReader(new FileInputStream(new File(fileName)));
            BufferedReader br = new BufferedReader(reader);
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            Gson gson = new GsonBuilder().setDateFormat(DateUtil.YYYY_MM_DD).create();
            Map<String, Object> map = gson.fromJson(sb.toString(), Map.class);
            List<Map<String, Object>> result = (List<Map<String, Object>>) map.get("result");
            Map<String, String> resultMap = new HashMap<>(result.size());
            for (Map<String, Object> city : result) {
                resultMap.put(city.get("district").toString(), city.get("id").toString());
            }
            return resultMap;
        } catch (Exception e) {
            log.info("加载本地天气json异常：{}", e);
            return null;
        }
    }
}
