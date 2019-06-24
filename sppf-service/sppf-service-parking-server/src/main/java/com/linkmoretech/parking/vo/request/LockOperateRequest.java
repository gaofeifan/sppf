package com.linkmoretech.parking.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/6/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LockOperateRequest {
    @ApiModelProperty(value = "车位id 与锁编号有一个即可 (默认取锁编号) ",required = false)
    private Long carPlaceId;
    @ApiModelProperty(value = "操作动作 1下降 2升级 ",required = true)
    private Integer state;
    @ApiModelProperty(value = "锁编号  与车位id有一个即可(默认取锁编号)",required = false)
    private String lockSn;
    @ApiModelProperty(value = "操作后消息通知类型 0直连(有结果后返回)  1 极光(立即返回 结果由极光推送) 2 自定义推送(未实现)",required = false)
    private Integer msgStatus = 0;

}
