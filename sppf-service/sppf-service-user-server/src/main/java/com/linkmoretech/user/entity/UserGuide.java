package com.linkmoretech.user.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户反馈信息
 * @author jhb
 * @Date 2019年6月27日 上午10:48:07
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_user_guide")
public class UserGuide {
	@Id
    @GeneratedValue
    private Long id;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //层级
    private Integer level;
    //父节点
    private Long parentId;
    //状态
    private Integer status;
    //标题
    private String title;
    //英文标题
    private String enTitle;
    //类型
    private Integer type;
    //排序
    private Integer orderIndex;
    //url
    private String url;

}