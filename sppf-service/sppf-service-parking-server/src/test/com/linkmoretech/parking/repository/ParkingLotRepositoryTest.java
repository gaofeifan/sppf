package com.linkmoretech.parking.repository;

import com.linkmoretech.parking.ParkingServerApplicationTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: alec
 * @Description:
 * @date: 11:53 AM 2019/4/23
 */
@Component
public class ParkingLotRepositoryTest extends ParkingServerApplicationTest {


    @Test
    public void findAllByCityCodeEquals() {
        String cityCode = "010000";

    }
}