package com.xlproject.modules.service.user;

import com.xlproject.modules.entity.bean.User;

import java.math.BigInteger;

public interface UserService {
    int addUser(User user);
    User queryUserByName(String name);
    User loginUser(String userName,String password);
    int updUserInfo(User user);
    int updUserPsw(BigInteger id,String username,String oldPwd,String newPwd);
    int updUserRoleStatus(User oraUser,User roleUser);
}
