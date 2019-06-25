package com.linkmoretech.order.resposity;

import com.linkmoretech.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

/**
 * 订单持久层
 * @author jhb
 * @Date 2019年6月20日 下午7:18:12
 * @Version 1.0
 */
@Repository
@Transactional
public interface OrdersRepository extends JpaRepository<Orders, String> {

    /**
     * 根据用户ID查询未结算订单
     * @param userId 用户ID
     * @param status 订单状态
     * @return 订单
     * */
    Orders findOrdersByUserIdAndStatusIn(String userId, Integer... status);
    
    

    /**
     * 取消订单
     * @param orderId
     * @param finishDate
     * @param status
     */
    @Query(value = "update o_orders set status=?3, finish_time=?2, update_time =?2 where id=?1", nativeQuery = true)
    @Modifying
    void cancelOrder(String orderId, Date finishDate, Integer status);

    /**
     * 挂起订单
     * @param orderId
     * @param finishDate
     * @param status
     * @param totalAmount
     */
    @Query(value = "update o_orders set status=?3, status_time=?2, update_time =?2, total_amount =?4 where id=?1", nativeQuery = true)
    @Modifying
    void suspendOrder(String orderId, Date finishDate, Integer status,Double totalAmount);

    /**
     * 关闭订单
     * @param orderId
     * @param finishDate
     * @param status
     */
    @Query(value = "update o_orders set status=?3, finish_time=?2, status_time=?2, update_time =?2 where id=?1", nativeQuery = true)
    @Modifying
    void closeOrder(String orderId, Date finishDate, Integer status);

    /**
     * 升锁后更新订单状态及时间
     * @param orderId
     * @param date
     * @param status
     */
    @Query(value = "update o_orders set status=?3, finish_time=?2, pay_time=?2, update_time =?2 where id=?1", nativeQuery = true)
    @Modifying
    void updateOrderStatus(String orderId, Date date, Integer status);

    /**
     * 确认支付更新订单信息
     * @param param
     */
    @Query(value = "update o_orders set finish_time=?3, pay_type=?2, update_time =?3, total_amount=?4, pay_amount =?5  where id=?1", nativeQuery = true)
    @Modifying
	void updateConfirm(String id, Integer payType, Date finishTime, BigDecimal totalAmount, BigDecimal payAmount);

    /**
     * 完成支付更新订单信息
     * @param param
     */
    @Query(value = "update o_orders set finish_time=?3, pay_time=?3, update_time =?3, status=?2  where id=?1", nativeQuery = true)
    @Modifying
	void finishOrder(String id, Integer status, Date current);

    //and date(createTime) = curdate()
    @Query("select o from Orders o where o.userId =?3 and o.status = 4 ")
	List<Orders> getDayOfCancelOrderList(String userId);
}
