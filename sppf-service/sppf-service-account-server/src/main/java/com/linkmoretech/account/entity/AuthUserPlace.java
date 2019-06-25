package com.linkmoretech.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author: alec
 * Description:
 * @date: 17:03 2019-06-24
 */
@Entity
@Table(name = "a_user_place")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private Long authDataId;

    private String placeNo;
}
