package com.linkmoretech.station.service.impl;

import com.linkmoretech.station.entity.ParkingData;
import com.linkmoretech.station.repository.ParkingDataRepository;
import com.linkmoretech.station.service.RealTimeQueryService;
import com.linkmoretech.station.vo.response.ParkingRealDataResponse;
import com.linkmoretech.station.vo.response.RealDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: alec
 * Description:
 * @date: 15:26 2019-07-15
 */
@Slf4j
@Service
public class RealTimeQueryServiceImpl implements RealTimeQueryService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    ParkingDataRepository parkingDataRepository;

    @Override
    public RealDataResponse realTime() {

        RealDataResponse realDataResponse = new RealDataResponse();
        List<ParkingData> parkingData = parkingDataRepository.findAll(Sort.by("sort"));
        Map<String, String> valueMap =  (Map)stringRedisTemplate.opsForHash().entries("carCount");
        log.info("value {}", valueMap);
        final  Long[] tempCount = {0L};
        final  Long[] fixedCount = {0L};
        final Integer[] totalFixedPlace = {0};
        final Integer[] totalTempPlace = {0};
        List<ParkingRealDataResponse> parkingRealDataResponses = parkingData.stream().map(data -> {
            ParkingRealDataResponse dataResponse = new ParkingRealDataResponse();
            dataResponse.setCode(data.getParkCode());
            dataResponse.setName(data.getParkName());
            dataResponse.setFixPlaceTotal(data.getFixedNum());
            dataResponse.setPlaceTotal(data.getTempNum());

            totalFixedPlace[0] += data.getFixedNum();
            totalTempPlace[0] += data.getTempNum();

            String tempKey = data.getParkCode() + "_0";

            String fixedKey = data.getParkCode() + "_1";

            log.info("value key {} - {}",tempKey,  valueMap.get(tempKey));
            Long tempRealCount = 0L;
            Long fixRealCount = 0L;
            String tempRealValue = valueMap.get(tempKey);
            String fixRealValue = valueMap.get(fixedKey);

            if (!StringUtils.isEmpty(tempRealValue)) {
                tempRealCount = Long.valueOf(tempRealValue);
            }
            if (!StringUtils.isEmpty(fixRealValue)) {
                fixRealCount = Long.valueOf(fixRealValue);
            }
            dataResponse.setNowFixTotal(fixRealCount);
            dataResponse.setNowTempTotal(tempRealCount);

            tempCount[0] += tempRealCount;
            fixedCount[0] += fixRealCount;

            return dataResponse;
        }).collect(Collectors.toList());
        realDataResponse.setParkingRealDataResponseList(parkingRealDataResponses);
        realDataResponse.setNowTempTotal(tempCount[0]);
        realDataResponse.setNowFixTotal(fixedCount[0]);
        realDataResponse.setFixPlaceTotal(totalFixedPlace[0]);
        realDataResponse.setPlaceTotal(totalTempPlace[0]);
        return realDataResponse;
    }
}
