package com.linkmoretech.versatile.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author: jhb
 * @Description:
 * @date: 8:33 PM 2019/4/29
 */
@Data
public class StaffAppVersionCreateRequest {

    @ApiModelProperty(value = "版本号", required = true)
    @NotNull(message = "版本号不能为空")
    private String version;

    @ApiModelProperty(value = "编码", required = true)
    @NotNull(message = "编码不能为空")
    private Long code;

    @ApiModelProperty(value = "名称", required = true)
    @NotNull(message = "名称不能为空")
    private String name;

    private Integer status = 0;

    @ApiModelProperty(value = "url", required = true)
    @NotNull(message = "url不能为空")
    private String url;
    @ApiModelProperty(value = "适用客户端", required = true)
    @NotNull(message = "1安卓；2ios；")
    private Integer type;

    @ApiModelProperty(value = "必须升级", required = true)
    @NotNull(message = "1是；")
    private Integer updateStatus = 0;

    @ApiModelProperty(value = "描述", required = true)
    @NotNull(message = "描述不能为空")
    private String description;

}
