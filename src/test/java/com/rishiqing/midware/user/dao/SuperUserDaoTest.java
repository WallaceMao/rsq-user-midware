package com.rishiqing.midware.user.dao;

import com.rishiqing.midware.user.model.SuperUser;
import com.rishiqing.midware.user.model.SuperUserTest;
import com.rishiqing.midware.user.model.User;
import org.apache.log4j.Logger;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by  on 2017/6/21.Wallace
 */
public class SuperUserDaoTest extends BaseDaoTest {
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

    private User[] initUser(){
        return initUser(userDao);
    }

    public User[] initUser(UserDao userDao){
        String username1 = "user1",
                password1 = "user1password",
                realName1 = "用户1",
                username2 = "user2",
                password2 = "user2password",
                realName2 = "用户2",
                username3 = "user3",
                password3 = "user3password",
                realName3 = "用户3";
        user1 = new User(username1, password1, realName1);
        long result = userDao.saveUser(user1);

        user2 = new User(username2, password2, realName2);
        result = userDao.saveUser(user2);

        user3 = new User(username3, password3, realName3);
        result = userDao.saveUser(user3);

        return new User[]{user1, user2, user3};
    }

    @Test
    public void test_saveSuperUser(){

        this.initUser();
        SuperUser su = new SuperUser(user1, user2);
        long result = dao.saveSuperUser(su);

        assertEquals("保存新超用户成功返回值为1", result, 1);
        assertNotNull("新增超用户返回值填充id属性", su.getId());

        SuperUser dbSuperUser = dao.getSuperUserById(su.getId());
        User mainUser = dao.getSuperUserMainUser(dbSuperUser).getMainUser();
        User loginUser = dao.getSuperUserDefaultLoginUser(dbSuperUser).getDefaultLoginUser();

        assertNotNull("新增超用户id属性保存成功", dbSuperUser.getId());
        assertEquals("新增超用户version属性保存成功，初始值为0", 0L, dbSuperUser.getVersion());
        assertNotNull("新增超用户dateCreated属性保存成功", dbSuperUser.getDateCreated());
        assertEquals("新增超用户mainUser属性保存成功", user1.getId(), mainUser.getId());
        assertEquals("新增超用户defaultLoginUser属性保存成功", user2.getId(), loginUser.getId());
    }

    @Test
    public void test_updateSuperUser(){
        this.initUser();

        SuperUser su = new SuperUser(user1, user2);
        long result = dao.saveSuperUser(su);

        SuperUser su1 = dao.getSuperUserById(su.getId());
        long user1Id = su1.getId();
        long user1Version = su1.getVersion();
        long o1LastUpdated = su1.getLastUpdated().getTime();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error(e);
        }

        //  2 使用updateUser更新用户，根据user的id进行更新
        SuperUser su2 = dao.getSuperUserById(user1Id);
        su2.setMainUser(user3);
        su2.setDefaultLoginUser(user3);
        dao.updateSuperUser(su2);

        su2 = dao.getSuperUserById(su2.getId());
        long user2Id = su2.getId();
        long user2Version = su2.getVersion();
        long o2LastUpdated = su2.getLastUpdated().getTime();
        User mainUser2 = dao.getSuperUserMainUser(su2).getMainUser();
        User loginUser2 = dao.getSuperUserDefaultLoginUser(su2).getDefaultLoginUser();

        assertEquals("更新超用户前后id是一样的", user1Id, user2Id);
        assertEquals("更新超用户前后version是同步更新的", user1Version + 1, user2Version);
        assertEquals("更新超用户mainUser属性保存成功", user3.getId(), mainUser2.getId());
        assertEquals("更新超用户defaultLoginUser属性保存成功", user3.getId(), loginUser2.getId());
        assertTrue("更新超用户lastUpdated属性保存成功", o2LastUpdated > o1LastUpdated);

    }

    @Test
    public void test_deleteSuperUser(){
        this.initUser();
        SuperUser su = new SuperUser(this.user1, this.user2);
        long result = dao.saveSuperUser(su);

        SuperUser dbSuperUser = dao.getSuperUserById(su.getId());

        assertNotNull(dbSuperUser);

        result = dao.deleteSuperUser(dbSuperUser);

        assertEquals(result, 1);

        dbSuperUser = dao.getSuperUserById(su.getId());

        assertNull(dbSuperUser);
    }

    @Test
    public void test_getSuperUserList(){
        this.initUser();
        SuperUser su = new SuperUser(user1, user2);
        long result = dao.saveSuperUser(su);

        assertEquals("保存新超用户1成功返回值为1", result, 1);
        assertNotNull("新增超用户1返回值填充id属性", su.getId());

        SuperUser su2 = new SuperUser(user2, user3);
        result = dao.saveSuperUser(su2);

        assertEquals("保存新超用户2成功返回值为1", result, 1);
        assertNotNull("新增超用户2返回值填充id属性", su2.getId());

        List<SuperUser> suList = dao.getSuperUserList();

        assertEquals("新增团队name属性保存成功", suList.size(), 2);
        assertEquals("新增团队name属性保存成功", suList.get(0).getId(), su.getId());
        assertEquals("新增团队name属性保存成功", suList.get(1).getId(), su2.getId());
    }

    @Test
    public void test_getSuperUserById(){
        this.initUser();
        SuperUser su = new SuperUser(this.user1, this.user2);
        long result = dao.saveSuperUser(su);

        assertEquals(result, 1);

        SuperUser dbSuperUser = dao.getSuperUserById(su.getId());

        assertNotNull(dbSuperUser);
    }

    @Test
    public void test_getMainUser(){
        this.initUser();
        SuperUser su = new SuperUser(this.user1, this.user2);
        long result = dao.saveSuperUser(su);

        assertEquals(result, 1);

        User mainUser = dao.getSuperUserMainUser(su).getMainUser();

        assertEquals("获取到的mainUser应该一致", this.user1.getId(), mainUser.getId());
    }

    @Test
    public void test_getDefaultLoginUser(){
        this.initUser();
        SuperUser su = new SuperUser(this.user1, this.user2);
        long result = dao.saveSuperUser(su);

        assertEquals(result, 1);

        User loginUser = dao.getSuperUserDefaultLoginUser(su).getDefaultLoginUser();

        assertEquals("获取到的mainUser应该一致", this.user2.getId(), loginUser.getId());
    }
}
