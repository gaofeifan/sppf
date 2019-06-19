package com.linkmoretech.parking.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: alec
 * Description: 车位详细信息
 * @date: 14:46 2019-05-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarPlaceInfoResponse {

    /** 车场名称;所在车场名称 */
    private String parkName ;

    /** 车位编号;车位编号 */
    private String placeNo ;

    private String lockCode ;

    private Integer placeType ;

    /** 创建人;创建人 */
    private String createBy ;

    /** 创建时间;创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime ;

    /** 更新人;更新人 */
    private String updateBy ;

    /** 更新时间;更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime ;
}
