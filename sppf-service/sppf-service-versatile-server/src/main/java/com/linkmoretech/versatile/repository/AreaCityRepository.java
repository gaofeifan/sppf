package com.linkmoretech.versatile.repository;

import com.linkmoretech.versatile.entity.AreaCity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: alec
 * @Description:
 * @date: 8:32 PM 2019/4/29
 */
public interface AreaCityRepository extends JpaRepository<AreaCity, Long> {

    /**
     * 根据城市编码查询
     * @param cityCode 城市编码
     * @return 返回城市
     * */
    AreaCity findByCityCodeEquals(String cityCode);

    /**
     * 根据父ID查询所属列表
     * @param id 父ID
     * @return 城市区域列表
     * */
    List<AreaCity> getAllByParentId(Long id);

    /**
     * 删除父ID下的子集
     * @param parentId 父ID
     * */
    void deleteAllByParentId(Long parentId);
}
