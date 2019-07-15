package com.linkmoretech.account.service;

import com.linkmoretech.common.exception.CommonException;

/**
 * @Author: alec
 * Description: 帐号业务
 * @date: 20:26 2019-06-19
 */
public interface AccountService {

    /**
     * 修改密码
     * @param clientId 客户端ID
     * @param username 帐号
     * @param password 密码
     * */
    void updatePassword(String clientId, String username, String password) throws CommonException;

    /**
     * 修改密码
     * @param clientId 客户端ID
     * @param username 帐号
     * @param password 密码
     * @param oldPassword 原密码
     * */
    void updatePassword(String clientId, String username, String password, String oldPassword) throws CommonException;


    /**
     * 修改用户手机号
     * @param userId 用户ID
     * @param mobile 手机号
     * */
    void registerMobile(Long userId, String mobile) throws CommonException;

}
