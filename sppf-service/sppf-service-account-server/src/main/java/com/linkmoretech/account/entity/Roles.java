package com.linkmoretech.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: alec
 * Description: 角色信息表
 * @date: 14:28 2019-05-30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "a_roles")
public class Roles {

    @Id
    @GeneratedValue
    private Long id;

    private String rolesName;

    private String clientId;

    private String content;
}
