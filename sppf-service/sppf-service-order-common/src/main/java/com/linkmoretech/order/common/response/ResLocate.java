package com.linkmoretech.order.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应-定位信息
 * 
 * @author liwenlong
 * @version 2.0
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResLocate {
	/**
	 * 国家
	 */
	private String nation;
	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 区，可能为空字串
	 */
	private String district;
	/**
	 * 街道
	 */
	private String street;
	/**
	 * 行政区划代码
	 */
	private String adcode;
	/**
	 * 行政区划名称
	 */
	private String name;
	/**
	 *  纬度
	 */
	private Double locationLat;
	
	/**
	 *  纬度
	 */
	private Double locationLng;

}
