package com.linkmoretech.order.resposity;

import com.linkmoretech.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * 订单明细持久层
 * @Author: alec
 * @Description:
 * @date: 下午1:48 2019/4/12
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    /**
     * 根据订单Id查询订单明细
     * @param orderId 订单ID
     * @return 订单明细信息
     * */
    OrderDetail findOrderDetailByOrderId(String orderId);

    /**
     * 根据订单ID查询订单明细列表
     * @param orderIds 订单ids
     * @return 订单明细列表
     * */
    List<OrderDetail> findOrderDetailsByOrderIdIn(String[] orderIds);

    /**
     * 更新订单明细的升锁情况
     * @param orderId 订单ID
     * @param upLockTime 升锁时间
     * @param upLockStatus 升锁结果
     * */
    @Query(value = "update t_orders_detail set up_lock_time=?2, up_lock_success=?3 where order_id=?1", nativeQuery = true)
    @Modifying
    void updateOrderDetailUpLock(String orderId, Date upLockTime, Integer upLockStatus);

    /**
     * 更新订单明细的降锁情况
     * @param orderId 订单ID
     * @param downLockTime 升锁时间
     * @param downLockStatus 升锁结果
     * */
    @Query(value = "update t_orders_detail set down_lock_time=?2, down_lock_success=?3 where order_id=?1", nativeQuery = true)
    @Modifying
    void updateOrderDetailDownLock(String orderId, Date downLockTime, Integer downLockStatus);

    /**
     * 更新订单明细的车位状态
     * @param orderId 订单ID
     * @param parkingStatus 车位状态
     * */
    @Query(value = "update t_orders_detail set parking_status=?2 where order_id=?1", nativeQuery = true)
    @Modifying
    void updateOrderDetailParking(String orderId, Integer parkingStatus);

    /**
     * 更新订单明细的车位锁状态
     * @param orderId 订单ID
     * @param lockStatus 车位状态
     * */
    @Query(value = "update t_orders_detail set lock_status=?2 where order_id=?1", nativeQuery = true)
    @Modifying
    void updateOrderDetailLock(String orderId, Integer lockStatus);

    /**
     * 更新订单明细的驶入时间
     * @param orderId 订单ID
     * @param sailInTime 驶入停车场时间
     * */
    @Query(value = "update t_orders_detail set sail_in_time=?2 where order_id=?1", nativeQuery = true)
    @Modifying
    void updateOrderDetailSailIn(String orderId, Date sailInTime);

    /**
     * 更新订单明细的驶出时间
     * @param orderId 订单ID
     * @param sailOutTime 驶出停车场时间
     * */
    @Query(value = "update t_orders_detail set sail_out_time=?2 where order_id=?1", nativeQuery = true)
    @Modifying
    void updateOrderDetailSailOut(String orderId, Date sailOutTime);
}
