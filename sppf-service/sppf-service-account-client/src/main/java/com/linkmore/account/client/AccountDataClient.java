package com.linkmore.account.client;

import com.linkmore.account.common.request.AccountParkRequest;
import com.linkmore.account.common.request.AccountPlaceRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: alec
 * Description: 获取用户数据操作权限接口定义
 * @date: 11:33 2019-06-25
 */
@FeignClient(name = "account")
public interface AccountDataClient {


    /**
     *
     *  返回用户可操作车场对数据权限
     *
     *  返回值为null 表示该用户可操作全部数据
     *  返回值为 empty 表示该用户无数据可操作
     * @param userId 用户ID
     * @return 可操作车场ID
     * */
    @GetMapping(value = "auth-user/getParkData")
    List<Long> getParkDataAccount(@RequestParam(value = "userId") Long userId);

    /**
     *
     *  返回用户可操作车位对数据权限
     *
     *  返回值为null 表示该用户可操作全部数据
     *  返回值为 empty 表示该用户无数据可操作
     * @param userId 用户ID
     * @param parkId 车场ID
     * @return 可操作车位ID
     * */
    @GetMapping(value = "auth-user/getPlaceData")
    List<String> getPlaceDataAccount(@RequestParam(value = "userId") Long userId, @RequestParam(value = "parkId")
                                   Long parkId);
}
