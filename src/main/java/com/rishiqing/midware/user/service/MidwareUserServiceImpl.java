package com.rishiqing.midware.user.service;

import com.rishiqing.midware.user.model.SuperUser;
import com.rishiqing.midware.user.model.User;

import java.util.List;

/**
 * Created by  on 2017/7/18.Wallace
 */
public class MidwareUserServiceImpl implements IMidwareUserService {

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
}
