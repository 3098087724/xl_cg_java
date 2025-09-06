package com.xlproject.modules.entity.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigInteger;

/**
 * 用户信息更新DTO
 * 专门用于用户管理的编辑功能，不包含密码字段
 * 
 * @author system
 * @since 2024
 */
@Data
public class UpdateUserInfoDTO {
    
    /**
     * 用户ID（必填）
     */
    @NotNull(message = "用户ID不能为空")
    private BigInteger id;
    
    /**
     * 用户名（可选，如果提供则验证唯一性）
     */
    @Size(min = 2, max = 50, message = "用户名长度必须在2-50字符之间")
    @Pattern(regexp = "^[a-zA-Z0-9_\\u4e00-\\u9fa5]+$", message = "用户名只能包含字母、数字、下划线和中文")
    private String username;
    
    /**
     * 真实姓名（可选）
     */
    @Size(max = 100, message = "真实姓名长度不能超过100字符")
    private String realName;
    
    /**
     * 联系电话（可选，如果提供则验证格式）
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "请输入正确的手机号码")
    private String phone;
    
    /**
     * 用户状态：0=禁用，1=正常（可选）
     */
    @Min(value = 0, message = "状态值必须为0或1")
    @Max(value = 1, message = "状态值必须为0或1")
    private Integer status;
    
    // Getter and Setter methods
    public BigInteger getId() { return id; }
    public void setId(BigInteger id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getRealName() { return realName; }
    public void setRealName(String realName) { this.realName = realName; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    
    @Override
    public String toString() {
        return "UpdateUserInfoDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", realName='" + realName + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                '}';
    }
}
