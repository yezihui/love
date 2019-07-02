package com.ty.yjx.love.cache;

import com.ty.yjx.love.config.RedisService;
import com.ty.yjx.love.model.WxUser;
import com.ty.yjx.love.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/11
 */
@Component
public class LoveCache {

    @Autowired
    private RedisService redisService;

    @Autowired
    private WxUserService wxUserService;

    /**
     * 用户选择
     */
    private static final String LOVE_USER_ICHANGE = "love:user:change:";

    /**
     * 用户信息
     */
    private static final String LOVE_USER_INFO = "love:user:info:";

    /**
     * 默认超时时间（分钟）2天
     */
    private static final int DEFAULT_MINUTES_TIME_OUT = 60 * 24 * 2;

    public boolean isNotExist(final String openId) {
        String userInfoKey = LOVE_USER_INFO + openId;
        Object ret = redisService.getHashValue(userInfoKey, "exist");
        if (ret == null) {
            WxUser wxUser = wxUserService.selectByOpenId(openId);
            if (wxUser == null) {
                return true;
            }
            setExist(openId);
            return false;
        }
        return false;
    }

    private void setExist(final String openId) {
        String userInfoKey = LOVE_USER_INFO + openId;
        redisService.setValue(userInfoKey, "1");
    }

    /**
     * 设置用户选择的功能 默认5分钟后失效
     *
     * @param userId 用户id
     * @param change 所选功能
     */
    public void setUserChange(final String userId, final String change) {
        String changeKey = LOVE_USER_ICHANGE + userId;
        redisService.setValue(changeKey, change, DEFAULT_MINUTES_TIME_OUT, TimeUnit.MINUTES);
    }

    /**
     * 获取用户所选功能
     *
     * @param userId 用户id
     * @return 所选功能
     */
    public String getUserChange(final String userId) {
        String changeKey = LOVE_USER_ICHANGE + userId;
        return (String) redisService.getValue(changeKey);
    }

}
