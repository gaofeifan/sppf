package com.linkmoretech.order.common.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接口凭证
 * @author liwl
 * @version 1.0
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResToken implements Serializable{
	
	private static final long serialVersionUID = -8901475040211577659L;
	// 接口访问凭证
	private String accessToken;
	// 凭证有效期，单位：秒
	private int expiresIn;

	//oauth2.0
	//刷新token
	private String refreshToken;
	private String openid;
	private String scope;

	private Integer errcode;//错误编码
	private String errmsg;//错误消息

}
