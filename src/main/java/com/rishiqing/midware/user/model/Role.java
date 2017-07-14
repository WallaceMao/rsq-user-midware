package com.rishiqing.midware.user.model;

import java.util.List;

/**
 * Created by  on 2017/6/22.Wallace
 */
public class Role {
    private long id;
    private long version = 0L;
    private String authority;

    private List<User> userList;

    public Role() {
    }

    public Role(long id, String authority){
        this.id = id;
        this.authority = authority;
    }

    public Role(String authority) {
        this.authority = authority;
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

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "Role: [" +
                "id: " + this.id +
                ",version: " + this.version +
                ",authority: " + this.authority +
                "]";
    }
}
