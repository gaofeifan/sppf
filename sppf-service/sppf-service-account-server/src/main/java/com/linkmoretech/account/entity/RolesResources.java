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
 * Description: 角色资源关联表
 * @date: 16:15 2019-05-30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "a_roles_resources")
public class RolesResources {

    @Id
    @GeneratedValue
    private Long id;

    private Long rolesId;

    private Long resourcesId;
}
