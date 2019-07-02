package com.linkmoretech.account.client.fallback.common.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * Description: 获取数据权限传入参数
 * @date: 11:28 2019-06-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountPlaceRequest extends  AccountParkRequest{

    private Long parkId;

}
