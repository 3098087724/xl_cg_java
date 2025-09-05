package com.xlproject.modules.common.response;

import lombok.Data;

@Data
public class R<T> {
    private Integer code;
    private String msg;
    private T data;

    public static<T> R<T> OK(String msg,T data){
        R<T> r=new R<>();
        r.setCode(200);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
    public static<T> R<T> OK(String msg){
        R<T> r=new R<>();
        r.setCode(200);
        r.setMsg(msg);
        return r;
    }
    public  static <T> R<T> ERROR(Integer code){
        R<T> r=new R<>();
        r.setCode(code);
        return r;
    }
    public  static <T> R<T> ERROR(Integer code,String msg){
        R<T> r=new R<>();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }
    public  static <T> R<T> ERROR(Integer code,String msg,T data){
        R<T> r=new R<>();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
}
