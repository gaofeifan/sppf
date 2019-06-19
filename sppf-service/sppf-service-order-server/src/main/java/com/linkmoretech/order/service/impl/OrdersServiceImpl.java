package com.linkmoretech.order.service.impl;

import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.order.config.GeneratedIdComponent;
import com.linkmoretech.order.entity.OrderDetail;
import com.linkmoretech.order.entity.Orders;
import com.linkmoretech.order.enums.OrderStatusEnum;
import com.linkmoretech.order.enums.OrderTypeEnum;
import com.linkmoretech.order.resposity.OrderDetailRepository;
import com.linkmoretech.order.resposity.OrdersRepository;
import com.linkmoretech.order.service.OrdersService;
import com.linkmoretech.order.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 订单业务逻辑层
 * @Author: alec
 * @Description:
 * @date: 上午9:20 2019/4/15
 */
@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    GeneratedIdComponent generatedIdComponent;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    OrdersRepository ordersRepository;

    @Override
    @Transactional
    public OrderEditResponse createOrder(OrderRequest orderRequest) throws CommonException {
        /**
         * 创建订单逻辑
         * 1. 检查该用户是否有未结算订单
         * 2. 调用用户服务获取用户信息
         * 3. 调用车区服务获取车区/车位信息(如果该车位被占用则 返回失败)
         * 4. 生成订单数据
         * 5. 生成订单明细数据
         * */

        /**
         * 根据用户ID查询该用户是否有未结算订单
         * */
        Orders noFinishOrder = ordersRepository.findOrdersByUserIdAndStatusIn(orderRequest.getUserId(), OrderStatusEnum.BOOKED.getCode(),
                OrderStatusEnum.SUSPENDED.getCode());
        if (noFinishOrder != null) {
            throw new CommonException(ResponseCodeEnum.ERROR, "有未结算订单");
        }
        /**
         * 调用车区服务获取并锁定车位（由车区服务提供）
         * 逻辑 首先车区ID必填永远锁定车区。车位ID选填，如果不填默认随机一个可用车位
         * 但车区服务返回车位信息后需要对该车位进行锁定，锁定时间1分钟，一分钟后该车位未产生订单将释放
         *
         * */
        /**
         * 调用用户服务获取用户信息
         * */
        Orders orders = new Orders();
        BeanUtils.copyProperties(orderRequest, orders);
        orders.setId(generatedIdComponent.getOrderId(orderRequest.getUserId(), orderRequest.getOrderType()));
        orders.setStatus(OrderStatusEnum.BOOKED.getCode());
        ordersRepository.save(orders);
        OrderDetail orderDetail = new OrderDetail();
        BeanUtils.copyProperties(orderRequest, orderDetail);
        orderDetail.setOrderId(orders.getId());
        orderDetail.setId(generatedIdComponent.getOrderDetailId(orderRequest.getUserId(), orderRequest.getOrderType()));
        orderDetailRepository.save(orderDetail);

        OrderEditResponse orderEditResponse = new OrderEditResponse();
        orderEditResponse.setOrderId(orders.getId());
        orderEditResponse.setOrderStatus(OrderStatusEnum.BOOKED.getCode());
        orderEditResponse.setOrderType(orderRequest.getOrderType());
        return orderEditResponse;
    }

    @Override
    public PageDataResponse<OrderListResponse> searchList(PageSearchRequest pageSearchRequest) {

        return null;
    }

    @Override
    public OrderDetailResponse findDetailByOrderId(String orderId, String userId) {
        return null;
    }

    @Override
    public OrderEditResponse cancelOrder(OrderOptionRequest orderOptionRequest) {
        return null;
    }

    @Override
    public OrderOptionRequest suspendOrder(OrderOptionRequest orderOptionRequest) {
        return null;
    }

    @Override
    public OrderEditResponse closeOrder(OrderOptionRequest orderOptionRequest) {
        return null;
    }
}
