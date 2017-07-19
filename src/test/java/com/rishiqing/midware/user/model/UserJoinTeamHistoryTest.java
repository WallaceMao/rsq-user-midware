package com.rishiqing.midware.user.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by  on 2017/6/27.Wallace
 */
public class UserJoinTeamHistoryTest {
    @Test
    public void test_UserJoinTeamHistoryConstructor(){
        long userId = 1L,
                superUserId = 2L,
                teamId = 3L;
        String teamName = "团队1";
        Team team = new Team(teamId, teamName);
        SuperUser su = new SuperUser(superUserId);
        User u = new User(userId);
        u.setSuperUser(su);

        UserJoinTeamHistory history = new UserJoinTeamHistory(u, su, team);
        assertEquals("构造方法：user", u, history.getUser());
        assertEquals("构造方法：superUser", su, history.getSuperUser());
        assertEquals("构造方法：team", team, history.getTeam());
    }

    @Test
    public void test_userJoinTeamHistoryToString(){
        long userId = 1L,
                superUserId = 2L,
                teamId = 3L;
        String teamName = "团队1";
        Team team = new Team(teamId, teamName);
        SuperUser su = new SuperUser(superUserId);
        User u = new User(userId);
        u.setSuperUser(su);

        UserJoinTeamHistory history = new UserJoinTeamHistory(u, su, team);
        String str = history.toString();
        System.out.println("str:-----" + str);
        assertTrue("toString中包括user", str.contains("\"user\":"));
        assertTrue("toString中包括superUser", str.contains("\"superUser\":"));
        assertTrue("toString中包括team", str.contains("\"team\":"));
    }
}
