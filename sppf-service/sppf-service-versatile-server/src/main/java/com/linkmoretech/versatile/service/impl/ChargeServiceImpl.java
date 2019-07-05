package com.linkmoretech.versatile.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.linkmoretech.common.vo.ResponseCommon;
import com.linkmoretech.http.util.HttpUtilComponent;
import com.linkmoretech.versatile.service.ChargeService;
import com.linkmoretech.versatile.vo.response.ChargeListResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 17:41 2019-05-20
 */
@Service
@Slf4j
public class ChargeServiceImpl implements ChargeService {
    @Value("${charge.url}")
    private String url;
    @Value("${charge.sign}")
    private String sign;
    @Value("${charge.code}")
    private String code;

    @Autowired
    HttpUtilComponent httpUtilComponent;

    @Override
    public List<ChargeListResponse> list() {

        JSONObject params = new JSONObject();
        params.put("sign", sign);
        params.put("code", code);
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));

        String message = httpUtilComponent.sendHttpPostRequest(url, params);
        log.info("响应消息{}", message);
        if (StringUtils.isEmpty(message)) {
            return new ArrayList<>();
        }
        ResponseCommon<List<ChargeListResponse>> chargeList  = JSONObject.parseObject(message, ResponseCommon.class);
        return chargeList.getData();
    }
}
