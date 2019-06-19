package com.linkmoretech.order.common.request;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqOrder {
	private String orderNo;
	private Date beginTime;
	private Date endTime;
	private BigDecimal totalAmount;
	private BigDecimal actualAmount;
	private String plateNo;
	private Long preId;
	private String dockId;
	private Integer status;
	
}
