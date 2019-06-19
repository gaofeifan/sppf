package com.linkmoretech.order.common.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求 - 定位请求
 * @author jhb
 * @version 2.0
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqLocate {
 
	/**
	 * 经度
	 */
	private String longitude;
	 
	/**
	 * 纬度
	 */
	private String latitude;

}
