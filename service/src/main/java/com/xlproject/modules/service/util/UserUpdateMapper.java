package com.xlproject.modules.service.util;

import com.xlproject.modules.entity.bean.User;
import com.xlproject.modules.entity.dto.UpdateUserInfoDTO;

/**
 * 用户更新相关的映射工具类
 * 
 * @author system
 * @since 2024
 */
public class UserUpdateMapper {
    
    /**
     * 将UpdateUserInfoDTO转换为User实体
     * 只设置允许更新的字段
     * 
     * @param dto 更新DTO
     * @return User 实体对象
     */
    public static User toUser(UpdateUserInfoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setRealName(dto.getRealName());
        user.setPhone(dto.getPhone());
        user.setStatus(dto.getStatus());
        
        // 明确设置为null，确保不会更新这些字段
        user.setPassword(null);
        user.setRole(null);
        user.setCreateTime(null);
        
        return user;
    }
    
    /**
     * 从User实体提取可编辑信息到DTO
     * 
     * @param user 用户实体
     * @return UpdateUserInfoDTO DTO对象
     */
    public static UpdateUserInfoDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        
        UpdateUserInfoDTO dto = new UpdateUserInfoDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRealName(user.getRealName());
        dto.setPhone(user.getPhone());
        dto.setStatus(user.getStatus());
        
        return dto;
    }
}
