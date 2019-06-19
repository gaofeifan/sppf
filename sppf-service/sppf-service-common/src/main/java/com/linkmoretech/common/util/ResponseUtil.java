package com.linkmoretech.common.util;

import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.ResponseCommon;

import java.util.HashMap;

/**
 * 响应工具类
 * @Author: alec
 * @Description:
 * @date: 上午9:59 2019/4/10
 */
public class ResponseUtil {


    /**
     * 响应成功数据返回格式无数据
     * */
    @SuppressWarnings("unchecked")
    public static ResponseCommon returnSuccess() {
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setCode(ResponseCodeEnum.SUCCESS.getCode());
        responseCommon.setMessage(ResponseCodeEnum.SUCCESS.getMessage());
        responseCommon.setData(new HashMap());
        return responseCommon;
    }

    /**
     * 响应成功数据返回格式有数据
     * */
    @SuppressWarnings("unchecked")
    public static ResponseCommon returnSuccess(Object object) {
        ResponseCommon responseCommon = returnSuccess();
        responseCommon.setData(object);
        return responseCommon;
    }

    /**
     * 根据错误码返回相应信息无数据
     * */
    @SuppressWarnings("unchecked")
    public static ResponseCommon returnFailure(ResponseCodeEnum codeEnum) {
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setCode(codeEnum.getCode());
        responseCommon.setMessage(codeEnum.getMessage());
        responseCommon.setData(new HashMap());
        return responseCommon;
    }

    /**
     * 根据错误码返回相应信息无数据
     * */
    @SuppressWarnings("unchecked")
    public static ResponseCommon returnFailure(CommonException commonException) {
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setCode(commonException.getCodeEnum().getCode());
        responseCommon.setMessage(commonException.getMessage());
        responseCommon.setData(new HashMap());
        return responseCommon;
    }

    /**
     * 根据错误码返回相应信息有数据
     * */
    @SuppressWarnings("unchecked")
    public static ResponseCommon returnFailure(ResponseCodeEnum codeEnum, Object data) {
       ResponseCommon responseCommon = returnFailure(codeEnum);
       responseCommon.setData(data);
       return responseCommon;
    }
}
