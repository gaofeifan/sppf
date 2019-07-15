package com.linkmoretech.station.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkmoretech.station.entity.Camera;
/**
 * 摄像头
 * @author jhb
 * @Date 2019年6月27日 上午10:53:47
 * @Version 1.0
 */
public interface CameraRepository extends JpaRepository<Camera, Long> {

}
