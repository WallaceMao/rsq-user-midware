package com.rishiqing.midware.user.dao;

import com.rishiqing.midware.user.model.Team;
import org.apache.log4j.Logger;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by  on 2017/6/21.Wallace
 */
public class TeamDaoTest extends BaseDaoTest {
    private static Logger logger = Logger.getLogger(TeamDaoTest.class);

    public static UserDao userDao = null;
    public static TeamDao dao = null;
    public static DatabaseDao dbDao = null;

    @BeforeClass
    public static void prepareMapper(){
        dao = session.getMapper(TeamDao.class);
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
        dbDao.setTableFieldNull("user", "team_id");
        dbDao.deleteAllTableData("user");
        dbDao.deleteAllTableData("team");
    }

    @Test
    public void test_saveTeam(){
        String name = "团队1";
        Team o = new Team();
        o.setName(name);
        long result = dao.saveTeam(o);

        assertEquals("保存新团队成功返回值为1", result, 1);
        assertNotNull("新增团队返回值填充id属性", o.getId());

        Team dbTeam = dao.getTeamById(o.getId());

        assertEquals("新增团队name属性保存成功", dbTeam.getName(), name);
        assertNotNull("新增团队version属性保存成功", dbTeam.getVersion());
        assertNotNull("新增团队dateCreated属性保存成功", dbTeam.getDateCreated());
        assertNotNull("新增团队lastUpdated属性保存成功", dbTeam.getLastUpdated());
    }

    @Test
    public void test_updateTeam(){
        String name = "团队1", name2 = "团队2";
        Team o = new Team();
        o.setName(name);
        dao.saveTeam(o);
        o = dao.getTeamById(o.getId());
        long o1Id = o.getId();
        long o1Version = o.getVersion();
        long o1LastUpdated = o.getLastUpdated().getTime();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error(e);
        }

        Team o2 = dao.getTeamById(o1Id);
        o2.setName(name2);
        dao.updateTeam(o2);
        o2 = dao.getTeamById(o2.getId());
        long o2Id = o2.getId();
        long o2Version = o2.getVersion();
        long o2LastUpdated = o2.getLastUpdated().getTime();

        assertEquals("更新前后id是一样的", o1Id, o2Id);
        assertEquals("更新前后version是同步更新的", o1Version + 1, o2Version);
        assertEquals("更新团队name属性保存成功", o2.getName(), name2);
        assertTrue("更新团队lastUpdated属性保存成功", o2LastUpdated > o1LastUpdated);
    }

    @Test
    public void test_deleteTeam(){
        String name = "团队1";
        Team u = new Team();
        u.setName(name);
        dao.saveTeam(u);

        Team dbTeam = dao.getTeamById(u.getId());

        assertNotNull(dbTeam);

        long result = dao.deleteTeam(dbTeam);

        assertEquals(result, 1);

        dbTeam = dao.getTeamById(u.getId());

        assertNull(dbTeam);
    }

    @Test
    public void test_getTeamList(){
        String name1 = "团队1", name2 = "团队2";
        Team o1 = new Team(name1);
        long result = dao.saveTeam(o1);

        assertEquals("保存新团队1成功返回值为1", result, 1);
        assertNotNull("新增团队1返回值填充id属性", o1.getId());

        Team o2 = new Team(name2);
        result = dao.saveTeam(o2);

        assertEquals("保存新团队2成功返回值为1", result, 1);
        assertNotNull("新增团队2返回值填充id属性", o2.getId());

        List<Team> teamList = dao.getTeamList();

        assertEquals("新增团队name属性保存成功", teamList.size(), 2);
        assertEquals("新增团队name属性保存成功", teamList.get(0).getId(), o1.getId());
        assertEquals("新增团队name属性保存成功", teamList.get(1).getId(), o2.getId());
    }

    @Test
    public void test_getTeamById(){
        String name = "团队1";
        Team user1 = new Team();
        user1.setName(name);
        dao.saveTeam(user1);

        Team dbTeam = dao.getTeamById(user1.getId());

        assertNotNull(dbTeam);
        //  MySQL和java的时间有差别……
//        assertEquals(dbTeam.toString(), user1.toString());
        assertEquals(user1.getId(), dbTeam.getId());
        assertEquals(user1.getName(), dbTeam.getName());
    }
}
