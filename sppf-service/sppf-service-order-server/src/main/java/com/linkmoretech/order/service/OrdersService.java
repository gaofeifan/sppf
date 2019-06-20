package com.linkmoretech.order.service;

import java.util.List;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.order.common.response.ResOrderDetail;
import com.linkmoretech.order.vo.*;

/**
 * Service接口 - 订单
 * @author jhb
 * @Date 2019年6月20日 下午7:22:10
 * @Version 1.0
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
    OrderEditResponse cancelOrder(OrderOptionRequest orderOptionRequest) throws CommonException;

    /**
     * 挂起订单
     * @param orderOptionRequest 操作订单参数
     * @return 订单状态
     * */
    OrderEditResponse suspendOrder(OrderOptionRequest orderOptionRequest) throws CommonException;

    /**
     * 关闭订单
     * @param orderOptionRequest 操作订单参数
     * @return 订单状态
     * */
    OrderEditResponse closeOrder(OrderOptionRequest orderOptionRequest) throws CommonException;

    /**
     * 当前订单页面控制降锁
     * @param downLockRequest
     * @return
     * @throws CommonException
     */
    Boolean downLock(OrderDownLockRequest downLockRequest) throws CommonException;

    /**
     * 当前订单页面控制升锁，适用于国贸服务
     * @param downLockRequest
     * @return
     * @throws CommonException
     */
    Boolean upLock(OrderDownLockRequest downLockRequest) throws CommonException;
    
    /**
     * 根据当前登录用户ID获取当前订单信息
     * @param userId
     * @return
     */
	ResCurrentOrder current(String userId);
	/**
	 * 当前订单页面控制降锁
	 * @param ros
	 * @return
	 */
	Boolean controlDown(ReqDownLock ros);
	/**
	 * 查看订单详情
	 * @param orderId
	 * @param userId
	 * @return
	 */
	ResOrderDetail detail(String orderId, String userId);
	/**
	 * 查询已完成订单
	 * @param start
	 * @param userId
	 * @return
	 */
	List<ResCheckedOrder> list(Long start, String userId);

}
