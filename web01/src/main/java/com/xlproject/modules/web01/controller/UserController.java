package com.xlproject.modules.web01.controller;

import com.github.pagehelper.PageInfo;
import com.xlproject.modules.common.exception.BizException;
import com.xlproject.modules.common.response.R;
import com.xlproject.modules.entity.bean.User;
import com.xlproject.modules.entity.dto.UpdateUserInfoDTO;
import com.xlproject.modules.service.user.UserService;
import com.xlproject.modules.util.jwt.JwtUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin//允许跨域
@RestController
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    
    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/user")
    public R<?> addUser(@Valid @RequestBody User user){
        try {
            int result = userService.addUser(user);
            if (result > 0) {
                return R.OK("新增用户成功");
            }
            return R.ERROR(10001, "新增用户失败");
        } catch (Exception e) {
            return R.ERROR(10002, "新增用户异常: " + e.getMessage());
        }
    }

    @GetMapping("/user/{name}")
    public  R<User> getUserByName(@PathVariable("name") String name){
        try {
            User user = userService.queryUserByName(name);
            user.setPassword("***********");
            return R.OK("根据用户名获取用户成功", user);
        } catch (Exception e) {
            return R.ERROR(10003, "获取用户失败: " + e.getMessage());
        }
    }

    @PostMapping("/user/login")
    public  R<?> loginUser(@RequestParam @Valid String userName,@RequestParam @Valid String password){
        try {
            User user = userService.loginUser(userName, password);
            user.setPassword("************");
            String token = jwtUtil.generateToken(user.getUsername(), user.getId().longValue(), user.getRole());
            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("userInfo", user);
            return R.OK("登录成功", result);
        } catch (Exception e) {
            return R.ERROR(10004, "登录失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新用户信息
     * @param userInfo 用户信息更新DTO
     * @return R<?> 响应结果
     */
    @PutMapping("/user/info")
    public R<?> updUserInfo(@Valid @RequestBody UpdateUserInfoDTO userInfo) {
        try {
            int result = userService.updUserInfo(userInfo);
            if (result > 0) {
                return R.OK("用户信息更新成功");
            }
            return R.ERROR(1000, "用户信息更新失败，请重试");
        } catch (BizException e) {
            return R.ERROR(e.getCode(), e.getMsg());
        } catch (Exception e) {
            return R.ERROR(1000, "更新用户信息异常: " + e.getMessage());
        }
    }

    @PutMapping("/user/pwd")
    public  R<?> updUserPwd(
            @RequestParam
            @NotNull(message = "id不可为空")
            BigInteger id,
            @RequestParam
            @NotNull(message = "用户名不可为null")
            @NotBlank(message = "用户名不可为空")
            String username,
            @RequestParam
            @NotNull(message = "旧密码不可为null")
            @NotBlank(message = "旧密码不可为空")
            String oldPwd,
            @RequestParam
            @NotNull(message = "新密码不可为null")
            @NotBlank(message = "新密码不可为空")
            String newPwd){
        int i = userService.updUserPsw(id, username, oldPwd, newPwd);

        if (i>0){
            return  R.OK("更新密码成功");
        }
        return  R.ERROR(1000,"更新密码失败");
    }
    @PutMapping("/user/roleStatus")
    public  R<?> updUserRole(
            @RequestBody Map<String,User>request){
        User oraUser = request.get("oraUser");
        User roleUser = request.get("roleUser");
        int i = userService.updUserRoleStatus(oraUser, roleUser);
        if(i>0){
            return R.OK("更改成功");
        }
       return   R.ERROR(1000,"更改权限失败");
    }

    @GetMapping("/user/name")
    public R<PageInfo<User>> getUserByName(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name) {
        return R.OK("根据用户名获取用户信息", userService.getUserLikeName(pageNum, pageSize, name));
    }
    
    @GetMapping("/user")
    public R<List<User>> getAllUsers() {
        return R.OK("获取用户信息", userService.getUserList());
    }

}
