package com.linkmoretech.versatile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.linkmoretech.versatile.entity.BaseDict;

/**
 * @Author: jiaohanbin
 * @Description:
 * @date: 8:32 PM 2019/4/29
 */
public interface BaseDictRepository extends JpaRepository<BaseDict, Long> {
	@Query(value ="select d.* from v_base_dict d left join v_base_dict_group dg on d.group_id = dg.id where dg.code = ?1 order by d.order_index ", nativeQuery = true)
	List<BaseDict> findListByCode(String code);
}
