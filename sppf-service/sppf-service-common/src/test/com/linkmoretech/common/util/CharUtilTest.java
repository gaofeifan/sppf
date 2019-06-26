package com.linkmoretech.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @Author: alec
 * Description:
 * @date: 11:29 2019-06-26
 */
@Slf4j
public class CharUtilTest {


    @Test
    public void change() {

       log.info("测试字符转化");

       String str = "A-0-01_——121212；";

       log.info("转化字符串{}", str);

       String newStr =  CharUtil.change(str);

       log.info("转化后{}" , newStr);
    }
}
