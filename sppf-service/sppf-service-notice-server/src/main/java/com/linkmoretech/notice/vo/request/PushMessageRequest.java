package com.linkmoretech.notice.vo.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/9
 */
@ApiModel
@Data
public class PushMessageRequest {
    private Long userId;

    private String client;

    private String content;

}
