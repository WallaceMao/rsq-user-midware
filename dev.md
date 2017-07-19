1  一对多：1端获取collection，多端获取association
2  多对多：两端分别获取collection

lastUpdated问题：dao层控制时间


CREATE TABLE `super_user` (
`id`  bigint NOT NULL AUTO_INCREMENT ,
`main_user_id`  bigint NOT NULL ,
`default_login_user_id`  bigint NOT NULL ,
PRIMARY KEY (`id`),
CONSTRAINT `FK_SUPER_USER_MAIN_USER_ID` FOREIGN KEY (`main_user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
CONSTRAINT `FK_SUPER_USER_LOGIN_USER_ID` FOREIGN KEY (`default_login_user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
;

ALTER TABLE `user`
ADD COLUMN `super_user_id`  bigint NULL AFTER `team_id`;
ALTER TABLE `user` ADD CONSTRAINT `FK_USER_SUPER_USER_ID` FOREIGN KEY (`super_user_id`) REFERENCES `super_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE `super_user`
ADD COLUMN `version`  bigint NOT NULL AFTER `id`;

ALTER TABLE `super_user`
ADD COLUMN `date_created`  datetime NOT NULL AFTER `default_login_user_id`,
ADD COLUMN `last_updated`  datetime NOT NULL AFTER `date_created`;


CREATE TABLE `user_join_team_history` (
`id`  bigint NOT NULL AUTO_INCREMENT ,
`join_time`  datetime NOT NULL ,
`leave_time`  datetime NULL ,
`user_id`  bigint NOT NULL ,
`super_user_id`  bigint NOT NULL ,
`team_id`  bigint NOT NULL ,
PRIMARY KEY (`id`),
CONSTRAINT `fk_join_history_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
CONSTRAINT `fk_join_history_super_user` FOREIGN KEY (`super_user_id`) REFERENCES `super_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
CONSTRAINT `fk_join_history_team` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);


# MODEL层设计说明

## model的基本属性

1. model的构造方法必须包含空构造方法（否则MyBatis会报错！），其他参数的构造方法视情况而定。
2. model中基本属性中可以用基本类型表示的一律使用基本类型表示。例如使用long而不是用Long
3. model中基本属性一律为private类型。根据情况增加getter和setter方法

## model的关联属性

model层中明确表示出不同model之间的关联关系
以下以此为例：
* User--UserDetail：一对一关系
* User--Team：多对一关系
* Team--User：一对多关系
* User--Role：多对多关系

### 关联原则

1. 双边关系均需要增加对方类型的属性

### 一对一关系

两端均以getXXX的方法读取对方
User--UserDetail之间是一对一的关系，则：

1. User端：
  UserDetail类型的属性userDetail，具有相应的getter和setter
2. UserDetail端：
  User类型的属性user，具有相应的getter和setter

### 一对多关系

一端以getXXXList的方法读取多端，多端以getXXX的方式读取一端
User--Team之间是一对多的关系
1. User端：
  Team类型属性team，具有相应的getter和setter
2. Team端：
  List<User>类型属性userList，具有相应的getter和setter

### 多对多关系

两端均以getXXXList的方法读取对方
User--Role之间是多对多的关系
1. User端：
  List<Role>类型属性roleList，具有相应的getter和setter
2. Role端：
  List<User>类型属性usersList，具有相应的getter和setter

# DAO层设计说明

每个model对应一个modelDao，用于进行与该model相关的操作

## model的基本dao方法
1. long saveXXX(XXX xxx)：保存方法
2. long deleteXXX(XXX xxx)：删除
3. long updateXXX(XXX xxx)：更新
4. List<XXX> getXXXList()：获取所有的XXX对象列表
4. XXX getXXXById(long id)：根据id获取对象
5. XXX getXXXByYYY(YYY yyy)：根据YYY属性获取对象

## model的关联属性的增删改查设计规则

例如：
* User--UserDetail：一对一关系
* User--Team：多对一关系
* Team--User：一对多关系
* User--Role：多对多关系

### 关联原则：
1. 首次读取先读取model的基本属性，关联属性需要二次读取
2. 关联对象的双边dao增加获取对方的方式
3. 如果根据1和2原则读取对象特别复杂，则适当增加快捷查询方式

### 一对一关系

两端均以getXXX的方法读取对方，以updateXXXSetYYY的方式建立或取消关联关系
User--UserDetail之间是一对一的关系，则：

1. User端：
  public long updateUserSetUserDetail(User user)：更新user参数的userDetail属性。如果user参数的userDetail属性为null，则清空user的userDetail属性
  public User getUserUserDetail(User user)：读取UserDetail。其中参数user是User端指定id的User对象；返回的User存在UserDetail类型的userDetail属性
2. UserDetail端：
  public long updateUserDetailSetUser(UserDetail userDetail)：更新userDetail参数的user属性。如果userDetail参数的user属性为null，则清空userDetail的user属性
  public UserDetail getUserDetailUser(UserDetail userDetail)：读取User。其中参数userDetail是指定id的UserDetail对象；返回的userDetail存在User类型的user属性

一般当一对一的两端存在主从关系时，从端的update方法可以省略

### 一对多关系

一端以getXXXList的方法读取多端，多端以getXXX的方式读取一端
一端以addXXXYYY的方式新增多端，多端以updateXXXSetYYY的方式建立或取消关联关系
User--Team之间是一对多的关系

1. User端（多端）：
  public long updateUserSetTeam(User user)：更新user参数的team属性。如果user参数的team属性为null，则清空user的team属性
  public User getUserTeam(User user)：读取User。其中参数user是指定id的User对象；返回的User存在Team类型的属性team
2. Team端（一端）：
  public long addTeamUser(User user)：在Team中新增user。user中包含team属性
  public Team getTeamUserList(Team team)：读取Team。其中参数team是指定id的Team对象；返回的Team存在List<User>类型的属性userList

### 多对多关系

两端均以getXXXList的方法读取对方
由于多对多关系一定有中间表，那么需要创建关联对象XXXYYY. addXXXYYY和removeXXXYYY分别用户增加和删除关联对象
如果关联对象具有其他属性，则应当拆分成两段一对多关系
User--Role之间是多对多的关系

1. User端：
  public long addUserRole(UserRole userRole)：增加一条UserRole的多对多关联
  public long removeUserRole(UserRole userRole)：删除一条UserRole的多对多关联
  public User getUserRoleList(User user)：读取User。其中参数user是指定id的User对象；返回的User存在List<Role>类型的属性roleList
2. Role端：
  public long addUserRole(UserRole userRole)：增加一条UserRole的多对多关联
  public long removeUserRole(UserRole userRole)：删除一条UserRole的多对多关联
  public Role getRoleUserList(Role role)：读取Role。其中参数role是指定id的Role对象；返回的Role存在List<User>类型的属性userList

一般多对多关联存在主从端，从端的add和remove方法可以省略

### 数据量限制

基于性能上的考虑，dao不能一次性获取太多数据

## 关于dateCreated和lastUpdated字段使用

dateCreated和lastUpdated字段均为系统维护，不需要在dao保存object时使用上做任何操作
