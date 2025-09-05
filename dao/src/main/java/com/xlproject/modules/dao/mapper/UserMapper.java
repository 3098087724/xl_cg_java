package com.xlproject.modules.dao.mapper;

import com.xlproject.modules.entity.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;

@Mapper
public interface UserMapper {
    int addUser(User user);
    User queryUserByName(String name);
    int updUserInfo(User user);
    User queryUserById(BigInteger id);
    int updUserPsw(BigInteger id, String username ,String newPwd);
    int updUserRoleStatus(User user);

}
