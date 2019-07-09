package com.linkmoretech.parking.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: GFF
 * @Description: 车位锁详情数据
 * @Date: 2019/6/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarPlaceDetailsSnResponse {
    @ApiModelProperty(value="车位id")
    private Long carPlaceId;
    @ApiModelProperty(value="车位名称")
    private String carPlaceName;
    @ApiModelProperty(value="车位锁编号")
    private String carPlaceLockSn;
    @ApiModelProperty(value="车位锁序列号")
    private String serialNumber;
    @ApiModelProperty(value="型号")
    private String model;
    @ApiModelProperty(value="版本")
    private String version;
    @ApiModelProperty("分区")
    private String areaName;
    @ApiModelProperty(value="车位锁状态 1 升起  2 降下 3 故障")
    private Integer lockStatus;
    @ApiModelProperty(value="车位锁离线状态 1离线  2 在线")
    private Integer lockOffLine;
    @ApiModelProperty(value="超声波 0 无车 1 有车 2其他(未知)")
    private int ultrasonic;
    @ApiModelProperty(value="超声波设备状态 0 异常 1正常 其他值表示未知")
    private int inductionState;
    @ApiModelProperty(value="电量")
    private int battery;
    @ApiModelProperty(value="车场id")
    private Long parkId;
    @ApiModelProperty(value="车场名称")
    private String parkName;
    @ApiModelProperty(value="城市id")
    private Long cityId;
    @ApiModelProperty(value="城市代码")
    private String cityCode;
    @ApiModelProperty(value="城市名称")
    private String cityName;
    @ApiModelProperty(value="车位状态 状态:0，空闲；1，使用中 ")
    private int carPlaceStatus;
    @ApiModelProperty(value=" 0 未安装  1已安装 ")
    private int installStatus = 0;
    @ApiModelProperty(value=" 是否绑定 true是 false 否 ")
    private boolean bindStatus = false;
    @ApiModelProperty(value="1 未绑定 2已绑定")
    private int bindStata = 1;
    @ApiModelProperty(value="车位所属层级")
    private String floor;
    @ApiModelProperty(value="车位所属层级Id")
    private Long floorId;
    /** 上线状态;上线状态: 1: 上线，2:下线 */
    @ApiModelProperty(value="锁在线状态  0下线 1上线")
    private Integer lineStatus ;
    @ApiModelProperty(value="车位锁绑定的网关")
    private List<ResLockGatewayList> gatewayList = new ArrayList<>();





}
