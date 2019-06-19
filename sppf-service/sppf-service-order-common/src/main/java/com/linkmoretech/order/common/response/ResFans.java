package com.linkmoretech.order.common.response;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 响应 - 微信用户
 * @author liwenlong
 * @version 2.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResFans {
	/**
	 * openId
	 */
    private String id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String headurl;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 状态
     */
    private Short status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 注册状态
     */
    private Short registerStatus;

    /**
     * unionid
     */
    private String unionid;
}
