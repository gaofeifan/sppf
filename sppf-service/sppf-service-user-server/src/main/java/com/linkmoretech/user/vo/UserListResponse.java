package com.linkmoretech.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户列表返回数据
 * @Author: alec
 * @Description:
 * @date: 下午4:47 2019/4/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户列表信息")
public class UserListResponse {

    @ApiModelProperty(value = "用户ID")
    private String id;

    @ApiModelProperty(value = "所在城市")
    private String city;

    @ApiModelProperty(value = "手机号")
    @JsonProperty(value = "personal")
    private String userMobile;

    @ApiModelProperty(value = "微信号")
    @JsonProperty(value = "openId")
    private String openId;

    @ApiModelProperty(value = "车牌号")
    private List<String> platNumbers = new ArrayList<>();

    @ApiModelProperty(value = "最近使用停车场")
    private String parkingName;

    @ApiModelProperty(value = "注册时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime;

    @ApiModelProperty(value = "用户状态")
    @JsonProperty(value = "status")
    private Integer userStatus;

}
