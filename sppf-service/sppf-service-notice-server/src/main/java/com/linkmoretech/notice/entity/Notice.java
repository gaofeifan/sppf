package com.linkmoretech.notice.entity;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "n_notice")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;
    
    private String uuid;
    
    private Long userId;
    
    private String version;
    
    private String content;
    
    private String title;
    
    private String pushType;
    
    private String pushName;
    
    private Integer state;
    
    private String privateField;
    
    private Integer agingType;
    
    private Date createTime;
    
    private Date EndTime;
    
}
