package com.linkmoretech.versatile.service.impl;

import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.versatile.entity.AreaCity;
import com.linkmoretech.versatile.repository.AreaCityRepository;
import com.linkmoretech.versatile.service.AreaCityService;
import com.linkmoretech.versatile.vo.request.AreaCityCreateRequest;
import com.linkmoretech.versatile.vo.response.AreaCityListResponse;
import com.linkmoretech.versatile.vo.response.AreaCityTreeResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: alec
 * @Description:
 * @date: 8:36 PM 2019/4/29
 */
@Service
@Slf4j
public class AreaCityServiceImpl implements AreaCityService {

    @Autowired
    AreaCityRepository areaCityRepository;

    @Override
    public void create(AreaCityCreateRequest areaCityCreateRequest) throws CommonException {
        AreaCity areaCity = new AreaCity();
        BeanUtils.copyProperties(areaCityCreateRequest, areaCity);
        if (areaCityCreateRequest.getParentId() != null) {
           AreaCity parentCity = areaCityRepository.findById(areaCityCreateRequest.getParentId()).get();
           areaCity.setParentId(parentCity.getId());
           checkCityCode(parentCity.getCityCode(), areaCity.getCityCode());
        }
        AreaCity existArea = areaCityRepository.findByCityCodeEquals(areaCityCreateRequest.getCityCode());
        if (existArea != null) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, "城市编码已存在");
        }
        areaCityRepository.save(areaCity);
    }

    @Override
    public List<AreaCityListResponse> list(Long id) {
        List<AreaCity> areaCityList = areaCityRepository.getAllByParentId(id);
        return areaCityList.stream().map(areaCity -> {
            return new AreaCityListResponse(areaCity.getId(),areaCity.getCityCode(),areaCity.getCityName());
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        areaCityRepository.deleteAllByParentId(id);
        areaCityRepository.deleteById(id);
    }

    @Override
    public List<AreaCityTreeResponse> tree() {
        List<AreaCity> areaCities = areaCityRepository.findAll(Sort.by(Sort.Order.asc("cityCode")));
        List<AreaCityTreeResponse> treeResponses = new ArrayList<>();
        areaCities.forEach(areaCity -> {
            if (areaCity.getParentId() == null) {
                AreaCityTreeResponse node = new AreaCityTreeResponse(areaCity.getId(),areaCity.getCityCode(),
                        areaCity.getCityName(), new ArrayList<>());
                findChildren(node, areaCities);
                treeResponses.add(node);
            }
        });
        return treeResponses;
    }

    @Override
    public List<String> getAllCityCode(String cityCode) {
        List<String> areaCodes = new ArrayList<>();
        AreaCity areaCity = areaCityRepository.findByCityCodeEquals(cityCode);
        log.info("cityCode {} parentID {}, name {}", areaCity.getCityCode(), areaCity.getParentId(), areaCity.getCityName());
        if (areaCity == null) {
            return areaCodes;
        }
        areaCodes = getAllCityCode(areaCity, areaCodes);
        log.info("city length {}", areaCodes.size());
        Collections.reverse(areaCodes);
        return areaCodes;
    }

    @Override
    public String getCityName(String cityCode) {
        AreaCity areaCity = areaCityRepository.findByCityCodeEquals(cityCode);
        if (areaCity == null) {
            return null;
        }
        String areaName = areaCity.getCityName();
        return areaName;
    }

    private void checkCityCode(String parentCode, String code) throws CommonException {
        int step = 3;
        if (code.length() - parentCode.length() != step) {
            throw new CommonException(ResponseCodeEnum.ERROR, "城市编码格式不正确");
        }
        if (code.indexOf(parentCode) != 0) {
            throw new CommonException(ResponseCodeEnum.ERROR, "城市编码格式不正确");
        }
    }

    /**
     * 递归遍历取得节点下的所有子节点
     * */
    private void findChildren(AreaCityTreeResponse treeResponse, List<AreaCity> areaCities) {
        areaCities.forEach(areaCity -> {
            log.info("node {} ", treeResponse.getValue());
            if (areaCity.getParentId() != null && areaCity.getParentId().equals(treeResponse.getId())) {
                AreaCityTreeResponse childrenTreeNode = new AreaCityTreeResponse(areaCity.getId(), areaCity.getCityCode(),
                        areaCity.getCityName(), new ArrayList<>());
                treeResponse.getChildren().add(childrenTreeNode);
                findChildren(childrenTreeNode, areaCities);
            }
        });
    }

    /**
     * 递归遍历所有父节点
     * */
    private List<String> getAllCityCode(AreaCity areaCity, List<String> codeList) {
        codeList.add(areaCity.getCityCode());
        if (areaCity.getParentId() == null) {
            return codeList;
        } else {
            AreaCity parentAreaCity = areaCityRepository.findById(areaCity.getParentId()).get();
            return getAllCityCode(parentAreaCity, codeList);
        }
    }
}
