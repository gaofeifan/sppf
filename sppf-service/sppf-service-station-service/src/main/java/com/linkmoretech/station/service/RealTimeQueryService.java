package com.linkmoretech.station.service;

import com.linkmoretech.station.vo.response.RealDataResponse;

/**
 * @Author: alec
 * Description: 定时查询数据
 * @date: 15:22 2019-07-15
 */
public interface RealTimeQueryService {

    /**
     * 实时查询当前已统计数量
     * */
    RealDataResponse realTime();
}
