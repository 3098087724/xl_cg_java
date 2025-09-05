package com.xlproject.modules.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BizException extends  RuntimeException{
    private Integer code;
    private  String msg;
    public BizException(Integer code,String msg){
        super(msg);
        this.code=code;
        this.msg=msg;
    }
    public BizException(BizExceptionEnum exceptionEnum){
        super(exceptionEnum.getMsg());
        this.code=exceptionEnum.getCode();
        this.msg=exceptionEnum.getMsg();
    }
}
