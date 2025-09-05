package com.xlproject.modules.entity.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xlproject.modules.entity.annotation.UserRole;
import jakarta.validation.constraints.*;

import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

//用户类
@Data
public class User {
    private BigInteger id;//id
    @NotBlank(message = "账户名不能为空")
    private String username;//登录账户名
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, message = "密码长度必须大于等于6位")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "密码不能包含特殊字符")
    private String password;//密码(不能为空，且长度需要大于6位，不能有特殊字符)
    private String realName;//真实姓名
    @Pattern(regexp = "^(1[3-9]\\d{9}|0\\d{2,3}-?\\d{7,8})$", message = "请输入正确的电话号码")
    private String phone;//联系电话
    @NotBlank(message = "角色必须为ADMIN(超级管理员)或WAREHOUSE(仓库管理员)")
    @UserRole(message = "角色必须为ADMIN(超级管理员)或WAREHOUSE(仓库管理员)")
    private String role;//角色（ADMIN=超级管理员，WAREHOUSE=仓库管理员）
    //@Pattern(regexp = "^(0|1)$", message = "状态必须是0或1")
    @Min(value = 0, message = "状态必须是0或1")
    @Max(value = 1, message = "状态必须是0或1")
    private Integer status;//状态 1=正常，0=禁用
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;//创建时间
}
