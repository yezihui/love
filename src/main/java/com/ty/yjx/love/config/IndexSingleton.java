package com.ty.yjx.love.config;
import lombok.Data;

import java.util.Map;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/26
 */
@Data
public class IndexSingleton {

    private Map<String, String> weatherMap;

    private static IndexSingleton instance = new IndexSingleton();

    private IndexSingleton() {
    }

    public static IndexSingleton getInstance() {
        return instance;
    }
}
