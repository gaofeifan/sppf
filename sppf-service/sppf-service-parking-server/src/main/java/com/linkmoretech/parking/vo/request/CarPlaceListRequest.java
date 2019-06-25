package com.linkmoretech.parking.vo.request;

/**
 * @Author: alec
 * @Description:
 * @date: 1:26 PM 2019/4/29
 */
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Author GFF
 * @Description 查询车位所需参数
 * @Date 2019/6/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "车位列表请求")
public class CarPlaceListRequest {
    @ApiModelProperty(value="车场id必填",required=true)
    @NotNull(message="车场id不能为空")
    private Long carParkId;
    @ApiModelProperty(value="车位名称模糊查询(非必填 )",required=false)
    private String carPlaceName;
    @ApiModelProperty(value="车位类型(2临停,1固定)",required=true)
    private Integer type;

}
