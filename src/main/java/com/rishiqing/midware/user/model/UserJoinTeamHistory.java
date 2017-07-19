package com.rishiqing.midware.user.model;

import com.alibaba.fastjson.JSON;

import java.util.Date;

/**
 * Created by  on 2017/7/18.Wallace
 * 用户加入公司的历史表
 */
public class UserJoinTeamHistory {
    private long id;

    private Date joinTime;
    private Date leaveTime;

    private User user;
    private SuperUser superUser;
    private Team team;

    public UserJoinTeamHistory() {
    }

    public UserJoinTeamHistory(User user, SuperUser superUser, Team team) {
        this.user = user;
        this.superUser = superUser;
        this.team = team;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SuperUser getSuperUser() {
        return superUser;
    }

    public void setSuperUser(SuperUser superUser) {
        this.superUser = superUser;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
