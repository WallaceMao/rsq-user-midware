package com.rishiqing.midware.user.service;

import com.rishiqing.midware.user.model.SuperUser;
import com.rishiqing.midware.user.model.User;

import java.util.List;

/**
 * Created by  on 2017/7/18.Wallace
 */
public interface IMidwareUserService {
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
     * 1 创建公司
     2 邀请成员
     3 退出公司（踢出成员）
     4 解散公司
     5 设置主公司
     6 设置默认登录公司
     */
}
