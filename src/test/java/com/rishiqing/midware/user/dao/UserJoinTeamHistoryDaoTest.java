package com.rishiqing.midware.user.dao;

import com.rishiqing.midware.user.model.*;
import org.apache.log4j.Logger;
import org.junit.*;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by  on 2017/7/10.Wallace
 */
public class UserJoinTeamHistoryDaoTest extends BaseDaoTest {
    private static Logger logger = Logger.getLogger(UserJoinTeamHistoryDaoTest.class);

    public static DatabaseDao dbDao = null;
    public static UserJoinTeamHistoryDao dao = null;
    public static TeamDao teamDao = null;
    public static SuperUserDao superUserDao = null;
    public static UserDao userDao = null;

    public User user1;
    public User user2;
    public User user3;

    public SuperUser superUser;

    public Team team;

    @BeforeClass
    public static void prepareMapper(){
        dao = session.getMapper(UserJoinTeamHistoryDao.class);
        userDao = session.getMapper(UserDao.class);
        teamDao = session.getMapper(TeamDao.class);
        superUserDao = session.getMapper(SuperUserDao.class);
        dbDao = session.getMapper(DatabaseDao.class);
    }

    @AfterClass
    public static void cleanMapper(){
        dao = null;
        userDao = null;
        teamDao = null;
        superUserDao = null;
        dbDao = null;
    }

    @Before
    @After
    public void clearDatabase(){
        dbDao.deleteAllTableData("user_join_team_history");
        dbDao.setTableFieldNull("user", "super_user_id");
        dbDao.deleteAllTableData("super_user");
        dbDao.deleteAllTableData("user");
        dbDao.deleteAllTableData("team");
    }

    public void initUser(){
        SuperUserDaoTest test = new SuperUserDaoTest();
        User[] users = test.initUser(userDao);
        user1 = users[0];
        user2 = users[1];
        user3 = users[2];

        superUser = new SuperUser(user1, user2);
        superUserDao.saveSuperUser(superUser);

        team  = new Team("测试团队1");
        teamDao.saveTeam(team);
    }

    @Test
    public void test_saveUserJoinTeamHistory(){
        this.initUser();
        Date now = new Date();
        UserJoinTeamHistory history = new UserJoinTeamHistory(user1, superUser, team);
        history.setJoinTime(now);
        long result = dao.saveUserJoinTeamHistory(history);

        assertEquals("userJoinTeamHistory保存返回结果成功", 1, result);
        assertNotNull("userJoinTeamHistory保存成功", history.getId());
    }

    @Test
    public void test_updateUserSetSuperUser(){
        this.initUser();
        Date now = new Date();
        long nowMills = now.getTime() / 1000 * 1000;
        UserJoinTeamHistory history = new UserJoinTeamHistory(user1, superUser, team);
        long result = dao.saveUserJoinTeamHistory(history);

        assertEquals("userJoinTeamHistory保存返回结果成功", 1, result);
        assertNotNull("userJoinTeamHistory保存成功", history.getId());

        history.setJoinTime(now);
        result = dao.updateUserJoinTeamHistory(history);
        assertEquals("userJoinTeamHistory保存返回结果成功", 1, result);

        UserJoinTeamHistory dbHistory = dao.getUserJoinTeamHistoryById(history.getId());

        assertEquals("userJoinTeamHistory的joinTime属性更新成功", nowMills, dbHistory.getJoinTime().getTime());

        history.setJoinTime(null);
        result = dao.updateUserJoinTeamHistory(history);
        assertEquals("userJoinTeamHistory保存返回结果成功", 1, result);

        dbHistory = dao.getUserJoinTeamHistoryById(history.getId());

        assertNull("userJoinTeamHistory的joinTime属性设置为null更新成功",dbHistory.getJoinTime());

        history.setLeaveTime(now);
        result = dao.updateUserJoinTeamHistory(history);
        assertEquals("userJoinTeamHistory保存返回结果成功", 1, result);

        dbHistory = dao.getUserJoinTeamHistoryById(history.getId());

        assertEquals("userJoinTeamHistory的leaveTime属性更新成功", nowMills, dbHistory.getLeaveTime().getTime());

        history.setLeaveTime(null);
        result = dao.updateUserJoinTeamHistory(history);
        assertEquals("userJoinTeamHistory保存返回结果成功", 1, result);

        dbHistory = dao.getUserJoinTeamHistoryById(history.getId());

        assertNull("userJoinTeamHistory的leaveTime属性设置为null更新成功",dbHistory.getLeaveTime());
    }

