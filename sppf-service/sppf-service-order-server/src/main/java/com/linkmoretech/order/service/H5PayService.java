package com.linkmoretech.order.service;

import com.linkmoretech.order.common.request.ReqH5Term;
import com.linkmoretech.order.common.request.ReqH5Token;
import com.linkmoretech.order.common.response.ResH5Degree;
import com.linkmoretech.order.common.response.ResH5Term;

public interface H5PayService {

	ResH5Degree wxOpenid( ReqH5Token reqH5Token);
	
	ResH5Degree aliOpenid( ReqH5Token reqH5Token);
	
	ResH5Term	wxpay(ReqH5Term reqH5Term);
	
	String  alipay(ReqH5Term reqH5Term);
	
}
