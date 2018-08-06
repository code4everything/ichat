package org.code4everything.ichat.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author pantao
 * @since 2018-08-06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testGetUserInfo() {
    }

    @Test
    public void testLogin() {
        System.out.println("user " + userService.login("tao@zhazhapan.com", "pantao"));
    }
}
