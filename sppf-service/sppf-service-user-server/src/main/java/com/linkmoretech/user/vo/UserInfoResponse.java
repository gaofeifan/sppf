package com.linkmoretech.user.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 响应用户详情数据
 * @Author: alec
 * @Description:
 * @date: 下午3:56 2019/4/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户详情信息")
public class UserInfoResponse {
    @ApiModelProperty(value = "用户ID")
    private String id;

    @ApiModelProperty(value = "用户账号")
    @JsonProperty(value = "name")
    private String userName;

    @ApiModelProperty(value = "手机号")
    @JsonProperty(value = "personal")
    private String userMobile;

    @ApiModelProperty(value = "用户昵称")
    @JsonProperty(value = "nickName")
    private String userNick;

    @ApiModelProperty(value = "用户性别")
    @JsonProperty(value = "sex")
    private Integer userSex;

    @ApiModelProperty(value = "用户来源")
    @JsonProperty(value = "source")
    private Integer userSource;

    @ApiModelProperty(value = "用户状态")
    @JsonProperty(value = "status")
    private Integer userStatus;

    @ApiModelProperty(value = "注册时间")
    private Date registerTime;

    @ApiModelProperty(value = "OpenID")
    private String openId;

    @ApiModelProperty(value = "车辆型号")
    private String vehicleType;

    @ApiModelProperty(value = "车辆品牌")
    private String vehicleBrand;
}
