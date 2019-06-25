package com.linkmoretech.parking.service;

import com.linkmoretech.parking.vo.request.LockOperateRequest;
import com.linkmoretech.parking.vo.request.ReqLockIntall;
import com.linkmoretech.parking.vo.response.ResGateway;
import com.linkmoretech.parking.vo.response.ResGatewayDetails;
import com.linkmoretech.parking.vo.response.ResLockGatewayList;
import com.linkmoretech.parking.vo.response.ResSignalHistory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: GFF
 * @Description: 锁操作实现类
 * @Date: 2019/6/17
 */
public interface LockOperateService {

    Boolean operate(HttpServletRequest request, LockOperateRequest lockOperate);


    /**
     * @Description  绑定网关
     * @Author   GFF
     * @Version  v2.0
     */
    Boolean bindGroup(Long preId, String serialNumber, HttpServletRequest request);

    /**
     * @Description  接触绑定
     * @Author   GFF
     * @Version  v2.0
     */
    Boolean unBindGroup(String groupCode, String serialNumber, HttpServletRequest request);

    /**
     * @Description  查询车区网关list
     * @Author   GFF
     * @Version  v2.0
     */
    List<ResGateway> findGatewayGroup(Long preId, HttpServletRequest request);

    /**
     * @Description  查询网关(扫一扫)
     * @Author   GFF
     * @Version  v2.0
     */
    ResGatewayDetails getGatewayDetails(String serialNumber, HttpServletRequest request);

    /**
     * @param serialNumber
     * @Description  加载锁
     * @Author   GFF
     * @Version  v2.0
     */
    Boolean loadLock(HttpServletRequest request, String serialNumber);

    /**
     * @Description  重启网关
     * @Author   GFF
     * @Version  v2.0
     */
    Boolean restartGateway(HttpServletRequest request, String serialNumber);

    /**
     * @Description  解除绑定锁
     * @Author   GFF
     * @Version  v2.0
     */
    Boolean unBindLock(String lockSn, String serialNumber, HttpServletRequest request);

    /**
     * @Description  删除锁
     * @Author   GFF
     * @Version  v2.0
     */
    Boolean removeLock(String serialNumber, HttpServletRequest request);



    /**
     * @Description  确认绑定
     * @Author   GFF
     * @Version  v2.0
     */
    Boolean confirm(String serialNumber, HttpServletRequest request);


    /**
     * @Description  查询车位锁在一定时间端内的信号强度变化
     * @Author   GFF
     * @Version  v2.0
     */
    ResSignalHistory lockSignalHistory(HttpServletRequest request, String sn);


    /**
     * @param preId
     * @Description  查询锁绑定的网关
     * @Author   GFF
     * @Version  v2.0
     */
    List<ResLockGatewayList> findLockGateways(HttpServletRequest request, String lockSn, Long preId);

    /**
     * @param lockSn
     * @Description  批量更新
     * @Author   GFF
     * @Version  v2.0
     */
    Boolean editLockBindGateway(HttpServletRequest request, String serialNumbers, String lockSn);

    Boolean removeStallLock(Long stallId, HttpServletRequest request);

    public Boolean installLock(ReqLockIntall reqLockIntall, HttpServletRequest request);
}
