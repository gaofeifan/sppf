package com.linkmoretech.order.service;

import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.order.vo.*;

/**
 * @Author: alec
 * @Description:
 * @date: 下午5:03 2019/4/12
 */
public interface OrdersService {

    /**
     * 创建订单
     * @param orderRequest 订单参数
     * @return 订单号
     * */
    OrderEditResponse createOrder(OrderRequest orderRequest) throws CommonException;

    /**
     * 查询订单列表
     * @param pageSearchRequest 分页查询条件
     * @return 分页查询结果
     * */
    PageDataResponse<OrderListResponse> searchList(PageSearchRequest pageSearchRequest);

    /**
     * 查询订单信息
     * @param orderId 订单ID
     * @param userId 用户ID
     * @return 订单详情
     * */
    OrderDetailResponse findDetailByOrderId(String orderId, String userId);

    /**
     * 取消订单
     * @param orderOptionRequest 操作订单参数
     * @return 订单状态
     * */
    OrderEditResponse cancelOrder(OrderOptionRequest orderOptionRequest);

    /**
     * 挂起订单
     * @param orderOptionRequest 操作订单参数
     * @return 订单状态
     * */
    OrderOptionRequest suspendOrder(OrderOptionRequest orderOptionRequest);

    /**
     * 关闭订单
     * @param orderOptionRequest 操作订单参数
     * @return 订单状态
     * */
    OrderEditResponse closeOrder(OrderOptionRequest orderOptionRequest);


    /**
     * 支付订单(同步完成支付,即不需要调用第三方支付回调)
     * */


}
