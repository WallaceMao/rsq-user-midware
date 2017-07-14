package com.rishiqing.midware.user.model;

import com.alibaba.fastjson.JSON;

import java.util.Date;
import java.util.List;

/**
 * Created by  on 2017/6/29.Wallace
 */
public class SuperUser {
    private long id;
    private long version = 0L;
    private User mainUser;
    private User defaultLoginUser;

    private Date dateCreated;
    private Date lastUpdated;

    private List<User> userList;

    public SuperUser() {
    }

    public SuperUser(long id) {
        this.id = id;
    }

    public SuperUser(User mainUser, User defaultLoginUser) {
        this.mainUser = mainUser;
        this.defaultLoginUser = defaultLoginUser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public User getMainUser() {
        return mainUser;
    }

    public void setMainUser(User mainUser) {
        this.mainUser = mainUser;
    }

    public User getDefaultLoginUser() {
        return defaultLoginUser;
    }

    public void setDefaultLoginUser(User defaultLoginUser) {
        this.defaultLoginUser = defaultLoginUser;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
