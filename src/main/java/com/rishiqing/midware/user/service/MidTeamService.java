package com.rishiqing.midware.user.service;

import com.rishiqing.midware.user.model.Team;
import com.rishiqing.midware.user.model.User;

/**
 * Created by  on 2017/7/20.Wallace
 */
public interface MidTeamService {
    /**
     * 团队邀请用户
     * @param currentTeam
     * @param user
     * @return
     */
    public User inviteMember(Team currentTeam, User user);

    /**
     * 团队移除用户
     * @param currentTeam
     * @param user
     * @return
     */
    public User removeMember(Team currentTeam, User user);
}
