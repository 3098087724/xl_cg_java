package com.xlproject.modules.service.user.impl;

import com.xlproject.modules.common.exception.BizException;
import com.xlproject.modules.common.exception.BizExceptionEnum;
import com.xlproject.modules.dao.mapper.UserMapper;
import com.xlproject.modules.entity.bean.User;
import com.xlproject.modules.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;

    @Autowired
    public void setUserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    /**
     * 增加用户
     *
     * @param user 用户信息对象
     * @return int结果 >0则成功
     */
    @Override
    public int addUser(User user) {
        user.setCreateTime(LocalDateTime.now());
        User user1 = userMapper.queryUserByName(user.getUsername());
        if (user1 != null) {
            throw new BizException(BizExceptionEnum.USER_ALREADY_EXIT);
        }
        if (user.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
        } else {
            throw new BizException(BizExceptionEnum.USER_PASSWORD_ISNULL);
        }
        // 调用Mapper保存用户
        return userMapper.addUser(user);
    }

    /**
     * 根据用户名搜索用户
     *
     * @param name 用户名
     * @return User 返回用户对象
     */
    @Override
    public User queryUserByName(String name) {
        User user = userMapper.queryUserByName(name);
        if (user != null) {
            return user;
        } else {
            throw new BizException(600, "用户名错误或用户不存在");
        }
    }

    /**
     * 用户登录
     *
     * @param name     用户名
     * @param password 用户密码
     * @return user 返回用户名和密码登录的对象
     */
    @Override
    public User loginUser(String name, String password) {
        User user = userMapper.queryUserByName(name);
        if (user != null) {
            boolean matches = passwordEncoder.matches(password, user.getPassword());
            if (matches) {
                return user;
            } else {
                throw new BizException(BizExceptionEnum.USER_PASSWORD_ERROR);
            }
        } else {
            throw new BizException(BizExceptionEnum.USER_NOT_EXIT);
        }
    }

    /**
     * 更改用户信息
     *
     * @param user 用户对象
     * @return int 返回影响行数
     */
    @Override
    public int updUserInfo(User user) {
        // 验证手机号格式（如果提供了手机号）
        if (user.getPhone() != null && !user.getPhone().matches("^1[3-9]\\d{9}$")) {
            throw new BizException(BizExceptionEnum.USER_PHONE_ERROR);
        }
        // 检查用户名是否已被其他用户使用
        User existingUser = userMapper.queryUserByName(user.getUsername());
        if (existingUser != null && !existingUser.getId().equals(user.getId())) {
            throw new BizException(BizExceptionEnum.USER_USERNAME_EXIT);
        }
        if (user.getRole() != null) {
            throw new BizException(BizExceptionEnum.USER_ROLE_NOT_CAN_UPD);
        }
        if (user.getCreateTime() != null) {
            throw new BizException(1000, "用户创建时间不可更改");
        }

        return userMapper.updUserInfo(user);
    }

    /**
     * @param id       要更改密码的用户id
     * @param username 要更改密码的用户的名字
     * @param oldPwd   要更改用户密码的旧密码
     * @param newPwd   要更改用户密码的新密码
     * @return int 返会更改结果
     */
    @Override
    public int updUserPsw(BigInteger id, String username, String oldPwd, String newPwd) {
        User user = userMapper.queryUserById(id);
        //判断根据id获取的用户是否为空
        if (user != null) {
            //判断根据id获取的用户名是否与参数用户名相等
            if (!username.equals(user.getUsername())) {
                throw new BizException(BizExceptionEnum.USER_Name_ERROR);
            }
        } else {
            throw new BizException(BizExceptionEnum.USER_NOT_EXIT);
        }
        if (!passwordEncoder.matches(oldPwd, user.getPassword())) {
            throw new BizException(BizExceptionEnum.USER_PASSWORD_ERROR);
        }
        if (oldPwd.equals(newPwd)) {
            throw new BizException(1000, "新旧密码需要不一样");
        }
        // 验证新密码长度至少为6位且不包含特殊字符
        if (newPwd == null || newPwd.length() < 6) {
            throw new BizException(1000, "新密码长度至少为6位");
        }
        // 检查是否包含特殊字符（只允许字母和数字）
        if (!newPwd.matches("^[a-zA-Z0-9]+$")) {
            throw new BizException(1000, "新密码只能包含字母和数字，不能包含特殊字符");
        }
        // 更新密码
        String encodedNewPassword = passwordEncoder.encode(newPwd);
        int result = userMapper.updUserPsw(id, username, encodedNewPassword);
        if (result > 0) {
            return result;
        } else {
            throw new BizException(1000, "密码更新失败");
        }
    }

    /**
     * @param oraUser  操作员
     * @param roleUser 被操作者
     * @return 返回影响行数
     */
    @Override
    public int updUserRoleStatus(User oraUser, User roleUser) {
        // 参数校验
        if (oraUser == null || roleUser == null || oraUser.getId() == null || roleUser.getId() == null) {
            throw new BizException(1000, "用户参数传递错误");
        }

        User user1 = userMapper.queryUserById(oraUser.getId());
        User user2 = userMapper.queryUserById(roleUser.getId());
        if (user1 == null || user2 == null) {
            throw new BizException(1000, "操作员或被操作员不存在，请确认");
        }
        // 权限检查
        if ("WAREHOUSE".equals(user1.getRole())) {
            throw new BizException(BizExceptionEnum.USER_NOT_PERMISSION);
        }
        // 角色校验 - 只有当提供了role时才进行校验
        if (roleUser.getRole() != null) {
            if (!"WAREHOUSE".equals(roleUser.getRole()) && !"ADMIN".equals(roleUser.getRole())) {
                throw new BizException(1000, "请设置正确的role角色，（WAREHOUSE|ADMIN）");
            }
        }
        // 状态校验 - 只有当提供了status时才进行校验
        if (roleUser.getStatus() != null) {
            // 假设有效状态值为 0(启用) 和 1(禁用)
            if (roleUser.getStatus() != 0 && roleUser.getStatus() != 1) {
                throw new BizException(1000, "请设置正确的status状态，（0:启用|1:禁用）");
            }
        }
        // 如果既没有设置role也没有设置status，则参数无效
        if (roleUser.getRole() == null && roleUser.getStatus() == null) {
            throw new BizException(1000, "请至少设置role或status中的一个");
        }
        System.out.println(roleUser);
        return userMapper.updUserRoleStatus(roleUser);
    }


}
