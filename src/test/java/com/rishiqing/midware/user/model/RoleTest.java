package com.rishiqing.midware.user.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by  on 2017/6/27.Wallace
 */
public class RoleTest {
    @Test
    public void test_roleConstructor(){
        long id = 1L;
        long version = 0L;
        String auth = "AUTH1";
        Role role = new Role(id, auth);
        assertEquals("Role的构造方法中含有id", role.getId(), id);
        assertEquals("Role的构造方法中含有version", role.getVersion(), version);
        assertEquals("Role的构造方法中含有auth", role.getAuthority(), auth);
    }
}
