package com.xlproject.modules.entity.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class Product {
    private BigInteger id;//商品ID

    @Pattern(regexp = "^SP\\d{4}$", message = "商品编号必须以SP开头，后跟4位数字")
    private String code;//商品编号（SP001）

    @NotBlank(message = "商品名称不能为空")
    private String name;//商品名称

    private BigInteger categoryId;//所属分类ID,关联categor表
    private String unit;//计量单位（如：件，箱，千克，包)

    @NotNull(message = "商品单价不能为空")
    @DecimalMin(value = "0.0", inclusive = false, message = "商品单价必须大于0")
    private Double price;//单价，单位：元

    @Min(value = 10, message = "预警值最低位10")
    private Integer minStock;//最低库存预警值，低于此值提醒补货,

    private BigInteger supplierId;//供应商ID
    private Integer shelfLife;//保质期，单位（天）

    @Min(value = 0, message = "状态必须是0或1")
    @Max(value = 1, message = "状态必须是0或1")
    private Integer status;//状态 1=正常销售，0=已停用

    private String imageUrl;//商品图片地址

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;//创建时间
}
