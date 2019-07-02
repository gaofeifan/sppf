package com.linkmoretech.parking.controller.read;

import com.linkmoretech.parking.common.PlaceParkIdAndRangeInput;
import com.linkmoretech.parking.common.PlaceParkIdAndRangeOutput;
import com.linkmoretech.parking.service.CarParkService;
import com.linkmoretech.parking.service.CarPlaceService;
import com.linkmoretech.parking.vo.request.CarPlaceListRequest;
import com.linkmoretech.parking.vo.response.CarPalceListResponse;
import com.linkmoretech.parking.vo.response.CarPlaceDetailsResponse;
import com.linkmoretech.parking.vo.response.CarPlaceDetailsSnResponse;
import com.linkmoretech.parking.vo.response.CityParkListResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.ipc.netty.http.server.HttpServerRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: GFF
 * @Description:
 */
@RestController
@RequestMapping(value = "car/place/read")
@Slf4j
@Api(tags = "车位读操作API", value = "CarPlaceController" )
public class CarPlaceReadController {

    @Autowired
    CarPlaceService carPlaceService;

    @ApiOperation(value = "查询车位列表", notes = "获取车场下的车位")
    @PostMapping(value = "car-park-list")
    public  List<CarPalceListResponse> carParkList(HttpServletRequest request, Authentication authentication, @RequestBody CarPlaceListRequest carPlace){
        List<CarPalceListResponse> list = this.carPlaceService.findCarPlaceListByParkId(request,carPlace,authentication);
        return list;
    }

    @ApiOperation(value = "查询车位详情", notes = "获取车位的详情数据")
    @GetMapping(value = "details")
    public  CarPlaceDetailsResponse details(HttpServletRequest request, @ApiParam(value="车位id",required = true) @RequestParam("carPlaceId") Long carPlaceId){
        log.info("details is running");
        return this.carPlaceService.details(request,carPlaceId);
    }

    @ApiOperation(value = "查询车位锁", notes = "获取车位与锁绑定的详情数据")
    @GetMapping(value = "car-park-details-sn")
    public CarPlaceDetailsSnResponse detailsSn(HttpServletRequest request, @ApiParam("车位锁编号")  @RequestParam("sn") String sn,
                                               @ApiParam(value="车场",required=false) @RequestParam(value="carParkId",required=false) Long carParkId){
        return this.carPlaceService.detailsSn(request,sn,carParkId);
    }

    @PostMapping(value = "park-id-and-id-range")
    @ApiOperation(value = "查询车位数据", notes = "根据车场id与车位区间的id查询")
    public List<PlaceParkIdAndRangeOutput> findByParkIdAndIdRange(@RequestBody PlaceParkIdAndRangeInput input){
        return this.carPlaceService.findByParkIdAndIdRange(input);
    }
}
