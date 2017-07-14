package com.rishiqing.midware.user.dao;

import com.rishiqing.midware.user.model.User;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by  on 2017/6/21.Wallace
 */
public class UserDaoTest extends BaseDaoTest {

    public static UserDao dao = null;
    public static DatabaseDao dbDao = null;

    @BeforeClass
    public static void prepareMapper(){
        dao = session.getMapper(UserDao.class);
        dbDao = session.getMapper(DatabaseDao.class);
    }

    @AfterClass
    public static void cleanMapper(){
        dao = null;
        dbDao = null;
    }

    @Before
    @After
    public void clearDatabase(){
        dbDao.deleteAllTableData("user_role");
        dbDao.deleteAllTableData("user");
    }

    @Test
    public void test_saveUser(){
        String username = "user1",
                password = "user1password",
                realName = "用户1";
        User u = new User(username, password, realName);
        long result = dao.saveUser(u);

        assertEquals("保存新用户成功返回值为1", result, 1);
        assertNotNull("新增用户返回值填充id属性", u.getId());

        User dbUser = dao.getUserById(u.getId());

        assertEquals("新增用户username属性保存成功", dbUser.getUsername(), username);
        assertEquals("新增用户password属性保存成功", dbUser.getPassword(), password);
        assertEquals("新增用户realName属性保存成功", dbUser.getRealName(), realName);
    }

    @Test
    public void test_updateUser(){
        String username = "user1",
                password = "user1password",
                realName = "用户1",
                username2 = "user2",
                password2 = "user2password",
                realName2 = "用户2",
                password3 = "user2password3",
                realName3 = "用户3";
        //  1 初始用户
        User u = new User(username, password, realName);
        dao.saveUser(u);
        u = dao.getUserById(u.getId());
        long user1Id = u.getId();
        long user1Version = u.getVersion();

        //  2 使用updateUser更新用户，根据user的id进行更新
        User u2 = dao.getUserById(user1Id);
        u2.setUsername(username2);
        u2.setPassword(password2);
        u2.setRealName(realName2);
        dao.updateUser(u2);
        u2 = dao.getUserById(u2.getId());
        long user2Id = u2.getId();
        long user2Version = u2.getVersion();

        assertEquals("更新前后id是一样的", user1Id, user2Id);
        assertEquals("更新前后version是同步更新的", user1Version + 1, user2Version);
        assertEquals("更新用户username属性保存成功", u.getUsername(), username2);
        assertEquals("更新用户password属性保存成功", u.getPassword(), password2);
        assertEquals("更新用户realName属性保存成功", u.getRealName(), realName2);

        //  3 使用saveUser也可以更新用户，不可以根据id进行更新。只有当存在除id以外的唯一约束的情况下才可以进行更新
        User u3 = dao.getUserById(user2Id);
        //  username字段具有唯一约束，所以根据username字段更新
        u3.setPassword(password3);
        u3.setRealName(realName3);
        dao.saveUser(u3);
        u3 = dao.getUserById(u3.getId());
        long user3Id = u3.getId();
        long user3Version = u3.getVersion();

        assertEquals("使用save进行更新前后id是一样的", user2Id, user3Id);
        assertEquals("更新前后version是同步更新的", user2Version + 1, user3Version);
        assertEquals("更新用户username属性保存成功", u3.getUsername(), username2);
        assertEquals("更新用户password属性保存成功", u3.getPassword(), password3);
        assertEquals("更新用户realName属性保存成功", u3.getRealName(), realName3);

    }

    @Test
    public void test_deleteUser(){
        String username = "user1",
                password = "user1password",
                realName = "用户1";
        User u = new User(username, password, realName);
        dao.saveUser(u);

        User dbUser = dao.getUserById(u.getId());

        assertNotNull(dbUser);

        long result = dao.deleteUser(dbUser);

        assertEquals(result, 1);

        dbUser = dao.getUserById(u.getId());

        assertNull(dbUser);
    }

    @Test
    public void test_getAllUsers(){
        List<User> list = dao.getUserList();

        assertEquals(list.size(), 0);

        String username1 = "user1",
                password1 = "user1password",
                realName1 = "用户1",
                username2 = "user2",
                password2 = "user2password",
                realName2 = "用户2";
        User user1 = new User(username1, password1, realName1);
        User user2 = new User(username2, password2, realName2);
        dao.saveUser(user1);
        dao.saveUser(user2);

        list = dao.getUserList();

        assertEquals(list.size(), 2);
        assertEquals(list.get(0).toString(), user1.toString());
        assertEquals(list.get(1).toString(), user2.toString());
    }

    @Test
    public void test_getUserById(){
        String username1 = "user1",
                password1 = "user1password",
                realName1 = "用户1";
        User user1 = new User(username1, password1, realName1);
        dao.saveUser(user1);

        User dbUser = dao.getUserById(user1.getId());

        assertNotNull(dbUser);
        assertEquals(dbUser.toString(), user1.toString());
    }
}
