package com.linkmoretech.user.service;

import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.user.common.vo.UserInfoInput;
import com.linkmoretech.user.vo.UserEditRequest;
import com.linkmoretech.user.vo.UserInfoResponse;
import com.linkmoretech.user.vo.UserListResponse;

/**
 * 用户业务层
 * @Author: alec
 * @Description:
 * @date: 下午3:23 2019/4/10
 */
public interface UserInfoService {

    /**
     * 根据用户ID查询用户详情
     * @param userId 用户ID
     * @return 用户详情数据
     * */
    UserInfoResponse findDetailByUserId(String userId);

    /**
     * 用户列表
     * @param pageSearchRequest 查询条件及分页条件
     * @return 返回条数及数据
     * */
    PageDataResponse<UserListResponse> searchList(PageSearchRequest pageSearchRequest);

    /**
     * 编辑用户
     * @param userEditRequest 编辑用户数据
     * */
    void editUser(UserEditRequest userEditRequest) throws CommonException;

    /**
     * 启用、禁用 用户状态
     * @param userId 用户ID
     * @param username 操作账号
     * @param userState 用户状态
     * @throws CommonException 自定义异常
     * */
    void updateUserState(String userId, String username, Integer userState) throws CommonException;

    /**
     * 变更手机号
     * @param userInfoInput 修改用户参数 userId 和 手机号
     * */
    void updateUserMobile(UserInfoInput userInfoInput) throws CommonException;

    /**
     * 绑定微信号
     * @param userInfoInput 绑定微信号参数
     * */
    void bindWeChat(String code, String userId) throws CommonException;

    /**
     * 解绑微信
     * @param userId 用户Id
     * */
    void unbindWeChat(String userId) throws CommonException;

    /**
     * 创建用户(由账户服务调用)
     * @param userInfoInput 创建用户参数
     * @return 返回用户ID
     * */
    String createUser(UserInfoInput userInfoInput) throws CommonException;

}
