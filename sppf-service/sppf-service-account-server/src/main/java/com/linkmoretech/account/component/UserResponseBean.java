package com.linkmoretech.account.component;

import lombok.Data;

import java.util.Set;

/**
 * @Author: alec
 * Description:
 * @date: 10:09 2019-06-19
 */
@Data
public class UserResponseBean {

    private String[] optionResources;

    private Set<Long> dataResources;
}
