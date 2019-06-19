package com.linkmoretech.versatile.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: jhb
 * @Description:
 * @date: 10:20 AM 2019/4/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffAppVersionPageResponse {

    private Long id;

    private String version;

    private Long code;

    private String name;

    private Integer status;

    private String url;

    private Integer type;

    private Integer updateStatus;

    private Date createTime;

    private Date updateTime;
}
