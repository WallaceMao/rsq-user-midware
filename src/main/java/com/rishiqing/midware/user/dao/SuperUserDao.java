package com.rishiqing.midware.user.dao;

import com.rishiqing.midware.user.model.SuperUser;
import com.rishiqing.midware.user.model.User;

import java.util.List;

/**
 * Created by  on 2017/7/10.Wallace
 */
public interface SuperUserDao {
    public long saveSuperUser(SuperUser superUser);

    public long deleteSuperUser(SuperUser superUser);

    public long updateSuperUser(SuperUser superUser);

    public List<SuperUser> getSuperUserList();

    public SuperUser getSuperUserById(long id);

    public long addSuperUserUser(User user);

    public SuperUser getSuperUserUserList(SuperUser superUser);

    public SuperUser getSuperUserMainUser(SuperUser superUser);

    public SuperUser getSuperUserDefaultLoginUser(SuperUser superUser);
}
