package com.linkmoretech.station.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.station.entity.Camera;
import com.linkmoretech.station.repository.CameraRepository;
import com.linkmoretech.station.service.CameraService;
import com.linkmoretech.station.vo.request.CameraRequest;
import com.linkmoretech.station.vo.response.CameraPageResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 设备管理
 * 
 * @author jhb
 * @Date 2019年6月27日 下午1:53:12
 * @Version 1.0
 */
@Service
@Slf4j
public class CameraServiceImpl implements CameraService {

	@Autowired
	CameraRepository cameraRepository;

	@Override
	public void save(CameraRequest cameraRequest) {
		Date currentDate = new Date();
		Camera camera = new Camera();
		BeanUtils.copyProperties(cameraRequest, camera);
		camera.setCreateTime(currentDate);
		camera.setUpdateTime(currentDate);
		cameraRepository.save(camera);
	}

	@Override
	public void update(CameraRequest cameraRequest) {
		Date currentDate = new Date();
		Camera camera = cameraRepository.findById(cameraRequest.getId()).get();
		BeanUtils.copyProperties(cameraRequest, camera);
		camera.setUpdateTime(currentDate);
		cameraRepository.saveAndFlush(camera);
	}

	@Override
	public void delete(Long id) {
		cameraRepository.deleteById(id);
	}

	@Override
	public PageDataResponse<CameraPageResponse> searchPage(@Valid PageSearchRequest pageSearchRequest) {
		Pageable pageable = PageRequest.of(pageSearchRequest.getPageNo(), pageSearchRequest.getPageSize());
		Page<Camera> cameraPage = cameraRepository.findAll(pageable);
		PageDataResponse<CameraPageResponse> pageDataResponse = new PageDataResponse<>();
		pageDataResponse.setTotal(cameraPage.getTotalElements());
		List<Camera> cameraList = cameraPage.getContent();
		List<CameraPageResponse> cameraPageResponses = cameraList.stream()
				.map(camera -> {
					CameraPageResponse cameraPageResponse = new CameraPageResponse();
					BeanUtils.copyProperties(camera, cameraPageResponse);
					return cameraPageResponse;
				}).collect(Collectors.toList());
		pageDataResponse.setData(cameraPageResponses);
		return pageDataResponse;
	}

	@Override
	public CameraPageResponse findById(Long id) {
		CameraPageResponse cameraPageResponse = new CameraPageResponse();
		Camera camera = cameraRepository.findById(id).get();
		BeanUtils.copyProperties(camera, cameraPageResponse);
		return cameraPageResponse;
	}

	@Override
	public void updateEnableStatus(Long id, Integer status) {
		Camera camera = cameraRepository.findById(id).get();
		camera.setStatus(status.shortValue());
		camera.setUpdateTime(new Date());
		cameraRepository.saveAndFlush(camera);
	}

}
