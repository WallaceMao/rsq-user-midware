package com.rishiqing.midware.user.dao;

import com.rishiqing.midware.user.model.Role;
import com.rishiqing.midware.user.model.User;
import com.rishiqing.midware.user.model.UserRole;
import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by  on 2017/6/21.Wallace
 */
public class UserRoleDaoTest extends BaseDaoTest {

    public static UserDao userDao = null;
    public static RoleDao roleDao = null;
    public static DatabaseDao dbDao = null;

    @BeforeClass
    public static void prepareMapper(){
        userDao = session.getMapper(UserDao.class);
        roleDao = session.getMapper(RoleDao.class);
        dbDao = session.getMapper(DatabaseDao.class);
    }

    @AfterClass
    public static void cleanMapper(){
        userDao = null;
        roleDao = null;
        dbDao = null;
    }

    @Before
    @After
    public void clearDatabase(){
        dbDao.deleteAllTableData("user_role");
        dbDao.deleteAllTableData("user");
        dbDao.deleteAllTableData("role");
    }


    @Test
    public void test_saveUserRole(){
        //  setup
        String username = "user1",
                password = "user1password",
                realName = "用户1";
        User u = new User(username, password, realName);
        long result = userDao.saveUser(u);
        assertEquals("保存新用户成功返回值为1", result, 1);

        String auth1 = "ROLE1";
        Role role1 = new Role(auth1);
        result = roleDao.saveRole(role1);
        assertEquals("保存新角色1成功返回值为1", result, 1);

        UserRole userRole1 = new UserRole(u, role1);
        result = userDao.addUserRole(userRole1);
        assertEquals("保持新成员角色关联返回值应该为1", result, 1);
    }

    @Test
    public void test_deleteUserRoleByUser(){
        //  setup
        String username = "user1",
                password = "user1password",
                realName = "用户1";
        User u = new User(username, password, realName);
        long result = userDao.saveUser(u);
        assertEquals("保存新用户成功返回值为1", result, 1);

        String auth1 = "ROLE1",
                auth2 = "ROLE2";
        Role role1 = new Role(auth1);
        result = roleDao.saveRole(role1);
        assertEquals("保存新角色1成功返回值为1", result, 1);
        Role role2 = new Role(auth2);
        result = roleDao.saveRole(role2);
        assertEquals("保存新角色2成功返回值为1", result, 1);

        UserRole userRole1 = new UserRole(u, role1);
        result = userDao.addUserRole(userRole1);
        assertEquals("保持新成员角色关联1返回值应该为1", result, 1);
        UserRole userRole2 = new UserRole(u, role2);
        result = userDao.addUserRole(userRole2);
        assertEquals("保持新成员角色关联2返回值应该为1", result, 1);

        result = userDao.removeUserRole(new UserRole(u));
        assertEquals("根据用户删除角色成功返回值应该为2", result, 2);
    }

    @Test
    public void test_deleteUserRoleByRole(){
        //  setup
        String username = "user1",
                password = "user1password",
                realName = "用户1",
                username2 = "user2",
                password2 = "user2password",
                realName2 = "用户2";
        User u = new User(username, password, realName);
        long result = userDao.saveUser(u);
        assertEquals("保存新用户1成功返回值为1", result, 1);
        User u2 = new User(username2, password2, realName2);
        result = userDao.saveUser(u2);
        assertEquals("保存新用户2成功返回值为1", result, 1);

        String auth1 = "ROLE1";
        Role role1 = new Role(auth1);
        result = roleDao.saveRole(role1);
        assertEquals("保存新角色1成功返回值为1", result, 1);

        UserRole userRole1 = new UserRole(u, role1);
        result = userDao.addUserRole(userRole1);
        assertEquals("保持新成员角色关联1返回值应该为1", result, 1);
        UserRole userRole2 = new UserRole(u2, role1);
        result = userDao.addUserRole(userRole2);
        assertEquals("保持新成员角色关联2返回值应该为1", result, 1);

        result = userDao.removeUserRole(new UserRole(role1));
        assertEquals("根据用户删除角色成功返回值应该为2", result, 2);
    }

