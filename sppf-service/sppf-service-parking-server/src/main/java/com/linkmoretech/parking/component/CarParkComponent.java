package com.linkmoretech.parking.component;

import com.linkmoretech.common.enums.ResponseCodeEnum;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.parking.entity.CarPark;
import com.linkmoretech.parking.entity.CarPlace;
import com.linkmoretech.parking.enums.CarParkTypeEnum;
import com.linkmoretech.parking.enums.CarPlaceTypeEnum;
import com.linkmoretech.parking.repository.CarParkRepository;
import com.linkmoretech.parking.repository.CarPlaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

/**
 * @Author: alec
 * Description: 车场/车位操作公共组件层
 * @date: 18:52 2019-05-21
 */
@Component
@Slf4j
public class CarParkComponent {

    @Autowired
    CarParkRepository carParkRepository;

    @Autowired
    CarPlaceRepository carPlaceRepository;


    /**
     * 更新车场类型
     * */
    public void updateCarParkType (CarPark carPark) {

        long tempPlaceCount = carPlaceRepository.countByParkIdAndPlaceType(carPark.getId(),
                CarPlaceTypeEnum.TEMP_PLACE.getCode());
        long totalPlaceCount = carPlaceRepository.countByParkId(carPark.getId());

        log.info("车场 {}, 车位总数  {} ,共有临停车位{} 个", carPark.getParkName(), totalPlaceCount, tempPlaceCount);
        carPark.setParkType(CarParkTypeEnum.COMBINATION.getCode());
        if (tempPlaceCount == 0) {
            carPark.setParkType(CarParkTypeEnum.RENTAL.getCode());
        }
        if (tempPlaceCount == totalPlaceCount || totalPlaceCount == 0) {
            carPark.setParkType(CarParkTypeEnum.TEMPORARY.getCode());
        }
        carPark.setUpdateTime(new Date());
        carParkRepository.save(carPark);
    }

    /**
     * 根据车场ID 查询车场
     * */
    public CarPark getCarPark(Long carParkId) throws CommonException {
        log.info("车场ID {}", carParkId);
        Optional<CarPark> optional = carParkRepository.findById(carParkId);
        if (! optional.isPresent()) {
            throw new CommonException(ResponseCodeEnum.ERROR, "未找到相关车场");
        }
        return optional.get();
    }

    /**
     * 根据车位ID 查询车位
     * */
    public CarPlace getCarPlace(Long carPlaceId) throws CommonException {
        CarPlace carPlace = carPlaceRepository.findById(carPlaceId).get();
        if (carPlace.getId() == null) {
            throw new CommonException(ResponseCodeEnum.ERROR, "车位数据不存在");
        }
        return carPlace;
    }
}
