package com.rishiqing.midware.user.dao;

import com.rishiqing.midware.user.model.Team;
import com.rishiqing.midware.user.model.User;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by  on 2017/6/26.Wallace
 */
public class UserTeamDaoTest extends BaseDaoTest {
    public static UserDao userDao;
    public static TeamDao teamDao;
    public static DatabaseDao dbDao;

    @BeforeClass
    public static void prepareMapper(){
        userDao = session.getMapper(UserDao.class);
        teamDao = session.getMapper(TeamDao.class);
        dbDao = session.getMapper(DatabaseDao.class);
    }

    @AfterClass
    public static void cleanMapper(){
        userDao = null;
        teamDao = null;
        dbDao = null;
    }

    @Before
    @After
    public void clearDatabase(){
        dbDao.deleteAllTableData("user");
        dbDao.deleteAllTableData("team");
    }

    /**
     * 更新用户，设置用户的团队字段为某个团队，然后再将用户的团队设置为null
     */
    @Test
    public void test_updateUserSetTeam(){
//        保存团队
        String name = "团队1";
        Team team = new Team();
        team.setName(name);
        long result = teamDao.saveTeam(team);

        assertEquals("保存新团队成功返回值为1", result, 1);
        assertNotNull("新增团队返回值填充id属性", team.getId());

//        保存用户信息
        String username = "user1",
                password = "user1password",
                realName = "用户1";
        User user = new User(username, password, realName);
        result = userDao.saveUser(user);

        assertEquals("保存新用户成功返回值为1", result, 1);
        assertNotNull("新增用户返回值填充id属性", user.getId());

        user.setTeam(team);
        result = userDao.updateUserSetTeam(user);
        User dbUser = userDao.getUserTeam(user);

        assertEquals("更新用户的team之后，成功返回值为1", result, 1);
        assertEquals("更新用户的team之后，用户的基本属性需要存在", username, dbUser.getUsername());
        assertNotNull("更新用户的team之后，获取team不能为空", dbUser.getTeam());
        assertEquals("更新用户的team之后，用户的team为设置的team", dbUser.getTeam().getId(), user.getTeam().getId());

        dbUser.setTeam(null);
        result = userDao.updateUserSetTeam(dbUser);
        User dbUserWithoutTeam = userDao.getUserTeam(user);

        assertEquals("更新用户的team为null之后，成功返回值为1", result, 1);
        assertEquals("更新用户的team为null之后，用户的基本属性需要存在", username, dbUserWithoutTeam.getUsername());
        assertNull("更新用户的team为null之后，获取team为空", dbUser.getTeam());
    }

    @Test
    public void test_getUserTeam(){
//        保存用户信息
        String username = "user1",
                password = "user1password",
                realName = "用户1";
        User user = new User(username, password, realName);
        long result = userDao.saveUser(user);

        assertEquals("保存新用户成功返回值为1", result, 1);
        assertNotNull("新增用户返回值填充id属性", user.getId());

        User dbUser = userDao.getUserTeam(user);
        assertEquals("保持用户信息成功，设置用户的username", user.getUsername(), username);
        assertEquals("保持用户信息成功，设置用户的password", user.getPassword(), password);
        assertEquals("保持用户信息成功，设置用户的realName", user.getRealName(), realName);
        assertNull("未设置team，user的team为null", dbUser.getTeam());

        //        保存团队
        String name = "团队1";
        Team team = new Team();
        team.setName(name);
        result = teamDao.saveTeam(team);

        assertEquals("保存新团队成功返回值为1", result, 1);
        assertNotNull("新增团队返回值填充id属性", team.getId());

        user.setTeam(team);
        result = userDao.updateUserSetTeam(user);
        User dbUserWithTeam = userDao.getUserTeam(user);

        assertEquals("更新用户的team之后，成功返回值为1", result, 1);
        assertEquals("更新用户的team之后，设置用户的username", dbUserWithTeam.getUsername(), username);
        assertEquals("更新用户的team之后，设置用户的password", dbUserWithTeam.getPassword(), password);
        assertEquals("更新用户的team之后，设置用户的realName", dbUserWithTeam.getRealName(), realName);
        assertNotNull("更新用户的team之后，获取team不能为空", dbUserWithTeam.getTeam());
        assertEquals("更新用户的team之后，用户的team为设置的team", team.getId(), dbUserWithTeam.getTeam().getId());
    }

    @Test
    public void test_getTeamUsers(){
//        保存团队
        String name = "团队1";
        Team team = new Team();
        team.setName(name);
        long result = teamDao.saveTeam(team);

        assertEquals("保存新团队成功返回值为1", result, 1);
        assertNotNull("新增团队返回值填充id属性", team.getId());

//        保存用户信息
        String username = "user1",
                password = "user1password",
                realName = "用户1",
                username2 = "user2",
                password2 = "user2password",
                realName2 = "用户2";
        User user = new User(username, password, realName);
        result = userDao.saveUser(user);

        assertEquals("保存新用户成功返回值为1", result, 1);

        User user2 = new User(username2, password2, realName2);
        result = userDao.saveUser(user2);

        assertEquals("保存新用户2成功返回值为1", result, 1);

        user.setTeam(team);
        result = userDao.updateUserSetTeam(user);

        assertEquals("更新用户的team之后，成功返回值为1", result, 1);

        user2.setTeam(team);
        result = userDao.updateUserSetTeam(user2);

        assertEquals("更新用户2的team之后，成功返回值为1", result, 1);

        team = teamDao.getTeamUserList(team);

        assertEquals("在team中添加新用户1和新用户2之后，teamUsers中包含两个用户", 2, team.getUserList().size());
        assertEquals("teamUsers中第一个用户为user1", team.getUserList().get(0).getId(), user.getId());
        assertEquals("teamUsers中第二个用户为user2", team.getUserList().get(1).getId(), user2.getId());
    }

    @Test
    public void test_addTeamUser(){
        String name1 = "团队1";
        Team o1 = new Team(name1);
        long result = teamDao.saveTeam(o1);

        assertEquals("保存新团队1成功返回值为1", result, 1);
        assertNotNull("新增团队1返回值填充id属性", o1.getId());

        String username = "user1",
                password = "user1password",
                realName = "用户1";
        User u = new User(username, password, realName);
        u.setTeam(o1);
        result = teamDao.addTeamUser(u);

        User dbUser = userDao.getUserTeam(u);

        assertEquals("保存有团队的新用户成功返回值为1", result, 1);
        assertEquals("新增团队用户含有团队属性", dbUser.getTeam().getId(), o1.getId());
    }

}
