package com.linkmoretech.account.resposity;

import com.linkmoretech.account.entity.WeChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: alec
 * Description:
 * @date: 14:11 2019-07-11
 */
public interface WeChatUserRepository extends JpaRepository<WeChatUser, String> {

    WeChatUser getByOpenIdAndUnionId(String openId, String unionId);
}
