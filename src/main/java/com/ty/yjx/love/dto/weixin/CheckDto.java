package com.ty.yjx.love.dto.weixin;

import lombok.Data;

/**
 * @Desc  微信企业网络设置 验证实体
 * 更改配置的时候 需要验证token信息
 * @Author yejx
 * @Date 2019/6/10
 */
@Data
public class CheckDto {

    private String signature;
    private String timestamp;
    private String nonce;
    private String echostr;
}
