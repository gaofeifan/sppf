package com.linkmoretech.user.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @Author: alec
 * Description: 长租记录联合主键
 * 用户ID 和 长租记录授权码做为联合主键
 * @date: 15:00 2019-05-24
 */
@Data
public class LeaseUserPk implements Serializable {

    private String userId;

    private String leaseCode;
}
