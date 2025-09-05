package com.xlproject.modules.entity.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

//入库单表
@Data
public class InboundOrder {
    private BigInteger id;//入库单ID
    private String orderNo;//入库单编号，如（RK20250819001）
    @NotBlank(message = "入库类型不可为空")
    @Pattern(regexp = "^(PURCHASE|RETURN|TRANSFER)$", message = "入库类型必须为PURCHASE(采购入库)、RETURN(退货入库)或TRANSFER(调拨入库)")
    private String type;//入库类型 （入库类型：PURCHASE=采购入库，RETURN=退货入库，TRANSFER=调拨入库）
    @NotNull(message = "目标仓库id不可为空")
    private BigInteger warehouseId;//目标仓库ID
    private BigInteger supplierId;//供应商ID，仅采购入库时有效
    @NotNull(message = "操作员id不可为空")
    private BigInteger operatorId;//操作员ID
    @NotNull(message = "总金额不可为空")
    private Double totalAmount;//总金额
    @NotBlank(message = "状态不可为空")
    private String status;//状态，固定为已审核
    private String remark;//备注信息
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;//创建时间
}
