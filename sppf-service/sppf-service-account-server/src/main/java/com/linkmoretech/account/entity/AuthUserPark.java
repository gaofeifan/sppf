package com.linkmoretech.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author: alec
 * Description: 用户车场权限
 * @date: 16:27 2019-06-24
 */
@Entity
@Table(name = "a_user_park")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserPark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private Long userId;

    private Long parkId;

    private String parkName;

    private Integer authType;


}
