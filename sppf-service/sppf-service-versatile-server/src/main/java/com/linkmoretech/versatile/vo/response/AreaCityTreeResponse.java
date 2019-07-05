package com.linkmoretech.versatile.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 城市下拉框
 * @Author: alec
 * @Description:
 * @date: 10:40 AM 2019/5/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaCityTreeResponse {

    private Long id;

    private String value;

    private String label;

    private List<AreaCityTreeResponse> children;
}