    @Test
    public void test_deleteUserJoinTeamHistory(){
        this.initUser();
        Date now = new Date();
        UserJoinTeamHistory history = new UserJoinTeamHistory(user1, superUser, team);
        history.setJoinTime(now);
        long result = dao.saveUserJoinTeamHistory(history);
        long dbId = history.getId();

        assertEquals("userJoinTeamHistory保存返回结果成功", 1, result);
        assertNotNull("userJoinTeamHistory保存成功", dbId);

        dao.deleteUserJoinTeamHistory(history);

        assertEquals("userJoinTeamHistory 删除返回结果成功", result, 1);

        UserJoinTeamHistory dbHistory = dao.getUserJoinTeamHistoryById(dbId);

        assertNull("userJoinTeamHistory 删除成功", dbHistory);
    }

    @Test
    public void test_getUserJoinTeamHistoryList(){
        this.initUser();
        Date now = new Date();
        UserJoinTeamHistory history = new UserJoinTeamHistory(user1, superUser, team);
        history.setJoinTime(now);
        long result = dao.saveUserJoinTeamHistory(history);

        assertEquals("userJoinTeamHistory保存返回结果成功", 1, result);
        assertNotNull("userJoinTeamHistory保存成功", history.getId());

        UserJoinTeamHistory history2 = new UserJoinTeamHistory(user2, superUser, team);
        history2.setJoinTime(now);
        result = dao.saveUserJoinTeamHistory(history2);

        assertEquals("userJoinTeamHistory保存返回结果成功", 1, result);
        assertNotNull("userJoinTeamHistory保存成功", history.getId());

        List<UserJoinTeamHistory> list = dao.getUserJoinTeamHistoryList();

        assertEquals("UserJoinTeamHistory list.size为2", 2, list.size());
        assertEquals("userJoinTeamHistory list.get(0) 为history", history.getId(), list.get(0).getId());
        assertEquals("userJoinTeamHistory list.get(1) 为history2", history2.getId(), list.get(1).getId());
    }

    @Test
    public void test_getUserJoinTeamHistoryById(){
        this.initUser();
        Date now = new Date();
        UserJoinTeamHistory history = new UserJoinTeamHistory(user1, superUser, team);
        history.setJoinTime(now);
        long result = dao.saveUserJoinTeamHistory(history);
        long historyId = history.getId();

        assertEquals("userJoinTeamHistory保存返回结果成功", 1, result);
        assertNotNull("userJoinTeamHistory保存成功", historyId);

        UserJoinTeamHistory dbHistory = dao.getUserJoinTeamHistoryById(historyId);

        assertEquals("根据id获取UserJoinTeamHistory成功", historyId, dbHistory.getId());
    }

    @Test
    public void test_getUserJoinTeamHistoryUser(){
        this.initUser();
        Date now = new Date();
        UserJoinTeamHistory history = new UserJoinTeamHistory(user1, superUser, team);
        history.setJoinTime(now);
        long result = dao.saveUserJoinTeamHistory(history);
        long historyId = history.getId();

        assertEquals("userJoinTeamHistory保存返回结果成功", 1, result);
        assertNotNull("userJoinTeamHistory保存成功", historyId);

        UserJoinTeamHistory dbHistory = dao.getUserJoinTeamHistoryUser(history);

        assertEquals("获取UserJoinTeamHistory user成功", user1.getId(), dbHistory.getUser().getId());
    }

    @Test
    public void test_getUserJoinTeamHistorySuperUser(){
        this.initUser();
        Date now = new Date();
        UserJoinTeamHistory history = new UserJoinTeamHistory(user1, superUser, team);
        history.setJoinTime(now);
        long result = dao.saveUserJoinTeamHistory(history);
        long historyId = history.getId();

        assertEquals("userJoinTeamHistory保存返回结果成功", 1, result);
        assertNotNull("userJoinTeamHistory保存成功", historyId);

        UserJoinTeamHistory dbHistory = dao.getUserJoinTeamHistorySuperUser(history);

        assertEquals("获取UserJoinTeamHistory user成功", superUser.getId(), dbHistory.getSuperUser().getId());
    }

