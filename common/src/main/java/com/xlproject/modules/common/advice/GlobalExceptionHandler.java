package com.xlproject.modules.common.advice;

import com.xlproject.modules.common.exception.BizException;
import com.xlproject.modules.common.response.R;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public R error(Exception e){return  R.ERROR(400,e.getMessage());}
    @ExceptionHandler(BizException.class)
    public  R error(BizException e){
        return  R.ERROR(e.getCode(),e.getMsg());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  R methodArgumentNotValidException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        Map<String,String> errorMap=new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return R.ERROR(500,"校验错误",errorMap);
    }
}
