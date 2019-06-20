package com.linkmoretech.order.service;

import com.linkmoretech.order.common.response.ResLocate;

/**
 * Service接口 - 定位
 * @author jhb
 * @Date 2019年6月20日 下午7:23:08
 * @Version 1.0
 */
public interface LocateService {

	/**
	 * 根据经纬度获取位置信息
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	ResLocate getInfo(String longitude,String latitude);

	/**
	 * 计算两点间距离
	 * @param oriLng 起点
	 * @param oriLat 起点
	 * @param desLng 起点
	 * @param desLat 起点
	 * @return
	 */
	String distance(String oriLng, String oriLat, String desLng, String desLat);

}
