package com.linkmoretech.parking.controller.read;

import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import com.linkmoretech.parking.service.CarParkService;
import com.linkmoretech.parking.vo.request.CarParkCreateRequest;
import com.linkmoretech.parking.vo.request.CarParkEditRequest;
import com.linkmoretech.parking.vo.request.CarParkLineRequest;
import com.linkmoretech.parking.vo.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reactor.ipc.netty.http.server.HttpServerRequest;

import javax.validation.Valid;
import java.util.List;

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
    public  List<CityParkListResponse> carParkList(HttpServerRequest request){
        List<CityParkListResponse> parkListResponses = carParkService.carParkList(request);
        return parkListResponses;
    }



}
