package com.linkmoretech.parking.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 14:35 2019-05-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LicensePlateOutput implements Serializable, Cloneable {

    private String leaseCode;

    private List<String> licensePlateNoList;
}
