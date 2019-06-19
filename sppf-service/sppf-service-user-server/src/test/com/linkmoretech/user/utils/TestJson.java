package com.linkmoretech.user.utils;

import com.alibaba.fastjson.JSONObject;
import com.linkmoretech.parking.common.LicensePlateOutput;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 15:36 2019-05-23
 */
public class TestJson {

    @Test
    public void testJson(){

        LicensePlateOutput licensePlateOutput = new LicensePlateOutput();
        licensePlateOutput.setLeaseCode("232234sfsdfdsfsdf");
        List<String> c = new ArrayList<>();
        c.add("京A222222");
        licensePlateOutput.setLicensePlateNoList(c);

        List<LicensePlateOutput> licensePlateOutputs = new ArrayList<>();
        licensePlateOutputs.add(licensePlateOutput);

        String json = JSONObject.toJSONString(licensePlateOutputs);
        System.out.println(json);

        List<LicensePlateOutput> plateOutput = JSONObject.parseArray(json, LicensePlateOutput.class);
        System.out.println(plateOutput.size());
    }
}
