package com.rishiqing.midware.user.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by  on 2017/6/27.Wallace
 */
public class UserRoleTest {
    @Test
    public void test_userRoleConstructor(){
        UserRole ur = new UserRole();
        assertNotNull(ur);

        long userId = 1L;
        String username = "user1";
        String password = "user1password";
        String realName = "用户1";
        User user = new User(userId, username, password, realName);
        ur.setUser(user);

        long roleId = 1L;
        String authority = "AUTH1";
        Role role = new Role(roleId, authority);
        ur.setRole(role);

        assertEquals("setUser之后user存在", userId, ur.getUser().getId());
        assertEquals("setRole之后role存在", roleId, ur.getRole().getId());
    }

    @Test
    public void test_userRoleToString(){
        UserRole ur = new UserRole();

        long userId = 1L;
        String username = "user1";
        String password = "user1password";
        String realName = "用户1";
        User user = new User(userId, username, password, realName);
        ur.setUser(user);

        long roleId = 1L;
        String authority = "AUTH1";
        Role role = new Role(roleId, authority);
        ur.setRole(role);

        System.out.println(ur.toString());
        assertTrue("toString包含user", ur.toString().contains("\"username\":\"" + username + "\""));
        assertTrue("toString包含role", ur.toString().contains("\"authority\":\"" + authority + "\""));
    }
}
