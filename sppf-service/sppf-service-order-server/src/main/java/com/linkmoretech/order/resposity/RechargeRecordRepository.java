package com.linkmoretech.order.resposity;

import org.springframework.data.jpa.repository.JpaRepository;
import com.linkmoretech.order.entity.RechargeRecord;

/**
 * 充值明细持久层
 * @author jhb
 * @Date 2019年6月20日 下午7:18:25
 * @Version 1.0
 */
public interface RechargeRecordRepository extends JpaRepository<RechargeRecord, String> {

	RechargeRecord findRechargeRecordByCode(String code);

}
