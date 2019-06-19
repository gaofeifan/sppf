package com.linkmoretech.account.component;

import com.linkmoretech.account.entity.Resources;
import com.linkmoretech.account.entity.RolesResources;
import com.linkmoretech.account.entity.User;
import com.linkmoretech.account.entity.UserRoles;
import com.linkmoretech.account.resposity.ResourcesRepository;
import com.linkmoretech.account.resposity.RolesResourcesRepository;
import com.linkmoretech.account.resposity.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: alec
 * Description:
 * @date: 20:45 2019-06-18
 */
@Component
public class UserComponent {

    @Autowired
    UserRolesRepository userRolesRepository;

    @Autowired
    RolesResourcesRepository rolesResourcesRepository;

    @Autowired
    ResourcesRepository resourcesRepository;

    public  List<Resources>  getResourceIdListByUser(User user) {
        List<UserRoles> userRolesList = userRolesRepository.getAllByUserIdIn(Arrays.asList(user.getId()));
        List<Long> rolesIdList = userRolesList.stream().map(UserRoles::getRolesId).collect(Collectors.toList());
        List<RolesResources> rolesResources = rolesResourcesRepository.getAllByRolesIdIn(rolesIdList);
        List<Long> resourcesId = rolesResources.stream().map(RolesResources::getResourcesId).collect(Collectors.toList());
        List<Resources> resourcesList = resourcesRepository.getAllByIdIn(resourcesId);
        return resourcesList;
    }
}
