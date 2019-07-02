package com.ty.yjx.love.dto.weixin;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/10
 */
@Data
@JacksonXmlRootElement(localName = "xml")
public class ScanInDto {

    private String URL;
    private String ToUserName;
    private String FromUserName;
    private Long CreateTime;
    private String MsgType;
    private String Event;
    private String Latitude;
    private String Longitude;
    private String Precision;
    private String MsgId;
    private String EventKey;
    private String Ticket;
    private String Encrypt;
    private String PicUrl;
    private String Content;
    private String MenuId;
    private String MsgID;
    private String Status;
    private String MediaId;
}
