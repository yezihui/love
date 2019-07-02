package com.ty.yjx.love.controller;

import com.ty.yjx.love.cache.LoveCache;
import com.ty.yjx.love.constant.LoveConstant;
import com.ty.yjx.love.dto.weixin.CheckDto;
import com.ty.yjx.love.dto.weixin.ScanInDto;
import com.ty.yjx.love.dto.weixin.ScanOutDto;
import com.ty.yjx.love.model.WxUser;
import com.ty.yjx.love.service.MessageHandleContext;
import com.ty.yjx.love.service.WechatService;
import com.ty.yjx.love.service.WxUserService;
import com.ty.yjx.love.util.HttpUtil;
import com.ty.yjx.love.util.MessageUtil;
import com.ty.yjx.love.util.XmlUtils;
import com.ty.yjx.love.util.aes.WXBizMsgCrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/10
 */
@Slf4j
@RestController
@RequestMapping("/wechat")
public class WechatController {

    @Autowired
    private LoveCache loveCache;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private WechatService wechatService;

    @Autowired
    private MessageHandleContext messageHandleContext;

    @Value("${wechat.server.token}")
    private String wechatServerToken;

    @Value("${wechat.server.encodingAesKey}")
    private String wechatServerEncodingAesKey;

    @Value("${wechat.account.id}")
    private String wechatServiceAppID;

    /**
     * 微信验签接口
     *
     * @param reqBody 请求体
     * @return 验签成功返回具体信息 验签失败返回空串
     */
    @GetMapping("/scan")
    public String scan(CheckDto reqBody) {
        try {
            log.info("加密结果 {}", reqBody.getSignature());
            WXBizMsgCrypt pc = new WXBizMsgCrypt(wechatServerToken, wechatServerEncodingAesKey, wechatServiceAppID);
            if (pc.checkSignature(reqBody.getSignature(), reqBody.getTimestamp(), reqBody.getNonce())) {
                return reqBody.getEchostr();
            } else {
                return "";
            }
        } catch (Exception e) {
            log.info("校验异常 e:" + e);
            return "";
        }
    }

    /**
     * 接收图文信息
     *
     * @param request 微信响应信息
     * @return 返回用户信息
     */
    @PostMapping("/scan")
    @ResponseBody
    public String scan(HttpServletRequest request) {
        try {
            try (InputStream is = request.getInputStream(); InputStreamReader isr = new InputStreamReader(is, LoveConstant.ENCODE); BufferedReader br = new BufferedReader(isr)) {
                String str;
                StringBuilder returnXml = new StringBuilder();
                while ((str = br.readLine()) != null) {
                    returnXml.append(str);
                }

                WXBizMsgCrypt pc = new WXBizMsgCrypt(wechatServerToken, wechatServerEncodingAesKey, wechatServiceAppID);
                String msgSignature = request.getParameter(LoveConstant.MSG_SIGNATURE);
                String timestamp = request.getParameter(LoveConstant.TIMESTAMP);
                String nonce = request.getParameter(LoveConstant.NONCE);
                ScanInDto enReqBody = XmlUtils.getObjectForXml(returnXml.toString(), ScanInDto.class);
                String deStr = pc.DecryptMsg(msgSignature, timestamp, nonce, enReqBody.getEncrypt());

                ScanInDto reqBody = XmlUtils.getObjectForXml(deStr, ScanInDto.class);
                String openid = reqBody.getFromUserName();
                System.out.println(reqBody);
                if (loveCache.isNotExist(openid)) {
                    WxUser wxUser = wechatService.getWxUserInfo(openid);
                    wxUserService.insert(wxUser);
                }
                if (LoveConstant.SUBSCRIBE.equals(reqBody.getEvent())) {
                    log.info("用户 openid: {} 关注公众号", openid);
                    return pc.EncryptMsg(XmlUtils.getXmlForObject(MessageUtil.getRespBody(reqBody, null)), timestamp, nonce);
                } else if (LoveConstant.UNSUBSCRIBE.equals(reqBody.getEvent())) {
                    log.info("用户 openid: {} 取消关注公众号", openid);
                } else if (LoveConstant.TEXT.equals(reqBody.getMsgType()) || LoveConstant.IMAGE.equals(reqBody.getMsgType())) {
                    return pc.EncryptMsg(XmlUtils.getXmlForObject(messageHandleContext.handle(reqBody)), timestamp, nonce);
                }
            }
        } catch (Exception e) {
            log.error("处理微信公众号请求信息，失败", e);
        }
        return null;
    }


}
