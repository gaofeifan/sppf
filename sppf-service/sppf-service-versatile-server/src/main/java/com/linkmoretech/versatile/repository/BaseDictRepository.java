package com.linkmoretech.versatile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.linkmoretech.versatile.entity.BaseDict;

/**
 * @Author: jiaohanbin
 * @Description:
 * @date: 8:32 PM 2019/4/29
 */
public interface BaseDictRepository extends JpaRepository<BaseDict, Long> {
    
}
