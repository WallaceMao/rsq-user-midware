package com.rishiqing.midware.user.dao;

import com.rishiqing.midware.user.model.SuperUser;
import com.rishiqing.midware.user.model.SuperUserTest;
import com.rishiqing.midware.user.model.User;
import org.apache.log4j.Logger;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by  on 2017/7/10.Wallace
 */
public class UserSuperUserDaoTest extends BaseDaoTest {
    private static Logger logger = Logger.getLogger(SuperUserTest.class);

    public static SuperUserDao dao = null;
    public static DatabaseDao dbDao = null;
    public static UserDao userDao = null;

    public User user1;
    public User user2;
    public User user3;

    @BeforeClass
    public static void prepareMapper(){
        dao = session.getMapper(SuperUserDao.class);
        userDao = session.getMapper(UserDao.class);
        dbDao = session.getMapper(DatabaseDao.class);
    }

    @AfterClass
    public static void cleanMapper(){
        dao = null;
        userDao = null;
        dbDao = null;
    }

    @Before
    @After
    public void clearDatabase(){
        dbDao.setTableFieldNull("user", "super_user_id");
        dbDao.deleteAllTableData("super_user");
        dbDao.deleteAllTableData("user");
    }

    public void initUser(){
        SuperUserDaoTest test = new SuperUserDaoTest();
        User[] users = test.initUser(userDao);
        user1 = users[0];
        user2 = users[1];
        user3 = users[2];
    }

    @Test
    public void test_updateUserSetSuperUser(){
        this.initUser();
        SuperUser su = new SuperUser(user1, user2);
        long result = dao.saveSuperUser(su);

        assertEquals("superUser保存返回结果成功", 1, result);
        assertNotNull("superUser保存成功", su.getId());

        user1.setSuperUser(su);
        result = userDao.updateUserSetSuperUser(user1);
        assertEquals("superUser更新返回结果成功", 1, result);

        User dbUser = userDao.getUserSuperUser(user1);

        assertEquals("user的superUser属性更新成功", dbUser.getSuperUser().getId(), su.getId());

        user1.setSuperUser(null);
        result = userDao.updateUserSetSuperUser(user1);
        User dbUserWithoutTeam = userDao.getUserSuperUser(user1);

        assertEquals("更新用户的team为null之后，成功返回值为1", result, 1);
        assertEquals("更新用户的team为null之后，用户的基本属性需要存在", user1.getUsername(), dbUserWithoutTeam.getUsername());
        assertNull("更新用户的team为null之后，获取team为空", dbUserWithoutTeam.getTeam());

    }

    @Test
    public void test_getUserListBySuperUser(){
        this.initUser();
        SuperUser su = new SuperUser(user1, user2);
        long result = dao.saveSuperUser(su);

        assertEquals("superUser保存返回结果成功", 1, result);
        assertNotNull("superUser保存成功", su.getId());

        user1.setSuperUser(su);
        result = userDao.updateUserSetSuperUser(user1);
        assertEquals("superUser1更新返回结果成功", 1, result);

        user2.setSuperUser(su);
        result = userDao.updateUserSetSuperUser(user2);
        assertEquals("superUser2更新返回结果成功", 1, result);

        user3.setSuperUser(su);
        result = userDao.updateUserSetSuperUser(user3);
        assertEquals("superUser3更新返回结果成功", 1, result);

        List<User> userList = userDao.getUserListBySuperUser(su);
        assertEquals("userList大小为3", 3, userList.size());
        assertEquals("userList中第一个为user1", user1.getId(), userList.get(0).getId());
        assertEquals("userList中第二个为user1", user2.getId(), userList.get(1).getId());
        assertEquals("userList中第三个为user1", user3.getId(), userList.get(2).getId());
    }

    @Test
    public void test_getUserSuperUser(){
        this.initUser();
        SuperUser su = new SuperUser(user1, user2);
        long result = dao.saveSuperUser(su);

        assertEquals("superUser保存返回结果成功", 1, result);
        assertNotNull("superUser保存成功", su.getId());

        user1.setSuperUser(su);
        result = userDao.updateUserSetSuperUser(user1);
        assertEquals("superUser更新返回结果成功", 1, result);

        User dbUser = userDao.getUserSuperUser(user1);

        assertEquals("获取superUser结果成功", su.getId(), dbUser.getSuperUser().getId());
    }

    @Test
    public void test_getSuperUserUserList(){
//        保存SuperUser
        this.initUser();
        SuperUser su = new SuperUser(user1, user2);
        long result = dao.saveSuperUser(su);

        assertEquals("superUser保存返回结果成功", 1, result);
        assertNotNull("superUser保存成功", su.getId());

//        保存用户信息
        String username = "user_su1",
                password = "user_su1password",
                realName = "用户超用户1",
                username2 = "user_su2",
                password2 = "user_su2password",
                realName2 = "用户超用户2";
        User user = new User(username, password, realName);
        result = userDao.saveUser(user);

        assertEquals("保存新用户成功返回值为1", 1, result);

        User user2 = new User(username2, password2, realName2);
        result = userDao.saveUser(user2);

        assertEquals("保存新用户2成功返回值为1", result, 1);

        user.setSuperUser(su);
        result = userDao.updateUserSetSuperUser(user);

        assertEquals("更新用户的super user之后，成功返回值为1", result, 1);

        user2.setSuperUser(su);
        result = userDao.updateUserSetSuperUser(user2);

        assertEquals("更新用户2的super user之后，成功返回值为1", result, 1);

        SuperUser dbSuperUser = dao.getSuperUserUserList(su);

        assertEquals("在team中添加新用户1和新用户2之后，superUserList中包含两个用户", 2, dbSuperUser.getUserList().size());
        assertEquals("superUserList中第一个用户为user1", dbSuperUser.getUserList().get(0).getId(), user.getId());
        assertEquals("superUserList中第二个用户为user2", dbSuperUser.getUserList().get(1).getId(), user2.getId());
    }

    @Test
    public void test_addSuperUserUser(){
        this.initUser();
        SuperUser o1 = new SuperUser(user1, user2);
        long result = dao.saveSuperUser(o1);

        assertEquals("保存新超用户1成功返回值为1", result, 1);
        assertNotNull("新增超用户1返回值填充id属性", o1.getId());

        String username = "user_su1",
                password = "user_su1password",
                realName = "用户_超用户1";
        User u = new User(username, password, realName);
        u.setSuperUser(o1);
        result = dao.addSuperUserUser(u);

        User dbUser = userDao.getUserSuperUser(u);

        assertEquals("保存有团队的新用户成功返回值为1", 1, result);
        assertEquals("新增团队用户含有超用户属性", dbUser.getSuperUser().getId(), o1.getId());
    }
}
