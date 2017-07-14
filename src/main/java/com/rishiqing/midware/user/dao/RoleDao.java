package com.rishiqing.midware.user.dao;

import com.rishiqing.midware.user.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by  on 2017/6/21.Wallace
 */
public interface RoleDao {

    public long saveRole(Role role);

    public long deleteRole(Role role);

    public long updateRole(Role role);

    public List<Role> getRoleList();

    public Role getRoleById(long id);

    public Role getRoleByAuthority(@Param("authority") String authority);

    public Role getRoleUserList(Role role);
}
