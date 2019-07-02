package com.ty.yjx.love.service;

import com.ty.yjx.love.dao.WxUserMapper;
import com.ty.yjx.love.model.WxUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/25
 */
@Service
public class WxUserService{

    @Autowired
    private WxUserMapper wxUserMapper;

    public WxUser selectByOpenId(String openId) {
        return wxUserMapper.selectByOpenId(openId);
    }

    public int insert(WxUser wxUser) {
        return wxUserMapper.insert(wxUser);
    }

    public int update(WxUser wxUser) {
        return wxUserMapper.updateByOpenId(wxUser);
    }
}
