package com.linkmoretech.account.service;

import com.linkmoretech.account.entity.AppUser;
import com.linkmoretech.account.vo.request.AppUserRegisterRequest;
import com.linkmoretech.common.exception.CommonException;

/**
 * @Author: alec
 * Description: APP 用户服务逻辑
 * @date: 14:52 2019-06-27
 */
public interface AppUserService {


    /**
     * 用户注册
     * @param appUserRegisterRequest 用户注册参数
     * @throws CommonException 验证用户异常
     * @return 返回已注册用户
     * */
    AppUser register(AppUserRegisterRequest appUserRegisterRequest) throws CommonException;


    /**
     * 注册临时帐号
     * @param openId 微信openID
     * */

    AppUser register(String openId) throws CommonException;



    /**
     * 验证注册用户手机号
     * @param mobile 用户手机号
     * @param type 验证类型，0 需要验证用户不存在，1 验证用户存在
     * */
    boolean validateMobile(String mobile, Integer type);

    /**
     * 设置用户密码
     * @param userId 用户ID
     * @param password 用户密码
     * */
    void setPassword(Long userId, String password) throws CommonException;

    /**
     * 获取用户
     * @return 返回用户信息
     * */
    AppUser getUser(String mobile);

}
