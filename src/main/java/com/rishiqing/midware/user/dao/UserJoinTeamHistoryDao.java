package com.rishiqing.midware.user.dao;

import com.rishiqing.midware.user.model.UserJoinTeamHistory;

import java.util.List;

/**
 * Created by  on 2017/7/18.Wallace
 */
public interface UserJoinTeamHistoryDao {

    public long saveUserJoinTeamHistory(UserJoinTeamHistory team);

    public long deleteUserJoinTeamHistory(UserJoinTeamHistory team);

    public long updateUserJoinTeamHistory(UserJoinTeamHistory team);

    public List<UserJoinTeamHistory> getUserJoinTeamHistoryList();

    public UserJoinTeamHistory getUserJoinTeamHistoryById(long id);

    public UserJoinTeamHistory getUserJoinTeamHistorySuperUser(UserJoinTeamHistory history);

    public UserJoinTeamHistory getUserJoinTeamHistoryUser(UserJoinTeamHistory history);

    public UserJoinTeamHistory getUserJoinTeamHistoryTeam(UserJoinTeamHistory history);
}
