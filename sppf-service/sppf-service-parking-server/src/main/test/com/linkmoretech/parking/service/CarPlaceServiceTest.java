package com.linkmoretech.parking.service;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.linkmoretech.common.util.JsonUtil;
import com.linkmoretech.parking.common.PlaceParkIdAndRangeInput;
import com.linkmoretech.parking.common.PlaceParkIdAndRangeOutput;
@RunWith(SpringRunner.class)
@SpringBootTest
public class CarPlaceServiceTest {

	@Autowired
	private CarPlaceService carPlaceService;
	@Test
	public void testFindByParkIdAndIdRange() {
		PlaceParkIdAndRangeInput input = new PlaceParkIdAndRangeInput();
		input.setParkId(824L);
		List<PlaceParkIdAndRangeOutput> list = carPlaceService.findByParkIdAndIdRange(input );
		System.out.println(JsonUtil.toJson(list));
	}

}
