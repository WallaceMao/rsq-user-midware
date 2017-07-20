package com.rishiqing.midware.user.service;

import com.rishiqing.midware.user.model.SuperUser;
import com.rishiqing.midware.user.model.Team;
import com.rishiqing.midware.user.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by  on 2017/7/18.Wallace
 */
public interface MidUserService {
    /**
     * @deprecated
     * 测试方法，以后删除
     * @return
     */
    public String getTestText();

    /**
     * 获取user的兄弟用户
     * @param user
     * @return
     */
    public List<User> getSiblingUsers(User user);

    /**
     * 获取user对应的超用户
     * @param user
     * @return
     */
    public SuperUser getParentSuperUser(User user);

    /**
     *  获取superUser
     * @param superUser
     * @return
     */
    public List<User> getChildrenUsers(SuperUser superUser);

    /**
     * 创建新公司
     * @param currentUser
     * @param team
     * @return
     */
    public Team createTeam(User currentUser, Team team);

    /**
     * 退出team团队
     * @param currentUser
     * @param team
     * @return
     */
    public Team quitTeam(User currentUser, Team team);

    /**
     * 解散团队
     * @param currentUser
     * @param team
     * @return
     */
    public Team dismissTeam(User currentUser, Team team);

    /**
     * 将team设置为主团队
     * @param currentUser
     * @return
     */
    public Team setMain(User currentUser, Team team);
}
