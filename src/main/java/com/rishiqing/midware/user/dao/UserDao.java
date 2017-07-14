package com.rishiqing.midware.user.dao;

import com.rishiqing.midware.user.model.SuperUser;
import com.rishiqing.midware.user.model.Team;
import com.rishiqing.midware.user.model.User;
import com.rishiqing.midware.user.model.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by  on 2017/6/21.Wallace
 */
public interface UserDao {

    public long saveUser(User user);

    public long deleteUser(User user);

    public long updateUser(User user);

    public List<User> getUserList();

    public User getUserById(long id);

//    用户角色相关

    public long addUserRole(UserRole userRole);

    public long removeUserRole(UserRole userRole);

    public User getUserRoleList(User user);

//    用户团队相关

    /**
     * 更新user表的team_id字段为user.team.id；如果team为null，
     * @return
     */
    public long updateUserSetTeam(User user);

    /**
     * 获取user的team，并加入到user的team属性中。参数user中只需要包含id属性
     * @param user
     * @return
     */
    public User getUserTeam(User user);

    /**
     * 更新用户的superUser信息，如果user.superUser存在，则更新为user.superUser，如果不存在，则设置为null
     * @param user
     * @return
     */
    public long updateUserSetSuperUser(User user);

    /**
     * 获取user的SuperUser
     * @param user
     * @return
     */
    public User getUserSuperUser(User user);

    /**
     * 获取属于同一个SuperUser的所有User
     * @param superUser
     * @return
     */
    public List<User> getUserListBySuperUser(SuperUser superUser);

}
