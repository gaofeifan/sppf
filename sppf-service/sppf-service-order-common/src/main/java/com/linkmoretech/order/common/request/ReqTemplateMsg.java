package com.linkmoretech.order.common.request;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqTemplateMsg {

	private List<String> openId = new ArrayList<>();
	
	private String templateId;
	
	private String url;
	
	private String topcolor = "#FF0000";

	private Object data;
	
	private String token;
	
}
