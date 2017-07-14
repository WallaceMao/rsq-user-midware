package com.rishiqing.midware.user;

import com.rishiqing.midware.user.dao.*;
import com.rishiqing.midware.user.model.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by  on 2017/7/10.Wallace
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        RoleDaoTest.class,
        TeamDaoTest.class,
        UserDaoTest.class,
        UserRoleDaoTest.class,
        UserTeamDaoTest.class,
        UserSuperUserDaoTest.class,
        SuperUserDaoTest.class,
        RoleTest.class,
        TeamTest.class,
        UserRoleTest.class,
        UserTest.class,
        SuperUserTest.class
})
public class ModelAndDaoTestSuite {
}
