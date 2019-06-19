package com.linkmoretech.account.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * Description:
 * @date: 15:48 2019-06-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesEditRequest {

    private Long id;

    private String rolesName;


    private String content;

}
