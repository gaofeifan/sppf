package com.linkmoretech.versatile.vo.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jhb
 * @Description:
 * @date: 10:20 AM 2019/4/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseDictGroupPageResponse {
	
    private Long id;

    private String code;

    private String name;
    
    private Date createTime;
    
    private int orderIndex;
}
