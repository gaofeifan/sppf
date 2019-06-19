package com.linkmoretech.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author: alec
 * Description: 用户角色关联表
 * @date: 14:37 2019-05-31
 */
@Entity
@Table(name = "a_user_roles")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private Long userId;

    private Long rolesId;
}
