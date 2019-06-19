package com.linkmoretech.user.message;

import com.alibaba.fastjson.JSONObject;
import com.linkmoretech.parking.common.LicensePlateOutput;
import com.linkmoretech.parking.common.MqLeasePlaceQueueCommon;
import com.linkmoretech.user.component.LeaseUserComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 18:31 2019-05-22
 */
@Component
@Slf4j
public class ParkingReceiveStream {

    @Autowired
    LeaseUserComponent leaseUserComponent;

    @RabbitListener(queuesToDeclare = @Queue(MqLeasePlaceQueueCommon.ADD_LEASE_PLACE_QUEUE))
    public void processAddLeasePlace(String message) {
        log.info("接受到车区服务投递到创建长租车位消息 {}", message);
        try {
            List<LicensePlateOutput> leasePlaceOutputs = JSONObject.parseArray(message, LicensePlateOutput.class);
            leaseUserComponent.handlerLeaseUser(leasePlaceOutputs);
        } catch (Exception e) {
            log.error("json 解析异常 {} - {} ", message, e);
        }
    }


    @RabbitListener(queuesToDeclare = @Queue(MqLeasePlaceQueueCommon.REMOVE_LEASE_PLACE_QUEUE))
    public void processRemoveLeasePlace(String message) {
        log.info("接受到车区服务投递到删除或冻结长租车位消息 {}", message);
        leaseUserComponent.handlerRemoveLeaseUser(message);
    }
}
