package com.linkmoretech.account.service;

import com.linkmoretech.account.vo.request.SearchRequest;
import com.linkmoretech.account.vo.request.UserCreateRequest;
import com.linkmoretech.account.vo.request.UserEditRequest;
import com.linkmoretech.account.vo.response.LoginSuccessResponse;
import com.linkmoretech.account.vo.response.UserDetailResponse;
import com.linkmoretech.account.vo.response.UserInfoResponse;
import com.linkmoretech.account.vo.response.UserListResponse;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;

/**
 * @Author: alec
 * Description: 后台用户业务层
 * @date: 13:52 2019-05-31
 */
public interface UserService {

    /**
     * 创建帐号
     * @param userCreateRequest 创建帐号参数
     * @throws CommonException 自定义验证异常
     * */
    void createUser(UserCreateRequest userCreateRequest) throws CommonException;

    /**
     * 分页显示用户数据
     * @param pageSearchRequest 查询条件及分页参数
     * @return 返回分页数据
     * */
    PageDataResponse<UserListResponse> search(PageSearchRequest<SearchRequest> pageSearchRequest);


    /**
     * 查询帐号信息
     * */
    UserInfoResponse getUserInfo(String username, String clientId);


    /**
     * 更新用户状态
     * @param userId 用户ID
     * @param userState 用户状态
     * */
    void updateUserState(Long userId, Integer userState) throws CommonException;


    /**
     * 更新用户数据开关状态
     * @param userId 用户ID
     * @param authState 用户状态
     * */
    void updateUserAuth(Long userId, Integer authState) throws CommonException;


    /**
     * 根据用户ID 查询用户信息
     * @param userId 用户ID
     * @return 用户详情
     * */
    UserDetailResponse detail(Long userId) throws CommonException;

    /**
     * 修改用户信息
     * @param userEditRequest 修改用户参数
     * */
    void updateUserInfo(UserEditRequest userEditRequest) throws CommonException;

}
