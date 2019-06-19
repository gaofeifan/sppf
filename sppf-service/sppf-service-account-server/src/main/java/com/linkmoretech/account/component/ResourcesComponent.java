package com.linkmoretech.account.component;

import com.linkmoretech.account.entity.Resources;
import com.linkmoretech.account.resposity.ResourcesRepository;
import com.linkmoretech.account.vo.response.ResourcesAllResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: alec
 * Description: 资源公共操作
 * @date: 11:44 2019-05-30
 */
@Component
@Slf4j
public class ResourcesComponent {

    @Autowired
    ResourcesRepository resourcesRepository;

    public List<ResourcesAllResponse> getResource(String clientId) {

        /**
         * 得到根节点
         * */
        List<Resources> resourcesList = resourcesRepository.getAllByClientId(clientId);

        List<ResourcesAllResponse> resourcesAllResponses = resourcesList.stream().map(resources -> {
            ResourcesAllResponse resourcesAllResponse = new ResourcesAllResponse();
            BeanUtils.copyProperties(resources, resourcesAllResponse);
            return resourcesAllResponse;
        }).collect(Collectors.toList());
        List<ResourcesAllResponse> rootResourceList = resourcesAllResponses.stream().filter(resources ->
                resources.getParentId() == null).collect(Collectors.toList());
        List<ResourcesAllResponse> childrenResourceList = resourcesAllResponses.stream().filter(resources ->
                resources.getParentId() != null).collect(Collectors.toList());

        List<ResourcesAllResponse> returnResources = new ArrayList<>();
        rootResourceList.forEach(resourcesAllResponse -> returnResources.add(getChildrenList(childrenResourceList,
                resourcesAllResponse)) );
        return returnResources;
    }

    private ResourcesAllResponse getChildrenList(List<ResourcesAllResponse> resourcesList, ResourcesAllResponse
            resourcesAllResponse) {
        resourcesList.forEach(resources -> {
            log.info("resource {}" , resources);
            if (resources.getParentId() != null && resources.getParentId().equals(resourcesAllResponse.getId())) {
                if (resourcesAllResponse.getChildren() == null) {
                    resourcesAllResponse.setChildren(new ArrayList<>());
                }
                resourcesAllResponse.getChildren().add(getChildrenList(resourcesList, resources));
            }
        });
        return resourcesAllResponse;
    }
}
