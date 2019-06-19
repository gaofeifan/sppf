package com.linkmoretech.order.service;

/**
 * 订单明细服务层
 * @Author: alec
 * @Description:
 * @date: 下午5:07 2019/4/12
 */

public interface OrderDetailService {

    /**
     * 更新车位状态(通过消息队列异步执行)
     * @param orderId 订单ID
     * @param status 车位状态 0 free 1 running
     * */
    void updateParkingStatus(String orderId, Integer status);

    /**
     * 更新车锁状态(通过消息队列异步执行)
     * @param orderId 订单ID
     * @param status 车锁状态 0 parking 1 lock
     * */
    void updateLockStatus(String orderId, Integer status);

    /**
     * 更新车锁升降情况
     * 发起升降锁操作后根据锁平台返回值更新其操作结果
     * 记录当前订单升降锁的结果
     * */
    void updateLockUpOrDownStatus();

    /**
     * 更新订单车辆驶入驶出情况
     * 当有闸机的停车场接入时根据闸机动态返回的值来更新该订单所停放
     * 车辆的驶离情况
     * 无闸机停车场不调用该方法
     * */
    void updateSillInOrOutStatus();

}
