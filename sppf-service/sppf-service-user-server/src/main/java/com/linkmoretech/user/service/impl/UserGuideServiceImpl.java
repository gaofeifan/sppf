package com.linkmoretech.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.linkmoretech.user.entity.UserGuide;
import com.linkmoretech.user.repository.UserGuideRepository;
import com.linkmoretech.user.service.UserGuideService;
import com.linkmoretech.user.vo.UserGuideResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 个人版版本管理
 * @author jhb
 * @Date 2019年6月27日 下午1:53:12
 * @Version 1.0
 */
@Service
@Slf4j
public class UserGuideServiceImpl implements UserGuideService {

    @Autowired
    UserGuideRepository userGuideRepository;

	@Override
	public List<UserGuideResponse> find(String language) {
		
		List<UserGuide> list = userGuideRepository.findAll(); 
		log.info("user guide list = {}",JSON.toJSON(list));
		Map<Long,List<UserGuide>> ugm = new HashMap<Long,List<UserGuide>>();
		List<UserGuide> ugs = null;
		for(UserGuide ug:list){
			ugs = ugm.get(ug.getParentId());
			if(ugs==null){
				ugs = new ArrayList<UserGuide>();
				ugm.put(ug.getParentId(), ugs);
			}
			ugs.add(ug);
		}
		if(ugm.isEmpty()) {
			return null;
		}
		return this.parse(ugm.get(0l), ugm,language);
	}
	
	private List<UserGuideResponse> parse(List<UserGuide> list,Map<Long,List<UserGuide>> ugm,String language){
		List<UserGuideResponse> trees = new ArrayList<UserGuideResponse>();
		UserGuideResponse tree = null;
		if(StringUtils.isNotBlank(language)){
			if(language.equals("zh")){
				for(UserGuide ug:list){
					tree = new UserGuideResponse();
					tree.setId(ug.getId());
					tree.setLevel(ug.getLevel());
					tree.setTitle(ug.getTitle());
					tree.setType(ug.getType());
					if(ug.getType()==0&&ugm.get(ug.getId())!=null){
						tree.setChildren(this.parse(ugm.get(ug.getId()), ugm,language)); 
					} else{
						tree.setUrl(ug.getUrl());
					}
					trees.add(tree);
				}
			}else if(language.equals("en")){
				for(UserGuide ug:list){
					tree = new UserGuideResponse();
					tree.setId(ug.getId());
					tree.setLevel(ug.getLevel());
					tree.setTitle(ug.getEnTitle());
					tree.setType(ug.getType());
					if(ug.getType()==0&&ugm.get(ug.getId())!=null){
						tree.setChildren(this.parse(ugm.get(ug.getId()), ugm,language)); 
					} else{
						tree.setUrl(ug.getUrl());
					}
					trees.add(tree);
				}
			}
		}else{
			for(UserGuide ug:list){
				tree = new UserGuideResponse();
				tree.setId(ug.getId());
				tree.setLevel(ug.getLevel());
				tree.setTitle(ug.getTitle());
				tree.setType(ug.getType());
				if(ug.getType()==0&&ugm.get(ug.getId())!=null){
					tree.setChildren(this.parse(ugm.get(ug.getId()), ugm,language)); 
				} else{
					tree.setUrl(ug.getUrl());
				}
				trees.add(tree);
			}
		}
		return trees;
	}
	
}
