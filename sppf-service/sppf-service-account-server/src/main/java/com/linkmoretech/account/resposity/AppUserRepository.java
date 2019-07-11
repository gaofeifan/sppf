package com.linkmoretech.account.resposity;

import com.linkmoretech.account.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: alec
 * Description:
 * @date: 14:51 2019-06-27
 */
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    /**
     * 根据用户手机号查询用户是否存在
     * @param mobile 用户手机号
     * @return 反映用户帐号信息
     * */
    AppUser getByMobile(String mobile);


    /**
     * @param openId 用户OPEN ID
     * @param unionId 用户UUID
     * */
    AppUser getByOpenIdAndUnionId(String openId, String unionId);
}
