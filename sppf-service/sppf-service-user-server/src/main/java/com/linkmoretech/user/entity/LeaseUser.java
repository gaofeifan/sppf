package com.linkmoretech.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 长租用户
 * @Author: alec
 * @Description:
 * @date: 下午5:29 2019/4/15
 */
@Entity
@Table(name = "t_rental_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(LeaseUserPk.class)
public class LeaseUser {

    @Id
    @Column(length = 20)
    private Long userId;

    @Id
    @Column(length = 50)
    private String leaseCode;

    /**用户手机号*/
    private String mobile;
}
