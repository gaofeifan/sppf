package com.linkmoretech.parking.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.linkmoretech.common.util.JsonUtil;
import com.linkmoretech.common.util.SpringUtil;
import com.linkmoretech.parking.service.CarPlaceService;
import com.linkmoretech.parking.service.LockOperateService;
import com.linkmoretech.parking.vo.request.LockOperateRequest;
import com.linkmoretech.parking.vo.response.CarPlaceDetailsResponse;
import com.linkmoretech.parking.vo.response.CarPlaceDetailsSnResponse;
import com.linkmoretech.parking.vo.response.ResGateway;
@Component
//public class StartUp //implements CommandLineRunner{
public class StartUp implements CommandLineRunner{

//	@Override
	public void run(String... args) throws Exception {
		new Thread(()-> {
			while (true) {
			CarPlaceService carPlaceService = SpringUtil.getBean(CarPlaceService.class);
//			CarPlaceListRequest carPlace = new CarPlaceListRequest();
//			carPlace.setCarParkId(696L);
//			carPlace.setType(1);
//			List<CarPalceListResponse> detail = carPlaceService.findCarPlaceListByParkId(null, carPlace, null);
//			System.out.println(JsonUtil.toJson(detail));
			CarPlaceDetailsResponse details = carPlaceService.details(null, 1L);
			System.out.println(JsonUtil.toJson(details));
//			
			CarPlaceDetailsSnResponse detailsSn = carPlaceService.detailsSn(null,"0000d73f725a2350",696L);
			System.out.println(JsonUtil.toJson(detailsSn));
//			
//			CarParkService carParkService = SpringUtil.getBean(CarParkService.class);
//			List<CityParkListResponse> list = carParkService.carParkList(null);
//			System.out.println(JsonUtil.toJson(list));
			
			LockOperateService operateService = SpringUtil.getBean(LockOperateService.class);
//			List<ResGateway> list = operateService.findGatewayGroup(1026L, null);
			LockOperateRequest lockOperate = new LockOperateRequest();
			lockOperate.setCarPlaceId(1040L);
			lockOperate.setState(1);
			operateService.operate(null, lockOperate );
			Boolean removeLock = operateService.removeStallLock(1040L, null);
//			ReqLockIntall reqLockIntall = new ReqLockIntall();
//			reqLockIntall.setFloor("B");
//			reqLockIntall.setFloorId(1028L);
//			reqLockIntall.setLockSn("F12A4A6357F7");
//			reqLockIntall.setPreId(1026L);
//			reqLockIntall.setStallName("57f7");
//			operateService.installLock(reqLockIntall , null);
//			List<ResGateway> gatewayGroup = operateService.findGatewayGroup(696L, null);
//			System.out.println(JsonUtil.toJson(gatewayGroup));			

			}
		}).start();;
		
		
	}

}
