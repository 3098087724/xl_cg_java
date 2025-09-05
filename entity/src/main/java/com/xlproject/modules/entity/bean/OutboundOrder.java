package com.xlproject.modules.entity.bean;

import com.xlproject.modules.entity.annotation.OutBoundType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class OutboundOrder {
    private BigInteger id;//出库单ID
    @NotBlank(message = "出库单编号不可为空")
    private String orderNo;//出库单编号（CK20050820001）
    @NotBlank(message = "出库类型不可为空")
    @OutBoundType(message = "出库类型必须为SALE(销售出库)，DAMAGE(报损出库)，TRANSFER(调拨出库)")
    private String type;//出库类型（SALE=销售出库，DAMAGE=报损出库，TRANSFER=调拨出库）
    @NotNull(message = "操作员ID不能为空")
    private BigInteger operatorId;//操作员ID
    @NotNull(message = "总金额不可为空")
    private Double totalAmount;//总金额（元）
    @NotBlank(message = "状态不可为空")
    private String status;//状态，固定为已审核
    private String remark;//备注信息
    private LocalDateTime createTime;//创建时间
}
