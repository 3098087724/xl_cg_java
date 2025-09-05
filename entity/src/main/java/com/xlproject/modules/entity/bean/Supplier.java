package com.xlproject.modules.entity.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

//供应商类
@Data
public class Supplier {
    private BigInteger id;//供应商Id
    @NotNull(message = "供应商名称不能为空")
    private  String name;//供应商名称
    private  String contactPerson;//联系人姓名
    @Pattern(regexp = "^(1[3-9]\\d{9}|0\\d{2,3}-?\\d{7,8})$", message = "请输入正确的电话号码")
    private  String phone;//联系电话
    private  String address;//地址
    @Min(value = 0, message = "状态必须是0或1")
    @Max(value = 1, message = "状态必须是0或1")
    private  Integer status;//状态 1=合作中，0=已终止
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;//创建时间
}
