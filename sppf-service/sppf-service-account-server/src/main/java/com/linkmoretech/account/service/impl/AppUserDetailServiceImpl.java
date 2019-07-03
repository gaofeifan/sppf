package com.linkmoretech.account.service.impl;

import com.linkmoretech.account.component.AppUserComponent;
import com.linkmoretech.account.entity.AppUser;
import com.linkmoretech.account.service.AppUserService;
import com.linkmoretech.account.vo.request.AppUserRegisterRequest;
import com.linkmoretech.auth.common.bean.AppUserDetail;
import com.linkmoretech.auth.common.exception.RegisterException;
import com.linkmoretech.auth.common.service.AppUserDetailAbstract;
import com.linkmoretech.common.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @Author: alec
 * Description:
 * @date: 11:14 2019-06-28
 */
@Component
@Slf4j
public class AppUserDetailServiceImpl extends AppUserDetailAbstract {

    @Autowired
    AppUserService appUserService;

    @Autowired
    AppUserComponent appUserComponent;

   /* @Override
    public UserDetails register(String mobile, Integer type) throws RegisterException {

    }
*/
   /* @Override
    public UserDetails login(String mobile, Integer type) throws RegisterException {

    }*/

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
