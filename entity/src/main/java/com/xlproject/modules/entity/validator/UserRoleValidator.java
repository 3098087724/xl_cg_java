package com.xlproject.modules.entity.validator;

import com.xlproject.modules.entity.annotation.UserRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserRoleValidator implements ConstraintValidator<UserRole,String> {
    /**
     *
     * @param s 前端提交来的需要进行校验的数据
     * @param constraintValidatorContext 校验上下文
     * @return
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return "ADMIN".equals(s)||"WAREHOUSE".equals(s);
    }
}
