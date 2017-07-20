package com.rishiqing.midware.user.service;

import com.rishiqing.midware.user.dao.BaseDaoTest;
import com.rishiqing.midware.user.dao.DatabaseDao;
import com.rishiqing.midware.user.dao.SuperUserDao;
import com.rishiqing.midware.user.dao.UserDao;
import com.rishiqing.midware.user.model.SuperUser;
import com.rishiqing.midware.user.model.SuperUserTest;
import com.rishiqing.midware.user.model.User;
import com.rishiqing.midware.user.service.impl.MidUserServiceImpl;
import org.apache.log4j.Logger;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by  on 2017/7/20.Wallace
 */
public class MidUserServiceGetterTest extends BaseDaoTest{
    private static Logger logger = Logger.getLogger(SuperUserTest.class);

    public static SuperUserDao superUserdao = null;
    public static DatabaseDao dbDao = null;
    public static UserDao userDao = null;

    public User user1InSuperUserA;
    public User user2InSuperUserA;
    public User user3InSuperUserA;
    public User userWithoutSuperUser;
    public SuperUser superUserA;

    @BeforeClass
    public static void prepareMapper(){
        superUserdao = session.getMapper(SuperUserDao.class);
        userDao = session.getMapper(UserDao.class);
        dbDao = session.getMapper(DatabaseDao.class);
    }

    @AfterClass
    public static void cleanMapper(){
        superUserdao = null;
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
        return initUser(userDao, superUserdao);
    }

    public User[] initUser(UserDao userDao, SuperUserDao superUserDao){
        String username1 = "user1InSuperUserA",
                password1 = "user1InSuperUserApassword",
                realName1 = "用户1",
                username2 = "user2InSuperUserA",
                password2 = "user2InSuperUserApassword",
                realName2 = "用户2",
                username3 = "user3InSuperUserA",
                password3 = "user3InSuperUserApassword",
                realName3 = "用户3",
                username4 = "user4",
                password4 = "user4password",
                realName4 = "用户4";
        user1InSuperUserA = new User(username1, password1, realName1);
        long result = userDao.saveUser(user1InSuperUserA);

        user2InSuperUserA = new User(username2, password2, realName2);
        result = userDao.saveUser(user2InSuperUserA);

        user3InSuperUserA = new User(username3, password3, realName3);
        result = userDao.saveUser(user3InSuperUserA);

        userWithoutSuperUser = new User(username4, password4, realName4);
        result = userDao.saveUser(userWithoutSuperUser);

        superUserA = new SuperUser(user1InSuperUserA, user2InSuperUserA);
        result = superUserDao.saveSuperUser(superUserA);

        user1InSuperUserA.setSuperUser(superUserA);
        result = userDao.updateUserSetSuperUser(user1InSuperUserA);

        user2InSuperUserA.setSuperUser(superUserA);
        result = userDao.updateUserSetSuperUser(user2InSuperUserA);

        user3InSuperUserA.setSuperUser(superUserA);
        result = userDao.updateUserSetSuperUser(user3InSuperUserA);

        return new User[]{user1InSuperUserA, user2InSuperUserA, user3InSuperUserA};
    }

    @Test
    public void test_getSiblingUsers(){
        this.initUser();

        MidUserService service = new MidUserServiceImpl();
        List<User> list = null;

        list = service.getSiblingUsers(null);

        assertNull("getSiblingUsers(null) 返回值应该为null", list);

        list = service.getSiblingUsers(user1InSuperUserA);

        assertEquals("siblingUsers应该有3个", list.size(), 3);
        assertEquals("siblingUsers第1个为user1InSuperUserA", user1InSuperUserA.getId(), list.get(0).getId());
        assertEquals("siblingUsers第2个为user2InSuperUserA", user2InSuperUserA.getId(), list.get(1).getId());
        assertEquals("siblingUsers第3个为user3InSuperUserA", user3InSuperUserA.getId(), list.get(2).getId());

        list = service.getSiblingUsers(userWithoutSuperUser);

        assertEquals("没有superUser的user，siblingUsers应该有1个", list.size(), 1);
        assertEquals("siblingUsers第1个为userWithoutSuperUser", userWithoutSuperUser.getId(), list.get(0).getId());
    }
    
    @Test
    public void test_getParentSuperUser(){
        this.initUser();

        MidUserService service = new MidUserServiceImpl();
        SuperUser superUser = null;

        superUser = service.getParentSuperUser(null);

        assertNull("getParentSuperUser(null) 返回值应该为null", superUser);

        superUser = service.getParentSuperUser(user1InSuperUserA);
        assertEquals("getParentSuperUser应该为superUserA", superUserA.getId(), superUser.getId());

        superUser = service.getParentSuperUser(userWithoutSuperUser);
        assertNull("userWithoutSuperUser getParentSuperUser应该为null", superUser);
    }
    
    @Test
    public void test_getChildrenUsers(){
        this.initUser();

        MidUserService service = new MidUserServiceImpl();
        List<User> list = null;

        list = service.getChildrenUsers(null);

        assertNull("getChildrenUsers(null) 返回值应该为null", list);

        list = service.getChildrenUsers(superUserA);

        assertEquals("siblingUsers应该有3个", list.size(), 3);
        assertEquals("siblingUsers第1个为user1InSuperUserA", user1InSuperUserA.getId(), list.get(0).getId());
        assertEquals("siblingUsers第2个为user2InSuperUserA", user2InSuperUserA.getId(), list.get(1).getId());
        assertEquals("siblingUsers第3个为user3InSuperUserA", user3InSuperUserA.getId(), list.get(2).getId());
    }
}
