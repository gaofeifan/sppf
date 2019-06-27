package com.linkmoretech.account.resposity;

import com.linkmoretech.account.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 13:51 2019-05-31
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 查询帐号或手机号存在对用户记录
     * @param userName 帐号
     * @param clientId 客户端ID
     * @return 用户记录
     * */
    User getUserByClientIdAndUserName(String clientId, String userName);

    /**
     * 查询帐号或手机号存在对用户记录
     * @param clientId 客户端ID
     * @param mobile 手机号
     * @return 用户记录
     * */
    User getUserByClientIdAndMobile(String clientId, String mobile);

}
