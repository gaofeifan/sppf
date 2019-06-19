package com.linkmoretech.order.common.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResWechatUserList {

	private Integer total;

	private Integer count;

	private List<String> openIds;

	private String nextOpenid;

}
