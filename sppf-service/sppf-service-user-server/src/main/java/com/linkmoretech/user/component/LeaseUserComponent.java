package com.linkmoretech.user.component;
import com.alibaba.fastjson.JSONObject;
import com.linkmoretech.parking.common.LicensePlateOutput;
import com.linkmoretech.user.entity.LeaseUser;
import com.linkmoretech.user.entity.LicensePlate;
import com.linkmoretech.user.entity.UserInfo;
import com.linkmoretech.user.repository.LeaseUserRepository;
import com.linkmoretech.user.repository.LicensePlateRepository;
import com.linkmoretech.user.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: alec
 * Description: 统一处理长租用户
 * @date: 16:15 2019-05-22
 */
@Component
@Slf4j
public class LeaseUserComponent {

    private static final String LEASE_PLACE_STOCK_TEMPLATE = "license_lease_%s";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    LicensePlateRepository licensePlateRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    LeaseUserRepository leaseUserRepository;

    @Transactional(rollbackFor = Exception.class)
    public void handlerLeaseUser(List<LicensePlateOutput> licensePlateOutputList) {
        /**
         * 用户服务接收到车区服务发送的固定用户车位数据，
         * 当前长租授权码对应的可以使用的车牌号
         *
         * 将车牌号和对应到长租授权码存储在redis队列中
         * */
        List<LeaseUser> leaseUserList = new ArrayList<>();
        licensePlateOutputList.forEach(licensePlateOutput -> {
            log.info("长租车位授权码{}", licensePlateOutput.getLeaseCode());
            licensePlateOutput.getLicensePlateNoList().forEach(plateNo -> {
                /**
                 * 以车牌号为key 对应的长租授权码加入可用队列
                 * */
                stringRedisTemplate.opsForSet().add(plateNo, licensePlateOutput.getLeaseCode());

                /**
                 * 查询车牌号对应用户
                 * */
                List<LicensePlate> licensePlateList = licensePlateRepository.findAllByPlateNoIn(licensePlateOutput.getLicensePlateNoList());

                List<String> userIdList = licensePlateList.stream().map(LicensePlate::getUserId).collect(Collectors.toList());
                /**
                 * 从系统中查询到绑定的用户ID
                 * */
                List<UserInfo> userInfos = userInfoRepository.findAllById(userIdList);

                /**
                 * 将用户ID， 用户授权码， 用户手机号存储
                 * 表示 该用户可以使用该授权码所指定的所有车位
                 * 如果需要设置车牌号和车位号的对应使用权 则需要中车区服务中设定
                 * */
                userInfos.forEach(userInfo -> leaseUserList.add(new LeaseUser(userInfo.getId(), licensePlateOutput.getLeaseCode(),
                        userInfo.getUserMobile())));
            });
        });
        leaseUserRepository.saveAll(leaseUserList);

    }

    @Transactional(rollbackFor = Exception.class)
    public void handlerRemoveLeaseUser(String leaseCode) {
        String licenseValue = (String) stringRedisTemplate.opsForHash().get(LEASE_PLACE_STOCK_TEMPLATE, leaseCode);
        try {
            List<String> licenseList = JSONObject.parseArray(licenseValue, String.class);
            if (licenseList != null && licenseList.size() > 0) {
                licenseList.forEach(license -> stringRedisTemplate.opsForSet().remove(license, leaseCode));
                stringRedisTemplate.opsForHash().delete(LEASE_PLACE_STOCK_TEMPLATE, leaseCode);
            }
            /**
             * 删除该授权码对应的长租用户记录
             * */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<String> getLeaseCode (String plateNo) {
        return stringRedisTemplate.opsForSet().members(plateNo);
    }
}
