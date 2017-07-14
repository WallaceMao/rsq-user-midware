package com.rishiqing.midware.user.model;

import com.alibaba.fastjson.JSON;

/**
 * Created by  on 2017/6/22.Wallace
 */
public class UserRole {
    private User user;
    private Role role;

    public UserRole() {
    }

    public UserRole(User user) {
        this.user = user;
    }

    public UserRole(Role role) {
        this.role = role;
    }

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
