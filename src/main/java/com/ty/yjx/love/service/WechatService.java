package com.ty.yjx.love.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ty.yjx.love.config.RedisConfig;
import com.ty.yjx.love.config.RedisService;
import com.ty.yjx.love.dto.AccessTokenResultDto;
import com.ty.yjx.love.model.WxUser;
import com.ty.yjx.love.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/10
 */
@Service
@Slf4j
public class WechatService {

    @Autowired
    private HttpUtil httpUtil;

    @Autowired
    private RedisService redisService;

    @Value("${wechat.api.accessToken}")
    private String accessTokenUrl;

    @Value("${wechat.account.id}")
    private String appId;

    @Value("${wechat.account.appSecret}")
    private String appSecret;

    @Value("${wechat.api.userInfo}")
    private String userInfoUrl;

    private static final String ACCESS_TOKEN_REDIS_KEY = "love:service-access-token";

    /**
     * 从redis获取accessToken
     *
     * @return accessToken
     */
    public AccessTokenResultDto getAccessTokenFormRedis() {
        AccessTokenResultDto resultData = new AccessTokenResultDto();
        resultData.setAccessToken(redisService.getStrHashValue(ACCESS_TOKEN_REDIS_KEY, "access_token"));
        resultData.setExpireIn(redisService.getIntHashValue(ACCESS_TOKEN_REDIS_KEY, "expires_in"));
        resultData.setTimestamp(redisService.getLongHashValue(ACCESS_TOKEN_REDIS_KEY, "timestamp"));
        return resultData;
    }

    /**
     * 更新微信 AccessToken
     *
     * @throws IOException io异常
     */
    public void updateAccessToken() throws IOException {
        String resStr = httpUtil.getForString(accessTokenUrl, "grant_type=client_credential&appid=" + appId + "&secret=" + appSecret);
        log.info("微信token返回结果  {}", resStr);
        JsonObject jsonObject = new JsonParser().parse(resStr).getAsJsonObject();
        String accessToken = jsonObject.get("access_token").getAsString();
        String expiresIn = jsonObject.get("expires_in").getAsString();
        redisService.setHValue(ACCESS_TOKEN_REDIS_KEY, "access_token", accessToken);
        redisService.setHValue(ACCESS_TOKEN_REDIS_KEY, "expires_in", expiresIn);
        redisService.setHValue(ACCESS_TOKEN_REDIS_KEY, "timestamp", String.valueOf(new java.util.Date().getTime()));
        log.info(redisService.getStrHashValue(ACCESS_TOKEN_REDIS_KEY, "access_token"));
        log.info(redisService.getStrHashValue(ACCESS_TOKEN_REDIS_KEY, "expires_in"));
        log.info(redisService.getStrHashValue(ACCESS_TOKEN_REDIS_KEY, "timestamp"));
    }

    /**
     * 通过openId 去获取微信用户信息
     *
     * @param openId openId
     * @return WxUser异常
     * @throws IOException IO一次
     */
    public WxUser getWxUserInfo(String openId) throws IOException {
        AccessTokenResultDto resultDto = getAccessTokenFormRedis();

        String resStr = httpUtil.getForString(userInfoUrl, "?access_token=" + resultDto.getAccessToken() + "&openid=" + openId + "&lang=zh_CN");
        log.info("微信userInfo返回结果：{}", resStr);
        JsonObject jsonObject = new JsonParser().parse(resStr).getAsJsonObject();

        WxUser wxUser = new WxUser();
        wxUser.setOpenId(openId);
        if (jsonObject.get("headimgurl") != null) {
            wxUser.setHeadImg(jsonObject.get("headimgurl").getAsString());
        }
        if (jsonObject.get("nickname") != null) {
            wxUser.setNickName(jsonObject.get("nickname").getAsString());
        }
        wxUser.setCreateTime(new Date());
        return wxUser;
    }
}
