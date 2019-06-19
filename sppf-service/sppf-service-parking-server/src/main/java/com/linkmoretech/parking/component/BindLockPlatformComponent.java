package com.linkmoretech.parking.component;

import com.alibaba.fastjson.JSONObject;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.util.MdUtil;
import com.linkmoretech.common.vo.ResponseCommon;
import com.linkmoretech.http.util.HttpUtilComponent;
import com.linkmoretech.parking.entity.CarPark;
import com.linkmoretech.parking.entity.CarPlace;
import com.linkmoretech.parking.entity.Coordinate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author: alec
 * Description: 和锁平台通讯服务
 * @date: 10:38 2019-05-21
 */
@Component
@Slf4j
public class BindLockPlatformComponent {

    public static final Integer SUCCESS_CODE = 200;

    @Autowired
    private HttpUtilComponent httpUtilComponent;

    @Value(value = "${lock.appId}")
    private String appId;

    @Value(value = "${lock.secret}")
    private String secret;

    @Value(value = "${lock.url}")
    private String url;

    /**
     * 初始化参数
     * */
    private Map<String, Object> initParams() {
        /**
         * 构建参数
         * */
        Map<String, Object> params = new HashMap<>();
        params.put("appId", appId);
        params.put("timestamp", System.currentTimeMillis());
        return params;
    }

    /**
     *格式化化参数
     * */
    private String formatParams(Map<String, Object> paramsMap) {
        /**
         * 对参数进行排序并用&连接
         * */
        List<Map.Entry<String, Object>> paramsList = new ArrayList<>(paramsMap.entrySet());
        paramsList.sort(Comparator.comparing(Map.Entry::getKey));
        /**
         * 对排序后对参数拼接
         * */
        log.info("排序后的参数列表 {}", paramsList);
        StringBuilder stringBuilder = new StringBuilder();
        paramsList.forEach(params -> stringBuilder.append("&").append(params.getKey()).append("=").append(params.getValue()));
        String sign = secret + stringBuilder.toString().substring(1).toLowerCase();
        log.info("拼装后对字符串 {}", sign);
        /**
         * 对拼装后字符串加密
         * */
        sign = MdUtil.encode(sign);
        log.info("加密后对值为 {}" , sign);
        return sign;
    }

    /**
     * 绑定车场分组
     * */
    public String bindGroup(CarPark carPark, Coordinate coordinate) throws CommonException {

        Map<String, Object> paramsMap = initParams();
        paramsMap.put("groupName", carPark.getParkName());
        paramsMap.put("cityCode", carPark.getCityCode());
        paramsMap.put("cityName", carPark.getCityName());
        paramsMap.put("longitude", coordinate.getLongitude());
        paramsMap.put("latitude", coordinate.getLatitude());
        paramsMap.put("state", 1);
        String serverUrl = "/api/v1/group/save-group";
        ResponseCommon responseCommon = execute(paramsMap, serverUrl);
        return String.valueOf(responseCommon.getData());
    }

    /**
     * 获取锁状态
     * */
    public void getLockInfo(CarPlace carPlace) throws CommonException{
        Map<String, Object> paramsMap = initParams();
        paramsMap.put("serialNumber", carPlace.getLockCode());
        String serverUrl = "/api/v1/lock-info";
        ResponseCommon responseCommon = execute(paramsMap, serverUrl);
        Object lockData = responseCommon.getData();
        JSONObject jsonObject = JSONObject.parseObject(lockData.toString());
        Integer lockState = jsonObject.getInteger("lockState");
        Integer parkingState = jsonObject.getInteger("parkingState");
        Integer electricity = jsonObject.getInteger("electricity");
        carPlace.setElectric(electricity);
        carPlace.setLockStatus(lockState);
        carPlace.setPlaceStatus(parkingState);
    }


    private ResponseCommon execute(Map<String, Object> paramsMap, String serverUrl) throws CommonException{
        String sign = formatParams(paramsMap);
        paramsMap.put("sign", sign);
        JSONObject jsonObject = new JSONObject(paramsMap);
        serverUrl = url + serverUrl;
        String message = httpUtilComponent.sendHttpPostRequest(serverUrl, jsonObject);
        log.info("响应消息 {}" , message);
        if (StringUtils.isEmpty(message)) {
            log.error("调用锁平台服务出错");
            throw new CommonException(ResponseCodeEnum.FAILURE);
        }
        ResponseCommon responseCommon = JSONObject.parseObject(message, ResponseCommon.class);
        if (!responseCommon.getCode().equals(SUCCESS_CODE)) {
            log.error("锁平台未返回分组编码");
            throw new CommonException(ResponseCodeEnum.FAILURE);
        }
        return responseCommon;
    }
}
