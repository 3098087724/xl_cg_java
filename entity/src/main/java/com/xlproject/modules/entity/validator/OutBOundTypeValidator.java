package com.xlproject.modules.entity.validator;

import com.xlproject.modules.entity.annotation.OutBoundType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OutBOundTypeValidator implements ConstraintValidator<OutBoundType,String> {
    /**
     *
     * @param s 前端提交来的需要进行校验的数据
     * @param constraintValidatorContext 校验上下文
     * @return 返回校验结果
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return "SALE".equals(s)||"DAMAGE".equals(s)||"TRANSFER".equals(s);
    }
}
