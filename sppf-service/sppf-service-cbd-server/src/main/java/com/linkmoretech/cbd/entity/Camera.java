package com.linkmoretech.cbd.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 摄像头
 * @author jhb
 * @Date 2019年6月27日 上午10:45:02
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "c_camera")
public class Camera {
	@Id
    @GeneratedValue
    private Long id;
    //车场id
    private Long parkId;
    //车场名称
    private String parkName;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //出入口位置类型 1出口 0入口 
    private Short positionType;
    //位置
    private String position;
    //状态 1启用 0禁用
    private Short status;
    //在线状态 1在线 0离线
    private Short onlineStatus;
    //备注
    private String remarks;
    //设备序列号
    private String serialNumber;

}