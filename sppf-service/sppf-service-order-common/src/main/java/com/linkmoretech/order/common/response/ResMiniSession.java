package com.linkmoretech.order.common.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResMiniSession implements Serializable {
	private static final long serialVersionUID = 8180867875988641112L;
	private String session_key;
	private String openid;
	private String unionid;

}