package com.linkmoretech.parking.config;

import java.util.List;

import com.linkmoretech.parking.service.CarParkService;
import com.linkmoretech.parking.vo.response.CityParkListResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.util.JsonUtil;
import com.linkmoretech.common.util.SpringUtil;
import com.linkmoretech.parking.service.CarPlaceService;
import com.linkmoretech.parking.service.LockOperateService;
import com.linkmoretech.parking.vo.request.LineStatusRquest;
import com.linkmoretech.parking.vo.request.LockOperateRequest;
import com.linkmoretech.parking.vo.response.CarParkFloorsResponse;
import com.linkmoretech.parking.vo.response.CarPlaceDetailsResponse;
import com.linkmoretech.parking.vo.response.CarPlaceDetailsSnResponse;
import com.linkmoretech.parking.vo.response.ResGateway;
//@Component
public class StartUp {
//public class StartUp implements CommandLineRunner{

//	@Override
	public void run(String... args) throws Exception {
		new Thread(()-> {
//			while (true) {
			CarPlaceService carPlaceService = SpringUtil.getBean(CarPlaceService.class);
				CarParkService carParkService = SpringUtil.getBean(CarParkService.class);
//				List<CarParkFloorsResponse> list = carParkService.carParkFloors(1075L);
//				System.out.println(JsonUtil.toJson(list));
//				List<CityParkListResponse> list1 = carPark Service.carParkList(null);
//				System.out.println(JsonUtil.toJson(list1));


//			CarPlaceListRequest carPlace = new CarPlaceListRequest();
//			carPlace.setCarParkId(696L);
//			carPlace.setType(1);
//			List<CarPalceListResponse> detail = carPlaceService.findCarPlaceListByParkId(null, carPlace, null);
//			System.out.println(JsonUtil.toJson(detail));
//			CarPlaceDetailsResponse details = carPlaceService.details(null, 1L);
//			System.out.println(JsonUtil.toJson(details));
////
//			CarPlaceDetailsSnResponse detailsSn = carPlaceService.detailsSn(null,"0000d73f725a2350",696L);
//			System.out.println(JsonUtil.toJson(detailsSn));
//			
//			CarParkService carParkService = SpringUtil.getBean(CarParkService.class);
//			List<CityParkListResponse> list = carParkService.carParkList(null);
//			System.out.println(JsonUtil.toJson(list));
				LockOperateService operateService = SpringUtil.getBean(LockOperateService.class);
				LineStatusRquest lineStatusRquest = new LineStatusRquest();
				lineStatusRquest.setCarPlaceId(1217L);
				lineStatusRquest.setState(1);
				try {
					operateService.editLineStatus(lineStatusRquest );
				} catch (CommonException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			LockOperateRequest lockOperate = new LockOperateRequest();
			lockOperate.setLockSn("d674101962ea");
			lockOperate.setState(1);
			try {
				Boolean operate = operateService.operate(null, lockOperate);
				System.out.println("thread1"+operate);
			} catch (CommonException e) {
				e.printStackTrace();
			}
			
//				operateService.getGatewayDetails("0001f46ec0f58a3c",null);
//				operateService.findGatewayGroup(1026L,null);
//				operateService.lockSignalHistory(null,"d674101962ea");
//			List<ResGateway> list = operateService.findGatewayGroup(1026L, null);
//			LockOperateRequest lockOperate = new LockOperateRequest();
//			lockOperate.setCarPlaceId(1179L);
//			lockOperate.setState(1);
//			operateService.operate(null, lockOperate );
//			Boolean removeLock = operateService.removeStallLock(1040L, null);
//			ReqLockIntall reqLockIntall = new ReqLockIntall();
//			reqLockIntall.setFloor("B");
//			reqLockIntall.setFloorId(1028L);
//			reqLockIntall.setLockSn("F12A4A6357F7");
//			reqLockIntall.setPreId(1026L);
//			reqLockIntall.setStallName("57f7");
//			operateService.installLock(reqLockIntall , null);
//			List<ResGateway> gatewayGroup = operateService.findGatewayGroup(696L, null);
//			System.out.println(JsonUtil.toJson(gatewayGroup));			

//			}
		}).start();;
		
		
		
		/*new Thread(()-> {
			LockOperateService operateService = SpringUtil.getBean(LockOperateService.class);
			LockOperateRequest lockOperate = new LockOperateRequest();
			lockOperate.setLockSn("d674101962ea");
			lockOperate.setState(2);
			try {
				Boolean operate = operateService.operate(null, lockOperate);
				System.out.println("thread2"+operate);
			} catch (CommonException e) {
				e.printStackTrace();
			}
//			}
		}).start();;*/
	}

}
