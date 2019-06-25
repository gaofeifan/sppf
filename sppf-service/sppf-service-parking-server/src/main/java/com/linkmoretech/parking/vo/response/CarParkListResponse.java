package com.linkmoretech.parking.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/6/6
 */
@Data
@Api("车场列表")
public class CarParkListResponse {
    @ApiModelProperty(value = "车区名称")
    @JsonProperty("preName")
    private String parkName;
    @ApiModelProperty(value = "车区id")
    @JsonProperty("preId")
    private Long id;

    @ApiModelProperty(value = "车场车位总数")
    @JsonProperty("preTypeStalls")
    private int parkAllAmount;

    @ApiModelProperty(value = "车场车位使用数")
    @JsonProperty("preUseTypeStalls")
    private int parkUseNumber;

    @ApiModelProperty(value = "车场车位类型使用总数")
    @JsonProperty("typeStalls")
    private Map<String, ParkCarPlaceTypeResponse> carPlaceType = new HashMap<>();

    @ApiModelProperty(value = "车场空闲总数")
    @JsonProperty("preLeisureTypeStalls")
    private int parkLeisureAmount;

    @ApiModelProperty(value = "车位故障总数")
    @JsonProperty("preFaultTypeStalls")
    private int parkFaultAmount;

    @ApiIgnore
    public static class ParkCarPlaceTypeBuilder{
        private ParkCarPlaceTypeResponse carPlace;
        public ParkCarPlaceTypeBuilder(){
            carPlace = new ParkCarPlaceTypeResponse();
        }
        public ParkCarPlaceTypeResponse builder(){
            return carPlace;
        }
        public ParkCarPlaceTypeBuilder type(Short type){
            carPlace.setType(type);
            return this;
        }
        public ParkCarPlaceTypeBuilder typeName(String typeName){
            carPlace.setTypeName(typeName);
            return this;
        }
        public ParkCarPlaceTypeBuilder parkAmount(int parkAmount){
            carPlace.setParkAmount(parkAmount);
            return this;
        }
        public ParkCarPlaceTypeBuilder parkUseAmount(int parkUseAmount){
            carPlace.setParkUseAmount(parkUseAmount);
            return this;
        }
    }
}

@Api("车场车位类型")
@Data
class ParkCarPlaceTypeResponse {
    @ApiModelProperty(value = "类型 0临停 2 固定")
    private Short type;
    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "车位总数")
    @JsonProperty("preTypeStalls")
    private int parkAmount;
    @ApiModelProperty(value = "车位使用总数")
    @JsonProperty("preUseTypeStalls")
    private int parkUseAmount;

}