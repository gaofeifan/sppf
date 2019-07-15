package com.linkmoretech.versatile.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 异常日志
 * @author jhb
 * @Date 2019年7月11日 下午3:08:37
 * @Version 1.0
 */
@Data
@Entity
@Table(name = "v_unusual_log")
@AllArgsConstructor
@NoArgsConstructor
public class UnusualLog {
	@Id
	private Long id;
    /**
     *  app版本
     */ 
    private String appVersion;

    /**
     *  os版本
     */ 
    private String osVersion;

    /**
     *  型号
     */ 
    private String model;

    /**
     *  客户端类型
     */ 
    private Integer clientType;

    /**
     *  日志级别
     */ 
    private String level;

    /**
     *  品牌
     */ 
    private String brand;
    
    private Integer system;
    
    private String content;
    
    private Date createTime;
    
}