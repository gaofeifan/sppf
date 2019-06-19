package com.linkmoretech.order.service.impl;

import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.enums.StatusEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.order.config.GeneratedIdComponent;
import com.linkmoretech.order.entity.OrderDetail;
import com.linkmoretech.order.entity.Orders;
import com.linkmoretech.order.enums.OperateStatusEnum;
import com.linkmoretech.order.enums.OrderStatusEnum;
import com.linkmoretech.order.resposity.OrderDetailRepository;
import com.linkmoretech.order.resposity.OrdersRepository;
import com.linkmoretech.order.service.OrdersService;
import com.linkmoretech.order.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Date;

/**
 * 订单业务逻辑层
 * @Author: alec
 * @Description:
 * @date: 上午9:20 2019/4/15
 */
@Service
@Slf4j
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
            throw new CommonException(ResponseCodeEnum.ERROR, "您有未支付的订单，请先结账");
        }

        /**
         * 调用用户服务的用户车牌信息，根据车牌id获取车牌，查询当前车牌是否在预约中
         * 若当前车牌在预约中，则提示"当前车牌号已在预约中，请更换车牌号重新预约"
         */

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
        orders.setCreateTime(new Date());
        orders.setUpdateTime(new Date());

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
        OrderDetailResponse orderDetailResponse = null;
        Orders order = ordersRepository.getOne(orderId);
        OrderDetail orderDetail = orderDetailRepository.findOrderDetailByOrderId(orderId);
        if(order !=  null && orderDetail != null){

            orderDetailResponse = new OrderDetailResponse();
            //订单信息
            orderDetailResponse.setCreateTime(order.getCreateTime());
            orderDetailResponse.setOrderId(order.getId());
            orderDetailResponse.setOrderType(order.getOrderType());

            orderDetailResponse.setTotalAmount(order.getTotalAmount());

            orderDetailResponse.setPayAmount(order.getPayAmount());
            orderDetailResponse.setPayType(order.getPayType());
            orderDetailResponse.setPayTime(order.getPayTime());

            orderDetailResponse.setReductionAmount(order.getReductionAmount());
            orderDetailResponse.setReductionType(order.getReductionType());
            //TODO 此处需要根据结束开始时间计算
            orderDetailResponse.setDuration(1L);
            //订单详情信息
            orderDetailResponse.setDownLockTime(orderDetail.getDownLockTime());
            orderDetailResponse.setUpLockTime(orderDetail.getUpLockTime());
            orderDetailResponse.setParkName(orderDetail.getParkName());
            orderDetailResponse.setPlaceName(orderDetail.getPlaceName());
            orderDetailResponse.setPlaceStatus(orderDetail.getPlaceStatus());
            orderDetailResponse.setPlateNo(orderDetail.getPlateNo());
            orderDetailResponse.setMobile(orderDetail.getMobile());
            orderDetailResponse.setSailInTime(orderDetail.getSailInTime());
            orderDetailResponse.setSailOutTime(orderDetail.getSailOutTime());

        }

        return orderDetailResponse;
    }

    @Override
    public OrderEditResponse cancelOrder(OrderOptionRequest orderOptionRequest) throws CommonException {
        Orders order = ordersRepository.getOne(orderOptionRequest.getOrderId());
        if(order != null && order.getUserId().equals(orderOptionRequest.getUserId())){
            //判断订单是否为未支付
            if(order.getStatus() == 6){
                throw new CommonException(ResponseCodeEnum.ERROR, StatusEnum.ORDER_WAS_PENDING.label);
            }
            //当订单状态为非预约订单，取消失败
            if(order.getStatus() != 1){
                throw new CommonException(ResponseCodeEnum.ERROR, StatusEnum.ORDER_STATUS_EXPIRE.label);
            }
            //1、根据车位id查询车位锁是否降下来自车区服务 例：
            /*Map<String,Object> lockParam = stallClient.watch(order.getStallId());
            log.info("...........cancel order response result lock param = {}", JSON.toJSON(lockParam));
            if("200".equals(String.valueOf(lockParam.get("code"))) && Integer.valueOf(lockParam.get("status").toString()) == LockStatus.DOWN.status) {
                throw new BusinessException(StatusEnum.ORDER_LOCK_DOWN);
            }*/
            //2、当前订单是否超过免费时长，需要车位对应计费服务 例：
            /*long beginTime = order.getBeginTime().getTime();
            long now = new Date().getTime();
            if(order.getStallId() != null) {
                Map<String,Object> feeParam = new HashMap<String,Object>();
                feeParam.put("stallId", order.getStallId());
                int freeMins = strategyFeeClient.freeMins(feeParam);
                log.info("...........cancel order free mins = {}", freeMins);
                if(now - beginTime > freeMins * 60 * 1000){
                    throw new BusinessException(StatusEnum.ORDER_CANCEL_TIMEOUT);
                }
            }*/

            //3、是否超过当前取消订单次数 例如：
            /*List<ResUserOrder> ordersList = ordersClusterMapper.getDayOfCanceOrderlList(cu.getId());
            if(null != ordersList && ordersList.size() >= baseConfig.getCancelNumber()){
                throw new BusinessException(StatusEnum.ORDER_CANCEL_MORETIMES);
            }*/

            //4、更改订单预约状态
            ordersRepository.cancelOrder(orderOptionRequest.getOrderId(),new Date(),OrderStatusEnum.CANCEL.getCode());
            //5、更改车位预约状态，释放空闲车位

        }else{
            throw new CommonException(ResponseCodeEnum.ERROR, StatusEnum.ORDER_USERID_ERROR.label);
        }
        return null;
    }

    @Override
    public OrderEditResponse suspendOrder(OrderOptionRequest orderOptionRequest) throws CommonException {
        OrderEditResponse response = new OrderEditResponse();
        // 查订单
        Orders order = ordersRepository.getOne(orderOptionRequest.getOrderId());
        if(order == null){
            throw new CommonException(ResponseCodeEnum.ERROR, StatusEnum.ORDER_OPERATE_NULLORDER.label);
        }
        if(order.getStatus() != OrderStatusEnum.BOOKED.getCode()){
            throw new CommonException(ResponseCodeEnum.ERROR, StatusEnum.ORDER_OPERATE_NOUNPAID.label);
        }
        //TODO 挂起订单计算实际费用例如：
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> param = new HashMap<String,Object>();
        param.put("stallId", order.getId());
        param.put("plateNo", order.getPlateNo());
        param.put("startTime", sdf.format(order.getCreateTime()));
        param.put("endTime", sdf.format(new Date()));
        Map<String, Object> res = this.strategyFeeClient.amount(param);
        Double totalAmount = 0d;
        if(res != null) {
            String totalStr = res.get("chargePrice").toString();
            String totalAmountStr = new java.text.DecimalFormat("0.00").format(Double.valueOf(totalStr));
            totalAmount = Double.valueOf(totalAmountStr);
        }*/
        //处理订单的状态
        Double totalAmount = 0d;
        ordersRepository.suspendOrder(orderOptionRequest.getOrderId(),new Date(),OrderStatusEnum.SUSPENDED.getCode(),totalAmount);
        response.setOrderId(order.getId());
        response.setOrderType(order.getOrderType());
        response.setOrderStatus(OrderStatusEnum.SUSPENDED.getCode());
        //处理车位的状态调用车区服务
        return response;
    }

    @Override
    public OrderEditResponse closeOrder(OrderOptionRequest orderOptionRequest)throws CommonException {
        OrderEditResponse editResponse = new OrderEditResponse();
        // 查订单
        Orders orders = ordersRepository.getOne(orderOptionRequest.getOrderId());
        if(orders == null){
            throw new CommonException(ResponseCodeEnum.ERROR, StatusEnum.ORDER_OPERATE_NULLORDER.label);
        }
        if(orders.getStatus() != OrderStatusEnum.BOOKED.getCode()){
            throw new CommonException(ResponseCodeEnum.ERROR, StatusEnum.ORDER_OPERATE_NOUNPAID.label);
        }
        ordersRepository.closeOrder(orderOptionRequest.getOrderId(),new Date(),OrderStatusEnum.CLOSE.getCode());
        editResponse.setOrderId(orders.getId());
        editResponse.setOrderType(orders.getOrderType());
        editResponse.setOrderStatus(OrderStatusEnum.CLOSE.getCode());

        //处理车位的状态调用车区服务
        return editResponse;
    }

    @Override
    public Boolean downLock(OrderDownLockRequest downLockRequest) throws CommonException {
        Orders orders = ordersRepository.getOne(downLockRequest.getOrderId());
        OrderDetail orderDetail = orderDetailRepository.findOrderDetailByOrderId(downLockRequest.getOrderId());

        Boolean authStatus = orderDetail.getPlaceId().equals(downLockRequest.getPlaceId())
                && orders.getStatus() == OrderStatusEnum.BOOKED.getCode()
                && orders.getUserId().equals(downLockRequest.getUserId());
        if(authStatus){
            //车区服务控制降锁逻辑，所需参数
            //orderId,stallId
            Boolean downStatus = null;
            if(!downStatus){
                //根据车区服务返回值进行前端展示
                /*if(this.redisService.exists(RedisKey.ORDER_STALL_DOWN_FAILED.key+ros.getOrderId())) {
                    Object object = this.redisService.get(RedisKey.ORDER_STALL_DOWN_FAILED.key+ros.getOrderId());
                    log.info("down flag reason = {}", StatusEnum.get((int)object));
                    throw new BusinessException(StatusEnum.get((int)object));
                }else {
                    log.info("....................the server is unconnecting");
                    this.redisService.set(RedisKey.ORDER_STALL_DOWN_FAILED.key + ros.getOrderId(), StatusEnum.DOWN_LOCK_FAIL_RETRY.code,ExpiredTime.STALL_DOWN_FAIL_EXP_TIME.time);
                    throw new BusinessException(StatusEnum.DOWN_LOCK_FAIL_RETRY);
                }*/
            }
            Integer downLockStatus = downStatus ? OperateStatusEnum.SUCCESS.getCode() : OperateStatusEnum.FAILURE.getCode();
            orderDetailRepository.updateOrderDetailDownLock(downLockRequest.getOrderId(),new Date(),downLockStatus);
            return downStatus;
        }else{
            return false;
        }
    }

    @Override
    public Boolean upLock(OrderDownLockRequest upLockRequest) throws CommonException {
            Orders orders = ordersRepository.getOne(upLockRequest.getOrderId());
            OrderDetail orderDetail = orderDetailRepository.findOrderDetailByOrderId(upLockRequest.getOrderId());
            Boolean authStatus = orderDetail.getPlaceId().equals(upLockRequest.getPlaceId())
                    && orders.getStatus() == OrderStatusEnum.BOOKED.getCode()
                    && orders.getUserId().equals(upLockRequest.getUserId());
            if(authStatus){
                //车区服务控制升锁逻辑，所需参数 orderId,stallId
                Boolean upStatus = null;
                if(!upStatus){
                    //根据车区服务返回值进行前端展示

                }
                Integer upLockStatus = upStatus ? OperateStatusEnum.SUCCESS.getCode() : OperateStatusEnum.FAILURE.getCode();
                //更新订单状态为3已完成
                ordersRepository.updateOrderStatus(upLockRequest.getOrderId(),new Date(),OrderStatusEnum.PAID.getCode());
                orderDetailRepository.updateOrderDetailUpLock(upLockRequest.getOrderId(),new Date(),upLockStatus);
                return upStatus;
            }else{
                return false;
            }
        }
}
