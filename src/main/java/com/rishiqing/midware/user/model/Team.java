package com.rishiqing.midware.user.model;

import com.alibaba.fastjson.JSON;

import java.util.Date;
import java.util.List;

/**
 * Created by  on 2017/6/26.Wallace
 */
public class Team {
    private long id;
    private long version = 0L;
    private String name;
    private Date dateCreated;
    private Date lastUpdated;

    private List<User> userList;

    public Team() {
    }

    public Team(long id) {
        this.id = id;
    }

    public Team(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Team(String name) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
