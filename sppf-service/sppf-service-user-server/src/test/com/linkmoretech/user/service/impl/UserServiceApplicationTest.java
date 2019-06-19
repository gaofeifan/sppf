package com.linkmoretech.user.service.impl;

import com.linkmoretech.common.exception.CommonException;
import com.linkmoretech.user.UserServerApplicationTest;
import com.linkmoretech.user.common.vo.UserInfoInput;
import com.linkmoretech.user.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: alec
 * @Description:
 * @date: 下午5:00 2019/4/11
 */
@Slf4j
@Component
public class UserServiceApplicationTest extends UserServerApplicationTest {

    @Autowired
    UserInfoService userInfoService;

    @Test
    public void findDetailByUserId() {
        log.info("test unbindWeChat");
    }

    @Test
    public void searchList() {
        log.info("test unbindWeChat");
    }

    @Test
    public void editUser() {
        log.info("test unbindWeChat");
    }

    @Test
    public void updateUserState() {
        log.info("test unbindWeChat");
    }

    @Test
    public void updateUserMobile() {
        log.info("test unbindWeChat");
    }

    @Test
    public void bindWeChat() {
        log.info("test unbindWeChat");
    }

    @Test
    public void unbindWeChat() {
        log.info("test unbindWeChat");
    }

    @Test
    public void createUser() throws CommonException {

        UserInfoInput userInfoInput = new UserInfoInput();
        userInfoInput.setOpenId("wx-alec0234-192232322");
        userInfoInput.setUserMobile("13370140226");
        userInfoInput.setUserName(userInfoInput.getUserMobile());
        userInfoInput.setWeChatBindTime(new Date());
        userInfoInput.setWeChatBindState(1);
        userInfoService.createUser(userInfoInput);
    }

}