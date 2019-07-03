package com.linkmoretech.user.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 调用用户服务入参
 * @Author: alec
 * @Description:
 * @date: 上午11:21 2019/4/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoInput {

    private Long userId;

    private String userName;

    private String userMobile;

    private String openId;

    private Integer weChatBindState;

    private Date weChatBindTime;
}
