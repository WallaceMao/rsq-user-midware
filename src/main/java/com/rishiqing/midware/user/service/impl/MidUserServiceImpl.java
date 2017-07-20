package com.rishiqing.midware.user.service.impl;

import com.rishiqing.midware.user.model.SuperUser;
import com.rishiqing.midware.user.model.Team;
import com.rishiqing.midware.user.model.User;
import com.rishiqing.midware.user.service.MidUserService;

import java.util.List;

/**
 * Created by  on 2017/7/18.Wallace
 */
public class MidUserServiceImpl implements MidUserService {

    public String getTestText() {
        return "this is test from MidwareUserService";
    }

    public List<User> getSiblingUsers(User user) {
        return null;
    }

    public SuperUser getParentSuperUser(User user) {
        return null;
    }

    public List<User> getChildrenUsers(SuperUser user) {
        return null;
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
