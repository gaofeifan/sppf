package com.linkmoretech.account.service.impl;

import com.linkmoretech.account.entity.Roles;
import com.linkmoretech.account.entity.RolesResources;
import com.linkmoretech.account.resposity.RolesRepository;
import com.linkmoretech.account.resposity.RolesResourcesRepository;
import com.linkmoretech.account.service.RolesService;
import com.linkmoretech.account.vo.request.RolesAddResourceRequest;
import com.linkmoretech.account.vo.request.RolesCreateRequest;
import com.linkmoretech.account.vo.request.RolesEditRequest;
import com.linkmoretech.account.vo.response.RolesListResponse;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: alec
 * Description:
 * @date: 14:42 2019-05-30
 */
@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    RolesResourcesRepository rolesResourcesRepository;

    @Override
    public Long create(RolesCreateRequest rolesCreateRequest) throws CommonException {
        Roles roles = rolesRepository.findByRolesNameAndClientId(rolesCreateRequest.getRolesName(),
                rolesCreateRequest.getClientId());
        if (roles != null) {
            throw new CommonException(ResponseCodeEnum.ERROR, "角色名称已存在");
        }
        roles = new Roles();
        BeanUtils.copyProperties(rolesCreateRequest, roles);
        Roles returnRoles = rolesRepository.save(roles);
        return returnRoles.getId();
    }

    @Override
    public List<RolesListResponse> list(String clientId) {
        List<Roles> rolesList = rolesRepository.getAllByClientId(clientId);
        if (rolesList == null) {
            return new ArrayList<>();
        }
        return rolesList.stream().map(roles -> {
            RolesListResponse rolesListResponse = new RolesListResponse();
            BeanUtils.copyProperties(roles, rolesListResponse);
            return rolesListResponse;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Long> getResourcesIdByRolesId(Long rolesId) {
        List<RolesResources> rolesResources = rolesResourcesRepository.getAllByRolesId(rolesId);
        return rolesResources.stream().map(RolesResources::getResourcesId).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void addResourcesForRoles(RolesAddResourceRequest rolesAddResourceRequest) throws CommonException {
        Optional<Roles> roles = rolesRepository.findById(rolesAddResourceRequest.getRolesId());
        if (!roles.isPresent()) {
            throw new CommonException(ResponseCodeEnum.ERROR, "角色不已存在");
        }
        rolesResourcesRepository.deleteAllByRolesId(rolesAddResourceRequest.getRolesId());
        List<RolesResources> rolesResourcesList = rolesAddResourceRequest.getResourcesIdList().stream().map(resourceId -> {
            RolesResources rolesResources = new RolesResources();
            rolesResources.setResourcesId(resourceId);
            rolesResources.setRolesId(rolesAddResourceRequest.getRolesId());
            return rolesResources;
        }).collect(Collectors.toList());

        rolesResourcesRepository.saveAll(rolesResourcesList);
    }

    @Override
    public Map<String, List<RolesListResponse>> getRolesGroup() {
        List<Roles> rolesList = rolesRepository.findAll();
        List<RolesListResponse> responseList = rolesList.stream().map(roles -> {
            RolesListResponse rolesListResponse = new RolesListResponse();
            BeanUtils.copyProperties(roles, rolesListResponse);
            return rolesListResponse;
        }).collect(Collectors.toList());
        Map<String, List<RolesListResponse>> responseMap = responseList.stream()
                .collect(Collectors.groupingBy(RolesListResponse::getClientId));
        return responseMap;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void remove(Long rolesId) throws CommonException {
        Optional<Roles> roles = rolesRepository.findById(rolesId);
        if (!roles.isPresent()) {
            throw new CommonException(ResponseCodeEnum.ERROR, "角色不已存在");
        }
        rolesResourcesRepository.deleteAllByRolesId(rolesId);
        rolesRepository.deleteById(rolesId);
    }

    @Override
    public Long update(RolesEditRequest rolesEditRequest) throws CommonException {
        Optional<Roles> optional = rolesRepository.findById(rolesEditRequest.getId());
        if (!optional.isPresent()) {
            throw new CommonException(ResponseCodeEnum.ERROR, "角色不已存在");
        }
        Roles roles = optional.get();
        roles.setRolesName(rolesEditRequest.getRolesName());
        roles.setContent(rolesEditRequest.getContent());
        rolesRepository.save(roles);
        return roles.getId();
    }
}
