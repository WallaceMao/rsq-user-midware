package com.rishiqing.midware.user.service.impl;

import com.rishiqing.midware.user.dao.SuperUserDao;
import com.rishiqing.midware.user.dao.UserDao;
import com.rishiqing.midware.user.model.SuperUser;
import com.rishiqing.midware.user.model.Team;
import com.rishiqing.midware.user.model.User;
import com.rishiqing.midware.user.service.MidUserService;
import com.rishiqing.midware.user.sql.DatabaseSystem;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  on 2017/7/18.Wallace
 */
public class MidUserServiceImpl implements MidUserService {

    public static Logger logger = Logger.getLogger(MidUserServiceImpl.class);

    public String getTestText() {
        return "this is test from MidwareUserService";
    }

    public List<User> getSiblingUsers(User user) {

        if(null == user){
            return null;
        }

        SqlSessionFactory factory = DatabaseSystem.getSqlSessionFactory();
        SqlSession session = factory.openSession(true);
        List<User> list = null;

        try {
            UserDao userDao = session.getMapper(UserDao.class);
            SuperUserDao superUserDao = session.getMapper(SuperUserDao.class);

            User dbUser = userDao.getUserSuperUser(user);

            SuperUser su = dbUser.getSuperUser();
            if(null == su){
                 list = new ArrayList<User>();
                list.add(dbUser);
            }else{
                SuperUser dbSuperUser = superUserDao.getSuperUserUserList(su);
                list = dbSuperUser.getUserList();
            }
        } catch (Exception e) {
            logger.error("exception in getSiblingUsers", e);
        } finally {
            if(null != session){
                session.close();
            }
        }
        return list;
    }

    public SuperUser getParentSuperUser(User user) {

        if(null == user){
            return null;
        }

        SqlSessionFactory factory = DatabaseSystem.getSqlSessionFactory();
        SqlSession session = factory.openSession(true);
        SuperUser superUser = null;

        try {
            UserDao userDao = session.getMapper(UserDao.class);

            User dbUser = userDao.getUserSuperUser(user);

            superUser = dbUser.getSuperUser();
        } catch (Exception e) {
            logger.error("exception in getSiblingUsers", e);
        } finally {
            if(null != session){
                session.close();
            }
        }
        return superUser;
    }

    public List<User> getChildrenUsers(SuperUser superUser) {

        if(null == superUser){
            return null;
        }

        SqlSessionFactory factory = DatabaseSystem.getSqlSessionFactory();
        SqlSession session = factory.openSession(true);
        List<User> list = null;

        try {
            SuperUserDao superUserDao = session.getMapper(SuperUserDao.class);

            SuperUser dbSuperUser = superUserDao.getSuperUserUserList(superUser);

            list = dbSuperUser.getUserList();
        } catch (Exception e) {
            logger.error("exception in getSiblingUsers", e);
        } finally {
            if(null != session){
                session.close();
            }
        }
        return list;
    }

    public Team createTeam(User currentUser, Team team) {
        return null;
    }

    public Team quitTeam(User currentUser, Team team) {
        return null;
    }

    public Team dismissTeam(User currentUser, Team team) {
        return null;
    }

    public Team setMain(User currentUser, Team team) {
        return null;
    }
}
