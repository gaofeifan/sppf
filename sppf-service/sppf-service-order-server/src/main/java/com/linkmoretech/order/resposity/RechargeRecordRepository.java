package com.linkmoretech.order.resposity;

import org.springframework.data.jpa.repository.JpaRepository;
import com.linkmoretech.order.entity.RechargeRecord;

/**
 * 充值明细持久层
 * @Author: jhb
 * @Description:
 * @date: 下午1:48 2019/4/12
 */
public interface RechargeRecordRepository extends JpaRepository<RechargeRecord, String> {

	RechargeRecord findRechargeRecordByCode(String code);

}
