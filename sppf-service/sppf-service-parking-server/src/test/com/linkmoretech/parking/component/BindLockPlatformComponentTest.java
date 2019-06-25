package com.linkmoretech.parking.component;
import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.parking.ParkingServerApplicationTest;
import com.linkmoretech.parking.entity.CarPark;
import com.linkmoretech.parking.entity.Coordinate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @Author: alec
 * Description:
 * @date: 11:19 2019-05-21
 */
@Component
public class BindLockPlatformComponentTest extends ParkingServerApplicationTest {

    @Autowired
    BindLockPlatformComponent bindLockPlatformComponent;

    @Test
    public void bindGroup() throws CommonException {

        CarPark carPark = new CarPark();
        carPark.setParkName("京东锁住");
        carPark.setCityCode("010");

        Coordinate coordinate = new Coordinate();
        coordinate.setLongitude(new BigDecimal(0.0001));
        coordinate.setLatitude(new BigDecimal(1.121212));
        bindLockPlatformComponent.bindGroup(carPark, coordinate);

    }
}