    @Test
    public void test_deleteUserRoleByUserAndRole(){
        //  setup
        String username = "user1",
                password = "user1password",
                realName = "用户1",
                username2 = "user2",
                password2 = "user2password",
                realName2 = "用户2";
        User u = new User(username, password, realName);
        long result = userDao.saveUser(u);
        assertEquals("保存新用户1成功返回值为1", result, 1);
        User u2 = new User(username2, password2, realName2);
        result = userDao.saveUser(u2);
        assertEquals("保存新用户2成功返回值为1", result, 1);

        String auth1 = "ROLE1",
                auth2 = "ROLE2";
        Role role1 = new Role(auth1);
        result = roleDao.saveRole(role1);
        assertEquals("保存新角色1成功返回值为1", result, 1);
        Role role2 = new Role(auth2);
        result = roleDao.saveRole(role2);
        assertEquals("保存新角色2成功返回值为1", result, 1);

        UserRole userRole1 = new UserRole(u, role1);
        result = userDao.addUserRole(userRole1);
        assertEquals("保持新成员角色关联1返回值应该为1", result, 1);
        UserRole userRole2 = new UserRole(u2, role2);
        result = userDao.addUserRole(userRole2);
        assertEquals("保持新成员角色关联2返回值应该为1", result, 1);

        result = userDao.removeUserRole(new UserRole(u, role1));
        assertEquals("根据用户和角色删除角色成功返回值应该为1", result, 1);
        result = userDao.removeUserRole(new UserRole(u, role2));
        assertEquals("根据没有关联的用户和角色删除角色成功返回值应该为0", result, 0);
    }

    @Test
    public void test_getUserRolesByUser(){
        //  setup
        String username = "user1",
                password = "user1password",
                realName = "用户1";
        User u = new User(username, password, realName);
        long result = userDao.saveUser(u);
        assertEquals("保存新用户成功返回值为1", result, 1);

        String auth1 = "ROLE1",
                auth2 = "ROLE2";
        Role role1 = new Role(auth1);
        result = roleDao.saveRole(role1);
        assertEquals("保存新角色1成功返回值为1", result, 1);

        Role role2 = new Role(auth2);
        result = roleDao.saveRole(role2);
        assertEquals("保存新角色2成功返回值为1", result, 1);

        UserRole userRole1 = new UserRole(u, role1);
        result = userDao.addUserRole(userRole1);
        assertEquals("保持新成员角色关联1返回值应该为1", result, 1);
        UserRole userRole2 = new UserRole(u, role2);
        result = userDao.addUserRole(userRole2);
        assertEquals("保持新成员角色关联2返回值应该为1", result, 1);

        User dbUser = userDao.getUserRoleList(u);
        assertEquals(dbUser.getRoleList().size(), 2);

    }

    @Test
    public void test_getUserRolesByRole(){
        //  setup
        String username = "user1",
                password = "user1password",
                realName = "用户1",
                username2 = "user2",
                password2 = "user2password",
                realName2 = "用户2";
        User u = new User(username, password, realName);
        long result = userDao.saveUser(u);
        assertEquals("保存新用户1成功返回值为1", result, 1);
        User u2 = new User(username2, password2, realName2);
        result = userDao.saveUser(u2);
        assertEquals("保存新用户2成功返回值为1", result, 1);

        String auth1 = "ROLE1";
        Role role1 = new Role(auth1);
        result = roleDao.saveRole(role1);
        assertEquals("保存新角色1成功返回值为1", result, 1);

        UserRole userRole1 = new UserRole(u, role1);
        result = userDao.addUserRole(userRole1);
        assertEquals("保持新成员角色关联1返回值应该为1", result, 1);
        UserRole userRole2 = new UserRole(u2, role1);
        result = userDao.addUserRole(userRole2);
        assertEquals("保持新成员角色关联2返回值应该为1", result, 1);

        Role dbRole = roleDao.getRoleUserList(role1);
        assertEquals(dbRole.getUserList().size(), 2);

    }
}
