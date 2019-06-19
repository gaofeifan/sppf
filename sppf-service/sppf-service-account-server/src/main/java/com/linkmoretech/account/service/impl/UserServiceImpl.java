package com.linkmoretech.account.service.impl;

import com.linkmoretech.account.component.AccountComponent;
import com.linkmoretech.account.component.UserComponent;
import com.linkmoretech.account.entity.*;
import com.linkmoretech.account.enums.EnableStatusEnum;
import com.linkmoretech.account.resposity.*;
import com.linkmoretech.account.service.UserService;
import com.linkmoretech.account.vo.request.SearchRequest;
import com.linkmoretech.account.vo.request.UserCreateRequest;
import com.linkmoretech.account.vo.request.UserRolesRequest;
import com.linkmoretech.account.vo.response.LoginSuccessResponse;
import com.linkmoretech.account.vo.response.UserInfoResponse;
import com.linkmoretech.account.vo.response.UserListResponse;
import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.common.vo.PageDataResponse;
import com.linkmoretech.common.vo.PageSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: alec
 * Description:
 * @date: 14:53 2019-05-31
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRolesRepository userRolesRepository;

    @Autowired
    UserDataRolesRepository userDataRolesRepository;

    @Autowired
    RolesResourcesRepository rolesResourcesRepository;

    @Autowired
    ResourcesRepository resourcesRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserComponent userComponent;

    @Override
    public void createUser(UserCreateRequest userCreateRequest) throws CommonException {
        log.debug("参数信息 {}", userCreateRequest);
        List<UserRolesRequest> userRoles = userCreateRequest.getRoles();
        Map<String, List<User>> userMap = new HashMap<>();
        List<UserRoles> saveUserRolesList = new ArrayList<>();
        List<UserDataRoles> saveUserDataRoles = new ArrayList<>();
        /**
         * 验证帐号是否存在
         * */
        List<User> userList = userRepository.getAllByUserNameOrMobile(userCreateRequest.getUserName(),
                userCreateRequest.getMobile());
        if (userList != null) {
            /**
             * 对用户客户端分组
             * */
            userMap = userList.stream().collect(Collectors.groupingBy(User::getClientId));
        }
        log.info("存在数据{}" , userMap);
        for (UserRolesRequest userRolesRequest : userRoles) {
            User user = new User();
            Date currentDate = new Date();
            BeanUtils.copyProperties(userCreateRequest, user);
            if (userMap.get(userRolesRequest.getClientId()) != null) {
                throw new CommonException(ResponseCodeEnum.ERROR, "用户帐号或手机号在 帐号中已存在");
            }
            user.setClientId(userRolesRequest.getClientId());
            user.setStatus(EnableStatusEnum.ENABLED.getCode());
            user.setCreateTime(currentDate);
            user.setUpdateTime(currentDate);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User returnUser = userRepository.save(user);
            log.info("保存用户成功 {}", returnUser);
            /**
             * 存储用户角色
             * */
            List<UserRoles> userRolesTempList = userRolesRequest.getRolesId().stream().map(rolesId -> {
                UserRoles userRolesTemp = new UserRoles();
                userRolesTemp.setUserId(returnUser.getId());
                userRolesTemp.setRolesId(rolesId);
                return userRolesTemp;
            }).collect(Collectors.toList());
            saveUserRolesList.addAll(userRolesTempList);
            if (userCreateRequest.getCarParkIds() != null) {
                List<UserDataRoles> userDataRolesTempList = userCreateRequest.getCarParkIds().stream().map(carParkId -> {
                    UserDataRoles userDataRolesTemp = new UserDataRoles();
                    userDataRolesTemp.setCarParkId(carParkId);
                    userDataRolesTemp.setUserId(returnUser.getId());
                    return userDataRolesTemp;
                }).collect(Collectors.toList());
                saveUserDataRoles.addAll(userDataRolesTempList);
            }
        }
        userRolesRepository.saveAll(saveUserRolesList);
        userDataRolesRepository.saveAll(saveUserDataRoles);
    }

    @Override
    public PageDataResponse<UserListResponse> search(PageSearchRequest<SearchRequest> pageSearchRequest) {
        String[] sortField = new String[]{"lastLoginTime"};
        Sort sort = Sort.by(Sort.Direction.ASC, sortField);
        Pageable pageable  = PageRequest.of(pageSearchRequest.getPageNo(), pageSearchRequest
                .getPageSize(), sort);
        User searchUser = new User();
        searchUser.setClientId(pageSearchRequest.getCondition().getClientId());
        Example<User> example = Example.of(searchUser);
        Page<User> page = userRepository.findAll(example, pageable);
        PageDataResponse<UserListResponse> pageDataResponse = new PageDataResponse();
        pageDataResponse.setTotal(page.getTotalElements());

        List<User> userList = page.getContent();
        List<Long> userIdList = userList.stream().map(User::getId).collect(Collectors.toList());

        List<UserRoles> userRoles = userRolesRepository.getAllByUserIdIn(userIdList);
        Map<Long, List<String>> userRolesMap = new HashMap<>();
        if (userRoles != null) {
            Map<Long, List<UserRoles>> userRolesMapTemp = userRoles.stream().collect(Collectors.groupingBy(UserRoles::getUserId));
            for (Map.Entry<Long, List<UserRoles>> map : userRolesMapTemp.entrySet()) {
               List<Long> rolesIds = map.getValue().stream().map(UserRoles::getRolesId).collect(Collectors.toList());
               List<Roles> rolesList = rolesRepository.getAllByIdIn(rolesIds);
               if (rolesList != null) {
                   userRolesMap.put(map.getKey(), rolesList.stream().map(Roles::getRolesName).collect(Collectors.toList()));
               }
            }
        }
        List<UserListResponse> userListResponses = userList.stream().map(user -> {
            UserListResponse userListResponse = new UserListResponse();
            BeanUtils.copyProperties(user, userListResponse);
            userListResponse.setRolesList(userRolesMap.get(user.getId()));
            return userListResponse;
        }).collect(Collectors.toList());
        pageDataResponse.setData(userListResponses);
        return pageDataResponse;
    }

    @Override
    public UserInfoResponse getUserInfo(String username, String clientId) {

        User user = userRepository.getUserByClientIdAndUserName(clientId, username);
        log.info("username {}, client {}, user {}", username, clientId, user);

        List<Resources> resourcesList = userComponent.getResourceIdListByUser(user);

        List<String> menuList = resourcesList.stream().map(Resources :: getRouterName).collect(Collectors.toList());

        return new UserInfoResponse(user.getUserName(), menuList);
    }

    @Override
    public void updateUserState(Long userId, Integer userState) throws CommonException {

        EnableStatusEnum enableStatusEnum = EnableStatusEnum.getStatus(userState);

        if (enableStatusEnum == null) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, "状态码不正确");
        }
        Optional<User> optional = userRepository.findById(userId);
        if (!optional.isPresent()) {
            throw new CommonException(ResponseCodeEnum.PARAMS_ERROR, "用户不存在");
        }
        User user = optional.get();
        user.setStatus(enableStatusEnum.getCode());
        userRepository.save(user);
    }
}