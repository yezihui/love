package com.ty.yjx.love.util;

import org.apache.http.HttpStatus;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Desc http请求工具类
 * @Author yejx
 * @Date 2019/6/10
 */
@Component
public class HttpUtil {

    @Autowired
    private ResponseHandler<String> stringBodyRspHandler;
    @Autowired
    private ResponseHandler<byte[]> byteBodyRspHandler;

    /**
     * 发出post请求
     *
     * @param url     url
     * @param reqBody 参数json
     * @return 返回结果
     * @throws Exception 异常
     */
    public String postForString(String url, String reqBody) throws Exception {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            return httpclient.execute(getHttpPost(url, reqBody), stringBodyRspHandler);
        }
    }

    /**
     * 发出post请求
     *
     * @param url     url
     * @param reqBody 参数json
     * @return 返回结果
     * @throws Exception 异常
     */
    public byte[] bytePostForString(String url, String reqBody) throws Exception {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            return httpclient.execute(getHttpPost(url, reqBody), byteBodyRspHandler);
        }
    }

    private HttpPost getHttpPost(String url, String reqBody) {
        HttpPost httpPost = new HttpPost(url);
        StringEntity sentity = new StringEntity(reqBody, "utf-8");
        sentity.setContentEncoding("utf-8");
        sentity.setContentType("application/json;charset=utf-8");
        httpPost.setEntity(sentity);
        return httpPost;
    }

    /**
     * 发出get请求
     *
     * @param url   请求url
     * @param param url参数
     * @return 请求结果
     * @throws IOException 异常
     */
    public String getForString(String url, String param) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpGet;
            if (param == null) {
                httpGet = new HttpGet(url);
            } else {
                httpGet = new HttpGet(url + "?" + param);
            }
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                /**请求发送成功，并得到响应**/
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    /**读取服务器返回过来的json字符串数据**/
                    return EntityUtils.toString(response.getEntity());
                }
            }
        }
        return null;
    }
}
