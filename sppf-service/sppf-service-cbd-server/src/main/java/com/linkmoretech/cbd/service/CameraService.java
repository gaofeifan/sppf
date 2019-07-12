package com.linkmoretech.cbd.service;

import javax.validation.Valid;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.cbd.vo.request.CameraRequest;
import com.linkmoretech.cbd.vo.response.CameraPageResponse;

/**
 * 投诉建议
 * @author jhb
 * @Date 2019年6月27日 下午1:51:31
 * @Version 1.0
 */
public interface CameraService {

	void save(CameraRequest camera);

	void update(CameraRequest camera);

	void delete(Long id);

	PageDataResponse<CameraPageResponse> searchPage(@Valid PageSearchRequest searchRequest);

	CameraPageResponse findById(Long id);

	void updateEnableStatus(Long id, Integer code);
}
