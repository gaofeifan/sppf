package com.linkmoretech.order.resposity;

import com.linkmoretech.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 订单持久层
 * @Author: alec
 * @Description:
 * @date: 下午1:48 2019/4/12
 */
public interface OrdersRepository extends JpaRepository<Orders, String> {

    /**
     * 根据用户ID查询未结算订单
     * @param userId 用户ID
     * @param status 订单状态
     * @return 订单
     * */
    Orders findOrdersByUserIdAndStatusIn(String userId, Integer... status);
}
