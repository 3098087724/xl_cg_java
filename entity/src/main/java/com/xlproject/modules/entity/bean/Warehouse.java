package com.xlproject.modules.entity.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

//仓库表
@Data
public class Warehouse {
    private BigInteger id;//仓库Id
    @Pattern(regexp = "^WH\\d{3}$", message = "仓库编号必须以WH开头，后跟3位数字")
    private String code;//仓库编号，如（WH001）
    @NotNull(message = "仓库名称不能为空")
    private String name;//仓库名称
    private String location;//仓库位置
    private BigInteger managerId;//负责人ID
    private int capacity;//仓库容量
    @Min(value = 0, message = "状态必须是0或1")
    @Max(value = 1, message = "状态必须是0或1")
    private Integer status;//状态 1=启用，0=停用
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;//创建时间
}
