package com.linkmoretech.auth.common.token;
import com.linkmoretech.auth.common.construct.ParamsConstruct;
import lombok.Getter;

import java.util.LinkedHashSet;

/**
 * @Author: alec
 * Description: 基于APP短信登录及注册token
 * @date: 10:47 2019-06-28
 */
@Getter
public class AppAuthenticationToken extends SmsAuthenticationToken {

    private static final long serialVersionUID = 500L;

    private Integer type;

    private Boolean register;

    public AppAuthenticationToken(Object principal, Object source) {
        super(principal, ParamsConstruct.CLIENT_APP);
        this.type =  (Integer) source;
    }

    public AppAuthenticationToken (Object principal, Long userId, Boolean register){
        super(principal, ParamsConstruct.CLIENT_APP, new LinkedHashSet<>(), userId);
        this.register = register;
    }
}
