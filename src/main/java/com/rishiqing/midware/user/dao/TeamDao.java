package com.rishiqing.midware.user.dao;

import com.rishiqing.midware.user.model.Team;
import com.rishiqing.midware.user.model.User;

import java.util.List;

/**
 * Created by  on 2017/6/21.Wallace
 */
public interface TeamDao {

    public long saveTeam(Team team);

    public long deleteTeam(Team team);

    public long updateTeam(Team team);

    public List<Team> getTeamList();

    public Team getTeamById(long id);

    public long addTeamUser(User user);

    public Team getTeamUserList(Team team);

    public Team getTeamUserJoinTeamHistoryList(Team team);
}
