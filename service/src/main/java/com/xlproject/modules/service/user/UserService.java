package com.xlproject.modules.service.user;

import com.github.pagehelper.PageInfo;
import com.xlproject.modules.entity.bean.User;
import com.xlproject.modules.entity.dto.UpdateUserInfoDTO;

import java.math.BigInteger;
import java.util.List;

public interface UserService {
    int addUser(User user);
    User queryUserByName(String name);
    User loginUser(String userName,String password);
    int updUserInfo(User user);
    
    /**
     * 更新用户信息（使用DTO，更安全）
     * @param updateDTO 用户更新信息DTO
     * @return 影响行数
     */
    int updUserInfo(UpdateUserInfoDTO updateDTO);
    
    int updUserPsw(BigInteger id,String username,String oldPwd,String newPwd);
    int updUserRoleStatus(User oraUser,User roleUser);

    PageInfo<User> getUserLikeName(int pageNum,int pageSize,String name);

    List<User> getUserList();
}
