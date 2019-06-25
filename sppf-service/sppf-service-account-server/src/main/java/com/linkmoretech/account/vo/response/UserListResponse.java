package com.linkmoretech.account.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author: alec
 * Description: 用户分页显示数据
 * @date: 16:32 2019-05-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListResponse {

    private Long id;

    private String clientId;

    private String userName;

    private Integer status;

    private String mobile;

    private Integer authStatus;

    private List<String> rolesList;

    @JsonFormat(pattern = "MM-dd HH:mm:dd")
    private Date lastLoginTime;
}
