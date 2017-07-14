package com.rishiqing.midware.user.dao;

import com.rishiqing.midware.user.model.Role;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by  on 2017/6/22.Wallace
 */
public class RoleDaoTest extends BaseDaoTest {
    public static RoleDao dao = null;
    public static DatabaseDao dbDao = null;

    @BeforeClass
    public static void prepareMapper(){
        dao = session.getMapper(RoleDao.class);
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
        dbDao.deleteAllTableData("role");
    }

    @Test
    public void test_saveRole(){
        String authority = "ROLE";
        Role u = new Role(authority);
        long result = dao.saveRole(u);

        assertEquals("保存新角色成功返回值为1", result, 1);
        assertNotNull("新增返回值填充id属性", u.getId());

         Role dbRole = dao.getRoleById(u.getId());

        assertEquals("新增角色authority属性保存成功", dbRole.getAuthority(), authority);
    }

    @Test
    public void test_updateRole(){
        String auth1 = "ROLE1",
                auth2 = "ROLE2";

        //  初始对象Role
        Role r = new Role(auth1);
        dao.saveRole(r);
        r = dao.getRoleById(r.getId());
        long role1Id = r.getId();
        long role1Version = r.getVersion();

        Role r2 = dao.getRoleById(role1Id);
        r2.setAuthority(auth2);
        dao.updateRole(r2);
        r2 = dao.getRoleById(r2.getId());
        long role2Id = r2.getId();
        long role2Version = r2.getVersion();

        assertEquals("更新前后id是一样的", role1Id, role2Id);
        assertEquals("更新前后version是同步更新的", role1Version + 1, role2Version);
        assertEquals("更新角色authority属性保存成功", r2.getAuthority(), auth2);
    }

    @Test
    public void test_deleteRole(){
        String auth = "ROLE1";
        Role u = new Role(auth);
        dao.saveRole(u);

        Role dbRole = dao.getRoleById(u.getId());

        assertNotNull(dbRole);

        long result = dao.deleteRole(dbRole);

        assertEquals(result, 1);

        dbRole = dao.getRoleById(u.getId());

        assertNull(dbRole);
    }

    @Test
    public void test_getAllRoles(){
        List<Role> list = dao.getRoleList();

        assertEquals(list.size(), 0);

        String auth1 = "ROLE1", auth2 = "ROLE2";
        Role Role1 = new Role(auth1);
        Role Role2 = new Role(auth2);
        dao.saveRole(Role1);
        dao.saveRole(Role2);

        list = dao.getRoleList();

        assertEquals(list.size(), 2);
        assertEquals(list.get(0).toString(), Role1.toString());
        assertEquals(list.get(1).toString(), Role2.toString());
    }

    @Test
    public void test_getRoleById(){
        String auth1 = "ROLE1";
        Role Role1 = new Role(auth1);
        dao.saveRole(Role1);

        Role dbRole = dao.getRoleById(Role1.getId());

        assertNotNull(dbRole);
        assertEquals(dbRole.toString(), Role1.toString());
    }

    @Test
    public void test_getRoleByAuthority(){
        String auth = "ROLE1";
        Role Role1 = new Role(auth);
        dao.saveRole(Role1);

        Role dbRole = dao.getRoleByAuthority(auth);

        assertNotNull(dbRole);
        assertEquals(dbRole.toString(), Role1.toString());
    }
}
