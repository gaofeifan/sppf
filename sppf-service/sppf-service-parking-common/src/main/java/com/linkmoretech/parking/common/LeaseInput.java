package com.linkmoretech.parking.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 18:16 2019-05-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaseInput {

    private String leaseCode;

    private List<String> plateCodeList;
}
