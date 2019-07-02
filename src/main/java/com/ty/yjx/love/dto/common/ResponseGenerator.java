package com.ty.yjx.love.dto.common;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/11
 */
public class ResponseGenerator {

    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";
    private static final String SUCCESS_MSG = "ok";

    public static BaseResponse genSuccessResult() {
        BaseResponse result = new BaseResponse();
        result.setResult(SUCCESS);
        result.setRetMsg(SUCCESS_MSG);
        return result;
    }

    public static BaseResponse genSuccessResult(Object data) {
        BaseResponse result = new BaseResponse();
        result.setResult(SUCCESS);
        result.setRetMsg(SUCCESS_MSG);
        result.setRetData(data);
        return result;
    }

    public static BaseResponse genFailResult(String message) {
        BaseResponse result = new BaseResponse();
        result.setResult(FAIL);
        result.setRetMsg(message);
        return result;
    }
}
