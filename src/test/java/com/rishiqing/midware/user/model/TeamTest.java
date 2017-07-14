package com.rishiqing.midware.user.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by  on 2017/6/27.Wallace
 */
public class TeamTest {
    @Test
    public void test_teamConstructor(){
        long id = 1L;
        String name = "团队1";
        Team team = new Team(id);
        assertEquals("构造方法：id", id, team.getId());

        team = new Team(id, name);
        assertEquals("构造方法：id, name", id, team.getId());
        assertEquals("构造方法：id, name", name, team.getName());

        team = new Team(name);
        assertEquals("构造方法：name", name, team.getName());
    }

    @Test
    public void test_teamToString(){
        long id = 1L;
        String name = "团队1";
        Team team = new Team(id, name);

        String strName = team.toString();
        assertTrue("toString中包括id", strName.contains("\"id\":" + id));
        assertTrue("toString中包括name", strName.contains("\"name\":\"" + name + "\""));
    }
}
