package com.rishiqing.midware.user.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by  on 2017/6/27.Wallace
 */
public class UserTest {
    @Test
    public void test_userConstructor(){
        long id = 1L;
        String username = "user1";
        String password = "user1password";
        String realName = "用户1";

        User user = new User(id);
        assertEquals("构造方法：id", id, user.getId());

        user = new User(id, username, password, realName);
        assertEquals("构造方法：id, username, password, realName", id, user.getId());
        assertEquals("构造方法：id, username, password, realName", username, user.getUsername());
        assertEquals("构造方法：id, username, password, realName", password, user.getPassword());
        assertEquals("构造方法：id, username, password, realName", realName, user.getRealName());

    }
}
