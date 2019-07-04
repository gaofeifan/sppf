package com.linkmoretech.parking.controller.read;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkmoretech.parking.service.CarParkService;
import com.linkmoretech.parking.vo.request.UserParkListRequest;
import com.linkmoretech.parking.vo.response.CityParkListResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import reactor.ipc.netty.http.server.HttpServerRequest;

/**
 * @Author: GFF
 * @Description:
 */
@RestController
@RequestMapping(value = "car/park/read")
@Slf4j
@Api(tags = "车场读操作API", value = "CarParkReadController" )
public class CarParkReadController {

    @Autowired
    CarParkService carParkService;

    @ApiOperation(value = "查询车场列表", notes = "获取用户具有权限的车场")
    @GetMapping(value = "car-park-list")
    public  List<CityParkListResponse> carParkList(Authentication authentication){
        List<CityParkListResponse> parkListResponses = carParkService.carParkList(authentication);
        return parkListResponses;
    }

//    @ApiOperation(value = "查询车场列表", notes = "获取用户具有权限的车场")
//    @PostMapping(value = "car-park-list")
//    public  List<CityParkListResponse> carParkList(@RequestBody UserParkListRequest parkListRequest){
//        List<CityParkListResponse> parkListResponses = carParkService.carParkList(authentication);
//        return parkListResponses;
//    }
    


}
