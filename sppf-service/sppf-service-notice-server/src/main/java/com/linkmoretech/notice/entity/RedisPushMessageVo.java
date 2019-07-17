package com.linkmoretech.notice.entity;

import java.io.Serializable;

import com.linkmoretech.notice.vo.request.PushMesRequest;
import com.linkmoretech.notice.vo.request.PushMessageRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisPushMessageVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private PushMesRequest pushMesRequest;
	
	private volatile int count;
	
}
