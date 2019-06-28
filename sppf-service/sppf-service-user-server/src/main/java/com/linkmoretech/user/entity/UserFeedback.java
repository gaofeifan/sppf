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
 * @Date 2019年6月27日 上午10:45:02
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_user_feedback")
public class UserFeedback {
	@Id
    @GeneratedValue
    private Long id;
    //用户ID
    private String userId;
    //反馈内容
    private String content;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

}