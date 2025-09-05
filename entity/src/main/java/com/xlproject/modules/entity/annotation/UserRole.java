package com.xlproject.modules.entity.annotation;

import com.xlproject.modules.entity.validator.UserRoleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {UserRoleValidator.class}
)
public @interface UserRole {
    String message() default "{jakarta.validation.constraints.NotBlank.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
