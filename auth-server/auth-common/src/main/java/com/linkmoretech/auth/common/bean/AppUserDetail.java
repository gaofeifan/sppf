package com.linkmoretech.auth.common.bean;


import lombok.Getter;

import java.util.ArrayList;

/**
 * @Author: alec
 * Description:
 * @date: 11:04 2019-06-28
 */

public class AppUserDetail extends AccountUserDetail {

    @Getter
    private Boolean register;

    public AppUserDetail(String username, String password, Long userId, String clientId, Boolean register ) {
        super(username,password, true, new ArrayList<>(), userId, clientId);
        this.register = register;
    }
}
