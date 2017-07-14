package com.rishiqing.midware.user.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by  on 2017/6/27.Wallace
 */
public class SuperUserTest {

    @Test
    public void test_superUserConstructor(){

        long id = 1L;
        long mainUserId = 22L;
        long defaultLoginUserId = 33L;

        SuperUser su1 = new SuperUser();
        assertNotNull("构造方法：", su1);

        SuperUser su2 = new SuperUser(id);
        assertEquals("构造方法：id", id, su2.getId());

        User mainUser = new User(mainUserId);
        User defaultLoginUser = new User(defaultLoginUserId);
        SuperUser su3 = new SuperUser(mainUser, defaultLoginUser);

        assertEquals("构造方法：id, mainUser, defaultLoginUser, mainUser相同", mainUser, su3.getMainUser());
        assertEquals("构造方法：id, mainUser, defaultLoginUser, default login user相同", defaultLoginUser, su3.getDefaultLoginUser());

    }

    @Test
    public void test_superUserToString(){
        long id = 1L;

        SuperUser su = new SuperUser(id);
        assertEquals("构造方法：id", id, su.getId());

        String strSuperUser = su.toString();
        assertTrue("toString中包括id", strSuperUser.contains("\"id\":" + id));
    }
}
