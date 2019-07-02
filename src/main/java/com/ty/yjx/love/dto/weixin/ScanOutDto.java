package com.ty.yjx.love.dto.weixin;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * @Desc 收到消息提醒 给出的返回实体
 * @Author yejx
 * @Date 2019/6/10
 */
@Data
@JacksonXmlRootElement(localName = "xml")
public class ScanOutDto {

    private String ToUserName;
    private String FromUserName;
    private Long CreateTime;
    private String MsgType;
    private String Content;
}
