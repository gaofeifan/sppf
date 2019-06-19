package com.linkmoretech.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author: alec
 * Description: 用户数据权限记录
 * @date: 14:38 2019-05-31
 */

@Entity
@Table(name = "a_user_data")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDataRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private Long userId;

    private Long carParkId;
}
