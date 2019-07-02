package com.ty.yjx.love.dao;

import com.ty.yjx.love.model.WxUser;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/25
 */
public interface WxUserMapper {

    int insert(WxUser record);

    WxUser selectByOpenId(String openId);

    int updateByOpenId(WxUser record);

}