    @Test
    public void test_getUserJoinTeamHistoryTeam(){
        this.initUser();
        Date now = new Date();
        UserJoinTeamHistory history = new UserJoinTeamHistory(user1, superUser, team);
        history.setJoinTime(now);
        long result = dao.saveUserJoinTeamHistory(history);
        long historyId = history.getId();

        assertEquals("userJoinTeamHistory保存返回结果成功", 1, result);
        assertNotNull("userJoinTeamHistory保存成功", historyId);

        UserJoinTeamHistory dbHistory = dao.getUserJoinTeamHistoryTeam(history);

        assertEquals("获取UserJoinTeamHistory user成功", team.getId(), dbHistory.getTeam().getId());
    }

    @Test
    public void test_user_getUserUserJoinTeamHistoryList(){
        this.initUser();
        Date now = new Date();
        UserJoinTeamHistory history = new UserJoinTeamHistory(user1, superUser, team);
        history.setJoinTime(now);
        long result = dao.saveUserJoinTeamHistory(history);

        assertEquals("userJoinTeamHistory保存返回结果成功", 1, result);
        assertNotNull("userJoinTeamHistory保存成功", history.getId());

        User dbUser = userDao.getUserUserJoinTeamHistoryList(user1);

        assertEquals("userDao.getUserUserJoinTeamHistoryList获取到UserJoinTeamHistoryList size为1", 1, dbUser.getUserJoinTeamHistoryList().size());
        assertEquals("userDao.getUserUserJoinTeamHistoryList获取到UserJoinTeamHistoryList", history.getId(), dbUser.getUserJoinTeamHistoryList().get(0).getId());
    }

    @Test
    public void test_superUser_getSuperUserUserJoinTeamHistoryList(){
        this.initUser();
        Date now = new Date();
        UserJoinTeamHistory history = new UserJoinTeamHistory(user1, superUser, team);
        history.setJoinTime(now);
        long result = dao.saveUserJoinTeamHistory(history);

        assertEquals("userJoinTeamHistory保存返回结果成功", 1, result);
        assertNotNull("userJoinTeamHistory保存成功", history.getId());

        UserJoinTeamHistory history2 = new UserJoinTeamHistory(user2, superUser, team);
        history2.setJoinTime(now);
        result = dao.saveUserJoinTeamHistory(history2);

        assertEquals("userJoinTeamHistory保存返回结果成功", 1, result);
        assertNotNull("userJoinTeamHistory保存成功", history.getId());

        SuperUser dbSuperUser = superUserDao.getSuperUserUserJoinTeamHistoryList(superUser);

        assertEquals("superUserDao.getSuperUserUserJoinTeamHistoryList size为1", 2, dbSuperUser.getUserJoinTeamHistoryList().size());
        assertEquals("superUserDao.getSuperUserUserJoinTeamHistoryList", history.getId(), dbSuperUser.getUserJoinTeamHistoryList().get(0).getId());
        assertEquals("superUserDao.getSuperUserUserJoinTeamHistoryList", history2.getId(), dbSuperUser.getUserJoinTeamHistoryList().get(1).getId());

    }

    @Test
    public void test_team_getTeamUserJoinTeamHistoryList(){
        this.initUser();
        Date now = new Date();
        UserJoinTeamHistory history = new UserJoinTeamHistory(user1, superUser, team);
        history.setJoinTime(now);
        long result = dao.saveUserJoinTeamHistory(history);

        assertEquals("userJoinTeamHistory保存返回结果成功", 1, result);
        assertNotNull("userJoinTeamHistory保存成功", history.getId());

        UserJoinTeamHistory history2 = new UserJoinTeamHistory(user2, superUser, team);
        history2.setJoinTime(now);
        result = dao.saveUserJoinTeamHistory(history2);

        assertEquals("userJoinTeamHistory保存返回结果成功", 1, result);
        assertNotNull("userJoinTeamHistory保存成功", history.getId());

        Team dbTeam = teamDao.getTeamUserJoinTeamHistoryList(team);

        assertEquals("superUserDao.getSuperUserUserJoinTeamHistoryList size为1", 2, dbTeam.getUserJoinTeamHistoryList().size());
        assertEquals("superUserDao.getSuperUserUserJoinTeamHistoryList", history.getId(), dbTeam.getUserJoinTeamHistoryList().get(0).getId());
        assertEquals("superUserDao.getSuperUserUserJoinTeamHistoryList", history2.getId(), dbTeam.getUserJoinTeamHistoryList().get(1).getId());

    }
}
